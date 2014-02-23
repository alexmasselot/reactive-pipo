package pipo

import scala.util.{Try, Failure, Success}


/**
 *  extends MatcherCommon, with findOne and FindAll returning Try[Match] instead of jsut Match (which can fail)
 * Created by Alexandre Masselot on 2/23/14.

 * @param targets the list of values that must be found
 * @param minScore the minimum score to keep it
 * @param source  the container of targeted values
 * @param nSlice  the size of a slice
 *
 */
class MatcherWithTry(val targets: List[Int], val minScore: Double, val source: DataContainer, val nSlice: Int) extends MatcherCommon {
  /**
   * find the first occurrence
   * @return an potential Match (failing Match are returned with a None)
   */
  def findOne: Option[Match] = {
    buildSlices.find(isSliceMatching) match {
      // if something was found, we build a Match object out of it
      //      case Some(slice) => Some(buildMatchFromSlice(slice))
      case Some(slice) =>
        buildMatchFromSlice(slice) match {
          case Success(m) => Some(m)
          case Failure(e) => None //OK, failing silently is maybe not the best options
        }
      case None => None
    }
  }

  /**
   * get the list of all the matches
   */
  def findAll: List[Try[Match]] = buildSlices.filter(isSliceMatching).map(buildMatchFromSlice).toList


  /**
   * from a matching slice, build a Match object from the given slice
   * @param slice a slice of values from source
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
