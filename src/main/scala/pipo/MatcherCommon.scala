package pipo

import org.apache.commons.math3.distribution.BinomialDistribution
import scala.util.{Try, Failure, Success}


/**
 * slice source array in nSlice elements (sliding) and find occurrences of of a given subset wih the soruce collection
 * only the set with a score >= minScore are kept
 *
 * Created by Alexandre Masselot on 2/20/14.

 */
trait MatcherCommon {
  //the list of values that must be found
  val targets: List[Int]
  //the minimum score to keep it
  val minScore: Double
  //the container of targeted values
  val source: DataContainer
  //the size of a slice
  val nSlice: Int

  //just throw exception at instanciation if it makes no sense a match can ever be produced
  if (targets.size == 0) {
    throw new InvalidMatcherException(s"cannot look for empty targets")
  }

  /*
   * we have target.size trials
   * the probability of a number to match another is p=1/source.maxValue
   * so, the probability of matching within one slice is 1-(1-p)^slice.size
   *
   * Yep, that's a crude estimation, but please have a look at the class and package names
    */
  private val binomialDist = new BinomialDistribution(targets.size, 1 - Math.pow(1 - 1.0 / source.maxValue, nSlice))


  /**
   *
   * the score log10(1-P(X>=x)
   * @param commonValues list of common values
   * @return the score for the passed commonValues probability to have a match lower than the size
   */
  def score(commonValues: List[Int]): Double = {
    -math.log10(1.0 - binomialDist.cumulativeProbability(commonValues.size - 1))
  }


  /*
   * get all the slice of size nSlice into the source numbers, with an incremental step of 1
   */
  protected def buildSlices: Iterator[List[Int]] = {
    source.numbers.sliding(nSlice, 1)
  }

  /**
   * check if the curSlice as at least nMinMatch element in common with the target list
   * @param curSlice  the current slice
   * @return
   */
  protected def isSliceMatching(curSlice: List[Int]): Boolean = {
    score(curSlice.intersect(targets)) >= minScore
  }


}
