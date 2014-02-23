package pipo

import org.apache.commons.math3.distribution.BinomialDistribution
import scala.util.{Try, Failure, Success, Random}


/**
 * slice source array in nSlice elements (sliding) and find occurrences of of a given subset wih the soruce collection
 * only the set with a score >= minScore are kept
 *
 * Created by masseloa on 2/20/14.

 * @param targets the list of values that must be found
 * @param minScore the minimum score to keep it
 * @param source  the container of targeted values
 * @param nSlice  the size of a slice
 *
 */
class MatcherBasic(val targets: List[Int], val minScore: Double, val source: DataContainer, val nSlice: Int) extends MatcherCommon{
  /**
   * find the first occurrence
   * @return an potential Match (failing Match are returned with a None)
   */
  def findOne: Option[Match] = {
    buildSlices.find(isSliceMatching) match {
      case Some(slice) => Some(buildMatchFromSlice(slice))
      case None => None
    }
  }

  /**
   * get the list of all the matches
   */
  // v_try
  // def findAll: Iterator[Match] = buildSlices.filter(isSliceMatching).map(buildMatchFromSlice)
  def findAll: List[Match] = buildSlices.filter(isSliceMatching).map(buildMatchFromSlice).toList


  /**
   * from a matching slice, build a Match object from the given slice
   * @param slice a matching slice of numbers from the source
   * @return
   */
  private def buildMatchFromSlice(slice: List[Int]): Match = {
    val commonValues = slice.intersect(targets)
    new Match(srcName = source.name, score = score(commonValues), matchedValues = commonValues.toList)
  }

}
