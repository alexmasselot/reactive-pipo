package pipo.app

import scala.concurrent._
import ExecutionContext.Implicits.global
import pipo._
import scala.async.Async._
import scala.util.{Try, Success, Failure}
import rx.lang.scala._
import rx.lang.scala.schedulers._
import Notification._
import rx.lang.scala.subscriptions.CompositeSubscription


/**
 * Instead of blocking to wait for the Matcher, setup a Future[List[Try[Match]]], so computation can continue during computation
 *
 * Created by Alexandre Masselot on 2/23/14.
 */
object AppObservable extends CommonsApp {

  val data = DataContainer("AAA", 100, 1000000)

  go

  val matcher = new MatcherWithObservable(List(10, 50, 99, 25, 2, 3, 4, 5, 7, 8, 9), 3.0, data, 30)

  //in this example, the flow of Match is de termined at this moment.
  //but nothing would prevent the observable to have element matched added later
  // it will terminate at an onCompleted  or onError event.
  var obs: Observable[Try[Match]] = matcher.findAll
  obs.subscribe( v => output(s"${v.toString}"))


  //we can apply collection operators (map, flatmap, filter ...)
  output("filter on failure")
  obs.filter(_.isFailure).subscribe( v => output(s"${v.toString}"))

  output("done")

}
