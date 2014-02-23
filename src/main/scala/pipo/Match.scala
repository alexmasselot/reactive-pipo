package pipo

import scala.util.Random


/**
 * an object created by a Matcher
 * @param srcName typically the DummyData holder name
 * @param matchedValues the list of matched values
 */
class Match(val srcName:String, val score:Double, val matchedValues:List[Int]) {
  if(Match.rndFail.nextFloat()<0.3){
    throw new BadMatchException("could build it")
  }

  override def toString =  f"source=$srcName, score=$score%2.2f, matched=$matchedValues"
}

object Match {
  private val rndFail = new Random()
}