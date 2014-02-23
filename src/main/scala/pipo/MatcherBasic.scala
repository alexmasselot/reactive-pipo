package pipo

import org.apache.commons.math3.distribution.BinomialDistribution
import scala.util.{Try, Failure, Success, Random}


/**
 * slice source array in nSlice elements (sliding) and find occurrences of nMinMatch or the target set found in a slice
 *
 * Created by masseloa on 2/20/14.

 * @param targets the list of values that must be found
 * @param minScore the minimum score to keep it
 * @param source  the container of targeted values
 * @param nSlice  the size of a slice
 *
 */
class MatcherBasic(targets: List[Int], minScore: Double, source: DataStatic, nSlice: Int) {
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
   * find the first occurence
   * @return an potential Match
   */
  def findOne: Option[Match] = {
    buildSlices.find(isSliceMatching) match {
      // if something was found, we build a Match object out of it
      //      case Some(slice) => Some(buildMatchFromSlice(slice))
      case Some(slice) => {
        buildMatchFromSlice(slice) match {
          case Success(m) => Some(m)
          case Failure(e) => None //OK, failing silently is maybe not the best options
        }
      }
      case None => None
    }
  }

  /**
   * get the list of all the matches
   */
  // v_try
  // def findAll: Iterator[Match] = buildSlices.filter(isSliceMatching).map(buildMatchFromSlice)
  def findAll: Iterator[Try[Match]] = buildSlices.filter(isSliceMatching).map(buildMatchFromSlice)

  /**
   *
   * the score log10(1-P(X>=x)
   * @param commonValues
   * @return the score for the passed commonValues probability to have a match lower than the size
   */
  def score(commonValues: List[Int]): Double = {
    -math.log10(1.0 - binomialDist.cumulativeProbability(commonValues.size - 1))
  }


  /*
   * get all the slice of size nSlice into the source numbers, with an incremental step of 1
   */
  private def buildSlices: Iterator[List[Int]] = {
    source.numbers.sliding(nSlice, 1)
  }

  /**
   * check if the curSlice as at least nMinMatch element in common with the target list
   * @param curSlice
   * @return
   */
  private def isSliceMatching(curSlice: List[Int]): Boolean = {
    score(curSlice.intersect(targets)) >= minScore
  }


  /**
   * from a matching slice, build a Match object from the given slice
   * @param slice
   * @return
   */
  private def buildMatchFromSlice(slice: List[Int]): Try[Match] = {
    val commonValues = slice.intersect(targets)
    try {
      Success(new Match(srcName = source.name, score = score(commonValues), matchedValues = commonValues.toList))
    } catch {
      case e: BadMatchException => Failure(e)
    }
  }

}
