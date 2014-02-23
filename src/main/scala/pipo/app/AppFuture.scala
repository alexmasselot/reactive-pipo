package pipo.app

import pipo.{Match, DataContainer, MatcherBasic}
import scala.util.Try

/**
 * Created by Alexandre Masselot on 2/21/14.
 */

object AppFuture extends CommonsApp {
  output(s"start ${this.getClass.getSimpleName}")

  val data = DataContainer("one shot", 100, 1000000)
  val matcher = new MatcherBasic(List(10, 50, 99, 25, 2, 3, 4, 5, 7, 8, 9), 3.0, data, 30)

//  val matched = Async {
//    matcher.findAll.toList
//  }
//
//  for (m <- matched) {
//    output(m.toString)
//  }

  output("done")
}
