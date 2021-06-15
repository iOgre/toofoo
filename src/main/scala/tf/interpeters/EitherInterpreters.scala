package tf.interpreters

import data.Datasource._
import tf.algebras._


object EitherInterpreters {
     implicit object UserRepoEither extends UserRepo[Either[AppError, ?]] {
    override def getUser(userId: Option[Int]): Either[AppError, UserId] = {
      for {
        userParam <- userId.map(UserId(_)).toRight(UserNotProvided)
        userDb    <- users.find(_ == userParam).toRight(UserNotFound(userParam))
      } yield userDb
    }
  }
}