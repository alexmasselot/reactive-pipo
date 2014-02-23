package pipo.app

/**
 * Created by Alexandre Masselot on 2/21/14.
 */
trait CommonsApp extends App {
  val t0 = System.currentTimeMillis()

  def output(text: String*) = {
    println( s"""${System.currentTimeMillis() - t0}\t${text.mkString(", ")}""")
  }

}
