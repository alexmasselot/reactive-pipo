package pipo

import org.scalatest.FlatSpec
import org.scalatest.Matchers._

/**
 * Created by Alexandre Masselot on 2/20/14.
 */
class MatcherBasicSpec extends FlatSpec {
  Match.failureRate = 0

  val dd = new DataContainer("pipo", 100, List(10, 20, 30, 40, 50, 60, 70, 80, 90, 80, 70, 60))

  "new MatcherBasic empty set" should "throw exception" in {
    an[InvalidMatcherException] should be thrownBy {
      new MatcherBasic(List(), 2, dd, 3)
    }
  }

  "score" should "behave with pulling 1 within 5 with p=0.01" in {
    val m = new MatcherBasic(List(1, 2, 3), 3.0, dd, 5)
    m.score(List()) should be(0.0 +- 0.001)
    m.score(List(1)) should be(0.854 +- 0.001)
    m.score(List(1,2)) should be(2.1567 +- 0.001)
    m.score(List(1,2,3)) should be(3.929 +- 0.001)

  }

  "score" should "behave with pulling x within 5 when among a set of 10 values between [0:100[" in {
    val data = DataContainer("10k", 10000, 20)
    val m = new MatcherBasic(List(1, 2, 3,4,5), 3, data, 7)

    m.score(List()) should be(0.0 +- 0.001)
    m.score(List(1,2,3,4)) should be(11.921 +- 0.001)
    m.score(List(1,2,3,4,5)) should be(15.654 +- 0.001)

  }

  "findOne not found values" should
    "return None" in {
    val matcher = new MatcherBasic(List(53, 99), 0.001, dd, 3)
    matcher.findOne should be(None)
  }

  "findOne one value" should
    "return Some(o)" in {
    val matcher = new MatcherBasic(List(20), 1.7, dd, 2)
    val om = matcher.findOne

    om should be('defined)
    om.get.srcName should equal("pipo")
    om.get.matchedValues should equal(List(20))

  }

  "findOne 2 value with slice 3 with score 1" should
    "return Some(o)" in {
    val matcher = new MatcherBasic(List(20, 40), 1.23, dd, 3)
    val om = matcher.findOne

    om should be('defined)
    om.get.matchedValues should equal(List(20))
  }

  "findOne 2 value with slice 3 with score 2" should
    "return Some(o)" in {
    val matcher = new MatcherBasic(List(20, 40), 3.0, dd, 3)
    val om = matcher.findOne

    om should be('defined)
    om.get.matchedValues should equal(List(20, 40))
  }


  "findAll 2 value with slice 3 with score 3.0 (at least 2 guys)" should
    "return one couple" in {
    val matcher = new MatcherBasic(List(20, 40), 3.0, dd, 3)
    val matches = matcher.findAll

    matches.map(_.matchedValues) should equal(List(List(20,40)))
  }


  "findAll 2 value with slice 3 with score 3.0 (at least 2 guys)" should
    "return twp ppairs couple" in {
    val matcher = new MatcherBasic(List(60, 70, 80), 4.0, dd, 3)
    val matches = matcher.findAll

    matches.map(_.matchedValues) should equal(List(List(60, 70, 80),List(80, 70,60)))
  }
}