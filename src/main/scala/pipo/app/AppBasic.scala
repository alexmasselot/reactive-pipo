package pipo.app

import pipo._
import scala.util.Try

/**
 * Created by Alexandre Masselot on 2/21/14.
 */
object AppBasic extends CommonsApp {
  Match.failureRate = 0

  val data = DataContainer("one shot", 100, 1000000)
  go

  val matcher = new MatcherBasic(List(10, 50, 99, 25,2,3,4,5,7,8,9), 3.0, data, 30)
  for (m <- matcher.findAll) {
    output(m.toString)
  }

  output("done")
}
