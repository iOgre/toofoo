package tf

import data.Datasource._
import algebras._
import tf.monades.monad._
//import tf.interpreters.OptionInterpreters._

object Program {

  def getRecommendations[F[_]: UserRepo: AlgorithmRepo: Filter: MyMonad](
    userId: Option[Int],
    recommenderId: Option[String],
    limit: Option[Int]
  ): F[Result] =
    for {
      user      <- getUser(userId)
      algorithm <- getAlgorithm(recommenderId)
      result    <- execute(algorithm, user)
      limitFilter = limit.getOrElse(limitDefault)
      resultFiltered <- filter(result, limitFilter)
    } yield Result(algorithm, resultFiltered)

  def printResults[F[_]: MyMonad](userId: Option[Int], result: F[Result]): Unit =
    result.fold[AppError, Unit](
      error => println(s"Error $error"),
      algoRes => {
        println(s"\nRecommnedations for userId ${algoRes.recs.userId}...")
        println(s"Algorithm ${algoRes.algorithm.name}")
        println(s"Recs: ${algoRes.recs.recs}")
      }
    )

}
