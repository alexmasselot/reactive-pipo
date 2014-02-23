package pipo

import scala.util.Random


/**
 * an object created by a Matcher
 * THrough the companion object, one can set a failureRate, i.e. the probability to raise an BadMatchException at instance time
 *
 * @param srcName typically the DummyData holder name
 * @param matchedValues the list of matched values
 */
class Match(val srcName:String, val score:Double, val matchedValues:List[Int]) {
  if(Match.rndFail.nextFloat()<Match.failureRate){
    throw new BadMatchException("could build it")
  }

  override def toString =  f"source=$srcName, score=$score%2.2f, matched=$matchedValues"
}

object Match {
 var failureRate = 0.3

  private val rndFail = new Random()
}