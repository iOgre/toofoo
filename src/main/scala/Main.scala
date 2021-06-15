import tf.Program._
import data.Datasource.Result

object Main extends App {
  Application.mainApp()
}

object Application {
    def program(userId: Option[Int], recommenderId: Option[String] = None, limit: Option[Int] = None): Unit = {
    import tf.interpreters.OptionInterpreters._
    val result: Option[Result] = getRecommendations[Option](userId, recommenderId, limit)
    printResults(userId, result)
  }

  def mainApp() = {
    program(Some(1), Some("algo1"), None)
    println("------------------------------\n")

    program(Some(2), Some("algo2"), Some(5))
    println("------------------------------\n")

    program(Some(3), Some("algo5"), Some(15))
    println("------------------------------\n")

    program(Some(14), Some("algo2"), Some(15))
    println("------------------------------\n")

    program(None, Some("algo3"), Some(15))
    println("------------------------------\n")

    program(Some(1), Some("algo3"), Some(15))
    println("------------------------------\n")
  }

}
 