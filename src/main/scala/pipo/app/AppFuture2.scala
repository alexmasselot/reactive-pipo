package pipo.app

import scala.concurrent._
import ExecutionContext.Implicits.global
import pipo._
import scala.async.Async._
import scala.util.{Try, Success, Failure}

/**
 * get 2 DataContainer at once
 *
 * Created by Alexandre Masselot on 2/23/14.
 */
object AppFuture2 extends CommonsApp {
  val data1 = DataContainer("src_1", 100, 1000000)
  val data2 = DataContainer("src_2", 100, 1000000)

  go

  def findMatching(data: DataContainer) = {
    val matcher = new MatcherWithTry(List(10, 50, 99, 25, 2, 3, 4, 5, 7, 8, 9), 3.0, data, 30)

    val futureMatched: Future[List[Try[Match]]] = async {
      matcher.findAll.toList
    }
    futureMatched onComplete {
      case Success(matched) => for (m <- matched) {
        output(m.toString)
      }
      case Failure(t) => println(t)
    }
  }

  findMatching(data1)
  findMatching(data2)

  output("I'm free!")

  Thread.sleep(10000)
  output("done")
}
