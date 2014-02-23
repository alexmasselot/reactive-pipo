package pipo

import org.scalatest.FlatSpec
import org.scalatest.Matchers._
import scala.util.Failure

/**
 * Created by masseloa on 2/20/14.
 */
class MatcherWithTrySpec extends FlatSpec {

  val dd = new DataContainer("pipo", 100, List(10, 20, 30, 40, 50, 60, 70, 80, 90, 80, 70, 60))

  "findOne not found values" should
    "return None" in {
    Match.failureRate = 0
    val matcher = new MatcherWithTry(List(53, 99), 0.001, dd, 3)
    matcher.findOne should be(None)
  }

  "findOne not found values with failrueRate =1" should
    "return None" in {
    Match.failureRate = 1
    val matcher = new MatcherWithTry(List(53, 99), 0.001, dd, 3)
    matcher.findOne should be(None)
  }

  "findOne one value with failreRate =0" should
    "return Some(o)" in {
    Match.failureRate = 0

    val matcher = new MatcherWithTry(List(20), 1.7, dd, 2)
    val om = matcher.findOne

    om should be('defined)
    om.get.srcName should equal("pipo")
    om.get.matchedValues should equal(List(20))

  }
  "findOne one value with failreRate =1" should
    "return None" in {
    Match.failureRate = 1

    val matcher = new MatcherWithTry(List(20), 1.7, dd, 2)
    val om = matcher.findOne

    om should be('empty)
  }



  "findAll 2 value with slice 3 with score 3.0 (at least 2 guys)  with failureRate = 0" should
    "return twp pairs couple" in {
    Match.failureRate = 0

    val matcher = new MatcherWithTry(List(60, 70, 80), 4.0, dd, 3)
    val matches = matcher.findAll

    matches.map(_.get.matchedValues).toList should equal(List(List(60, 70, 80),List(80, 70,60)))
  }
  "findAll 2 value with slice 3 with score 3.0 (at least 2 guys)  with failureRate = 1" should
    "return two failure" in {
    Match.failureRate = 1

    val matcher = new MatcherWithTry(List(60, 70, 80), 4.0, dd, 3)
    val matches = matcher.findAll

    matches.map(_.isFailure) should equal(List(true, true))
  }
}