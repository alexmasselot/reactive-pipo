package pipo.app

import pipo.{MatcherWithTry, Match, DataContainer, MatcherBasic}
import scala.util.Try

/**
 * Created by Alexandre Masselot on 2/21/14.
 */
object AppWithTry extends CommonsApp {
  output(s"start ${this.getClass.getSimpleName}")

  val data = DataContainer("one shot", 100, 1000000)
  val matcher = new MatcherWithTry(List(10, 50, 99, 25,2,3,4,5,7,8,9), 3.0, data, 30)

  val matched: List[Try[Match]] = matcher.findAll

  for (m <- matched) {
    output(m.toString)
  }

  output("done")
}
