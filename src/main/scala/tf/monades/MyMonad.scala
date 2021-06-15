package tf.monades

import data.Datasource._

object monad {

  trait MyMonad[F[_]] {
    def flatMap[A, B](fa: F[A], afb: A => F[B]): F[B]
    def map[A, B](fa: F[A], ab: A => B): F[B]
    def fold[A, B, C](fa: F[A], first: B => C, second: A => C): C
  }

  object MyMonad {

    def apply[F[_]](
      implicit
      P: MyMonad[F]
    ): MyMonad[F] = P

  }

  implicit class MyMonadSyntax[F[_], A](fa: F[A]) {

    def map[B](f: A => B)(
      implicit
      P: MyMonad[F]
    ): F[B] = P.map(fa, f)

    def flatMap[B](afb: A => F[B])(
      implicit
      P: MyMonad[F]
    ): F[B] = P.flatMap(fa, afb)

    def fold[B, C](first: B => C, second: A => C)(
      implicit
      P: MyMonad[F]
    ) = P.fold(fa, first, second)
  }

  implicit object MyMonadOption extends MyMonad[Option] {

    override def flatMap[A, B](fa: Option[A], afb: A => Option[B]): Option[B] =
      fa.flatMap(afb)

    override def map[A, B](fa: Option[A], ab: A => B): Option[B] = fa.map(ab)

    override def fold[A, B, C](fa: Option[A], first: B => C, second: A => C): C = fa.fold(first(UnknownError.asInstanceOf[B]))(second(_))

  }
}
