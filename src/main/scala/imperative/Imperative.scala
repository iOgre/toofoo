package imperative

import data.Datasource._

object Imperative {

  def getUser(userId: Option[Int]): Option[Int] =
    userId.filter(uid => users.exists(_.userId == uid))
    
  def getAlgorithm(recommenderId: Option[String]) =
    recommenderId.orElse(algoDefault).flatMap(algorithms.get(_))
}

