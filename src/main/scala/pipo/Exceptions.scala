package pipo

/**
 * Created by Alexandre Masselot on 2/21/14.
 */
case class BadMatchException(msg:String) extends Exception

case class InvalidMatcherException(msg:String) extends Exception

case class MatchNotFoundException() extends Exception