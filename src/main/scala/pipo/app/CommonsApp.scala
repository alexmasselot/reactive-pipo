package pipo.app

/**
 * Created by Alexandre Masselot on 2/21/14.
 */
trait CommonsApp extends App {
  var t0 = System.currentTimeMillis()

  def go = {
    t0=System.currentTimeMillis()
    output(s"start ${this.getClass.getSimpleName}")
  }
  def output(text: String*) = {
    println( f"""${System.currentTimeMillis() - t0}%5d\t${text.mkString(", ")}""")
  }

}
