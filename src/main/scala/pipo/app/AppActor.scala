package pipo.app

import pipo.{MatcherWithTryIterator, MatcherWithTry, Match, DataContainer}
import akka.actor._
import scala.util.{Try, Success, Failure}

/**
 * to survive Match exception failure, get a list of Try[Match]
 * Created by Alexandre Masselot on 2/21/14.
 */
object AppActor extends App {
  Output.log(s"start ${this.getClass.getSimpleName}")
  Match.failureRate = 0.05

  val system = ActorSystem("System")
  val aMaster = system.actorOf(Props[ActorMaster], "seed")

  Output.log("done")
}

/**
 * the orchestra master
 * create a few workers send them work to do.
 * listen to answers and redirect failures & success to the corresponding managers
 */
class ActorMaster extends Actor {
  val system = ActorSystem("System")

  // a list of workers, each of them handles it own list
  val aWorkers = "ABCDEFGHIJ".toList.map(c =>
    context.actorOf(ActorWorkerMatch.props(c.toString, 100, 1000000), s"worker-$c")
  )

  //success and failure managers
  val aManagerMatches = system.actorOf(Props[ActorManagerMatches], "manager-matches")
  val aManagerFailures = system.actorOf(Props[ActorManagerFailures], "manager-failures")

  var iWorkerTerminated = 0
  val targets = List(List(1, 2, 3, 4, 5, 7, 8, 9, 10, 11, 12), List(81, 82, 83, 84, 85, 87, 88, 89, 90, 91, 92))

  // launch the work
  for {
    w <- aWorkers
    t <- targets
  } {
    w ! FindAll(t, 4.0, 30)
  }

  def receive = {
    case TryMatch(tm) =>
      tm match {
        case Success(m) => aManagerMatches ! new MatchSuccess(m)
        case Failure(e) => aManagerFailures ! new MatchFailure(e)
      }
    case WorkerIsDone =>
      iWorkerTerminated += 1
      //terminate everyone if all tasks have bee completed by all workers
      if (iWorkerTerminated == targets.size * aWorkers.size) {
        for (w <- aWorkers ++ List(aManagerFailures, aManagerMatches)) {
          w ! Die
        }
        context.stop(self)
      }
  }
}

/**
 *
 * @param name data container name,
 * @param maxValue max integer value
 * @param nbVal number of value withing the actor
 */
class ActorWorkerMatch(name: String, maxValue: Int, nbVal: Int) extends Actor {
  val data = DataContainer(name, maxValue, nbVal)

  def receive = {
    case FindAll(targets, minScore, sliceWidth) =>
      val matcher = new MatcherWithTryIterator(targets, minScore, data, sliceWidth)
      for (m <- matcher.findAll) {
        sender ! TryMatch(m)
      }
      sender ! WorkerIsDone
    case Die =>
      println(s"worker $name dying")
      context.stop(self)

  }
}

/**
 * companion object for the worker
 * that's the best practice to construct actor with parameters
 * http://doc.akka.io/docs/akka/snapshot/scala/actors.html
 */
object ActorWorkerMatch {
  def props(name: String, maxValue: Int, nbVal: Int): Props = Props(new ActorWorkerMatch(name, maxValue, nbVal))

}

/**
 * receives errors (and just print them...)
 */
class ActorManagerFailures extends Actor {
  def receive = {
    case MatchFailure(e) => Output.log(s"caught failed $e")
    case Die => println(s"worker mgr failure dying")
      context.stop(self)
  }
}

/**
 * receives successful matches (and just print them)
 */
class ActorManagerMatches extends Actor {
  def receive = {
    case MatchSuccess(m) =>
      Output.log(s"got a match $m")
    case Die =>
      println(s"worker mgr Success dying")
      context.stop(self)
  }
}

object Output {
  val t0 = System.currentTimeMillis()

  def log(text: String*) = {
    println( f"""${System.currentTimeMillis() - t0}%5d\t${text.mkString(", ")}""")
  }
}

/*
  messages. Names should be pretty explicit
  */

case class FindAll(targets: List[Int], minScore: Double, sliceWidth: Int)

case class TryMatch(tm: Try[Match])

case class MatchSuccess(m: Match)

case class MatchFailure(e: Throwable)

object WorkerIsDone

object Die