package tf
import data.Datasource._

object algebras {

  trait UserRepo[F[_]] {
    def getUser(userId: Option[Int]): F[UserId]
  }

  object UserRepo {

    def apply[F[_]](
      implicit
      UserR: UserRepo[F]
    ): UserRepo[F] = UserR
  }

  trait Filter[F[_]] {
    def filter(userRec: UserRec, limit: Int): F[UserRec]
  }

  object Filter {

    def apply[F[_]](
      implicit
      Fil: Filter[F]
    ): Filter[F] = Fil
  }

  trait AlgorithmRepo[F[_]] {
    def getAlgorithm(recommenderId: Option[String]): F[Algorithm]
    def execute(algo: Algorithm, userId: UserId): F[UserRec]
  }

  object AlgorithmRepo {

    def apply[F[_]](
      implicit
      Algo: AlgorithmRepo[F]
    ): AlgorithmRepo[F] = Algo
  }

  def getUser[F[_]: UserRepo](userId: Option[Int]): F[UserId] = UserRepo[F].getUser(userId)

  def filter[F[_]: Filter](userRec: UserRec, limit: Int): F[UserRec] =
    Filter[F].filter(userRec, limit)

  def getAlgorithm[F[_]: AlgorithmRepo](recommenderId: Option[String]): F[Algorithm] =
    AlgorithmRepo[F].getAlgorithm(recommenderId)

  def execute[F[_]: AlgorithmRepo](algo: Algorithm, userId: UserId): F[UserRec] =
    AlgorithmRepo[F].execute(algo, userId)

}

