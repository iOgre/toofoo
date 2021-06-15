package imperative
import data.Datasource._
import Imperative._

object ImperativeProgramV2 {

  def program(userId: Option[Int], recommenderId: Option[String] = None, limit: Option[Int] = None): Unit = {
    val result = for {
      user <- getUser(userId)
      algoritm <- getAlgorithm(recommenderId)
      result <- algoritm.run(UserId(user))
      limitFilter = limit.getOrElse(limitDefault)
      resultFiltered = result.copy(recs = recs.slice(0, limitFilter).toList)
    } yield Result(algoritm, resultFiltered)

    result match {
         case Some(algoRes) => {
        println(s"\nRecommnedations for userId ${algoRes.recs.userId}...")
        println(s"Algorithm ${algoRes.algorithm.name}")
        println(s"Recs: ${algoRes.recs.recs}")
      }
      case None => println(s"No recommendations found for userId $userId")
    }
    
  }
}

