package data

import scala.util.Random
import data.Datasource.AppError

object Datasource {

  sealed trait AppError extends Throwable {
    def message: String
    override def toString = message
  }

  case object UnknownError extends AppError {
    override def message = s"Unexpected error"
  }

  case class UserNotFound(userId: UserId) extends AppError {
    override def message: String = s"User not found for id $userId"
  }

  case object UserNotProvided extends AppError {
    override def message: String = s"User id must be provided"
  }

  case class UserId(userId: Int) extends AnyVal
  case class Rec(recId: String, score: Float)
  case class UserRec(userId: UserId, recs: List[Rec])
  case class Algorithm(name: String, run: UserId => Option[UserRec])
  case class Result(algorithm: Algorithm, recs: UserRec)

  lazy val users = (1 until 10).map(UserId(_)).toList

  lazy val recs = ('a' to 'z').map(c => Rec(c.toString, Random.nextFloat))

  lazy val recommendations = users.map { user =>
    if (user.userId % 2 == 0) {
      UserRec(user, List.empty)
    } else {
      UserRec(user, recs.toList)
    }
  }

  def emptyRecs(user: Int): UserRec =
    UserRec(UserId(user), List.empty)

  val algo1 = Algorithm("algo1", userId => recommendations.find(u => u.userId == userId))

  val algo2 = Algorithm(
    "algo2",
    userId =>
      recommendations
        .find(u => u.userId == userId)
        .map(_.copy(recs = recs.filter(r => r.recId > "h").toList))
  )
  val algo3 = Algorithm("algo3", _ => None)

  lazy val algorithms = Map(
    "algo1" -> algo1,
    "algo2" -> algo2,
    "algo3" -> algo3
  )

  val algoDefault: Option[String] = Some("algo1")

  val limitDefault = 10

}
