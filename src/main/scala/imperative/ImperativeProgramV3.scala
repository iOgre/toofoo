package imperative
import data.Datasource._
import Imperative._

object ImperativeProgramV3 {

  def getRecommendations(userId: Option[Int], recommenderId: Option[String], limit: Option[Int]): Option[Result] =
    for {
      user     <- getUser(userId)
      algoritm <- getAlgorithm(recommenderId)
      result   <- executeAlgorithm(user, algoritm)
      limitFilter    = limit.getOrElse(limitDefault)
      resultFiltered <- filterResults(result, limitFilter)
    } yield Result(algoritm, resultFiltered)

  def printResults(userId: Option[Int], result: Option[Result]): Unit =
    result.fold(println(s"No recommendations found for userId $userId")) { algoRes =>
      println(s"\nRecommnedations for userId ${algoRes.recs.userId}...")
      println(s"Algorithm ${algoRes.algorithm.name}")
      println(s"Recs: ${algoRes.recs.recs}")
    }

  def getUser(userId: Option[Int]): Option[UserId] = userId.filter(user => users.exists(_.userId == user)).map(UserId(_))
  def getAlgorithm(recommenderId:Option[String]):Option[Algorithm] = recommenderId.orElse(algoDefault).flatMap(algorithms.get(_))
  def executeAlgorithm(user:UserId, algorithm:Algorithm):Option[UserRec] = algorithm.run(user)
  def filterResults(result:UserRec, limitFilter:Int):Option[UserRec] = Some(result.copy(recs = recs.slice(0, limitFilter).toList))

}
