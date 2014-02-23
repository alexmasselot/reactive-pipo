package pipo.app

import scala.concurrent._
import ExecutionContext.Implicits.global
import pipo._
import scala.async.Async._
import scala.util.Success
import scala.util.Failure

/**
 * Created by Alexandre Masselot on 2/21/14.
 */
object AppFuture extends CommonsApp {
  output(s"start ${this.getClass.getSimpleName}")

  val data = DataContainer("one shot", 100, 1000000)
  val matcher = new MatcherWithTry(List(10, 50, 99, 25, 2, 3, 4, 5, 7, 8, 9), 3.0, data, 30)

  val futureMatched = async {
    matcher.findAll.toList
  }
  futureMatched onComplete {
    case Success(matched) => for (m <- matched) {
      output(m.toString)
    }
    case Failure(t) => println(t)
  }

  output("I'm free!")

  Thread.sleep(10000)
  output("done")
}
