package pipo.app

import pipo.{MatcherWithTry, Match, DataContainer, MatcherBasic}
import scala.util.Try

/**
 * to survive Match exception failure, get a list of Try[Match]
 * Created by Alexandre Masselot on 2/21/14.
 */
object AppActorOne extends CommonsApp {
  output(s"start ${this.getClass.getSimpleName}")
  Match.failureRate=0.3

  val data = DataContainer("one shot", 100, 1000000)
  val matcher = new MatcherWithTry(List(10, 50, 99, 25,2,3,4,5,7,8,9), 3.0, data, 30)

  for (m <- matcher.findAll) {
    output(m.toString)
  }

  output("done")
}
