package pipo.app

import scala.collection.parallel.ParIterable

/**
 * Created by Alexandre Masselot on 2/21/14.
 */
object AppListParSum extends CommonsApp{

  def stupidSum(l:List[Int])=l.par.map({ x=>
    (0 until 100).map({
      y => math.log10(y.toDouble)
    }).sum
    x
  }).sum

  val myList = List.fill(1000000)(1)

  output(s"start ${this.getClass.getSimpleName}")
  output(s"tot = ${stupidSum(myList)}")


  output("done")
}
