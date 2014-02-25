package pipo.app

import akka.actor._

/**
 * Created by Alexandre Masselot on 2/23/14.
 */
object Ping

object Pong

case class IntMessage(v: Int)

case class StringMessage(v: String)

object AppActorDummy extends App {
  val system = ActorSystem("System")

  val rootActor = system.actorOf(Props[OneActor], "seed")
  rootActor ! "launch"
}


class OneActor extends Actor {
  val otherActor = context.actorOf(Props[OtherActor], "my_friend")

  def receive = {
    case "launch" => otherActor ! Ping
    case Pong =>
      println("one  \tponged")
      otherActor ! new IntMessage(42)

    case StringMessage(v) =>
      println(s"one  \t$v")
      println(s"one  \tciao bella")
      context.stop(self)

  }

}

class OtherActor extends Actor {
  def receive = {
    case Ping =>
      println("other\tpinged")
      sender ! Pong

    case IntMessage( i)=>
      println(s"other\tint $i")
      sender ! new StringMessage(s"<<<${10*i}>>>")

      println("other\tciao bella")
      context.stop(self)

  }
}