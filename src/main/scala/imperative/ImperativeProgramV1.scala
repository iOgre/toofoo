package imperative
import data.Datasource._
import Imperative._

object ImperativeProgramV1 {

  def program(userId: Option[Int], recommenderId: Option[String] = None, limit: Option[Int] = None): Unit = {
    val user      = getUser(userId)
    val algorithm = getAlgorithm(recommenderId)
    val result = algorithm
      .flatMap(_.run(UserId(user.get)))
      .orElse(Some(emptyRecs(user.get)))
    val limitFilter = limit.getOrElse(limitDefault)

    val resultFiltered =
      result.map(_.copy(recs = recs.slice(0, limitFilter).toList))

    resultFiltered match {
      case Some(recs) =>
      {  println(s"\nRecommnedations for userId ${recs.userId}...")
        println(s"Algorithm ${algorithm.get.name}")
        println(s"Recs: ${recs.recs}")}
      case None => println(s"No recommendations found for userId $userId")
    }

  }
}

