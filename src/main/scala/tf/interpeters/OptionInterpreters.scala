package tf.interpreters

import data.Datasource._
import tf.algebras._

object OptionInterpreters {

  implicit object UserRepoOption extends UserRepo[Option] {
    override def getUser(userId: Option[Int]): Option[UserId] = userId.filter(user => users.exists(_.userId == user)).map(UserId(_))
  }

  implicit object AlgorithmRepoOption extends AlgorithmRepo[Option] {

    override def getAlgorithm(recommenderId: Option[String]): Option[Algorithm] =
      recommenderId.orElse(algoDefault).flatMap(algorithms.get(_))
    override def execute(algo: Algorithm, userId: UserId): Option[UserRec] = algo.run(userId)
  }

  implicit object FilterOption extends Filter[Option] {
      override def filter(userRec: UserRec, limit:Int):Option[UserRec] = 
        Some(userRec.copy(recs = recs.slice(0, limit).toList))
  }
}

