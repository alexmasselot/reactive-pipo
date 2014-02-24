package pipo

import scala.util.{Try, Failure, Success}
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.async.Async._
import rx.lang.scala._


/**
 * extends MatcherCommon,
 * findOne returns a Future[Match]
 * findAll return an Observable [Match]
 *
 * Created by Alexandre Masselot on 2/23/14.

 * @param targets the list of values that must be found
 * @param minScore the minimum score to keep it
 * @param source  the container of targeted values
 * @param sliceWidth  the size of a slice
 *
 */
class MatcherWithObservable(val targets: List[Int], val minScore: Double, val source: DataContainer, val sliceWidth: Int) extends MatcherCommon {
  /**
   * find the first occurrence and put into a future.
   * if nothing is found or an error os thrown, the exception will be captured by the Future failing state
   * @return an potential Match (failing Match are returned with a None)
   */
  def findOne: Future[Match] = async {
    buildSlices.find(isSliceMatching) match {
      case Some(m) => buildMatchFromSlice(m) match {
        case Success(n) => n
        case Failure(e) => throw e
      }
      case None => throw new MatchNotFoundException()
    }
  }

  /**
   * get the list of all the matches
   */
  def findAll: Observable[Try[Match]] = {

    Observable.create[Try[Match]] {
      observer => {
        for (slice <-  buildSlices.filter(isSliceMatching)) {
          observer.onNext(buildMatchFromSlice(slice))
        }
        observer.onCompleted()
        Subscription {}
      }
    }
  }

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
