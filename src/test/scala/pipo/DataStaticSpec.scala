package pipo

import org.scalatest.FlatSpec
import org.scalatest.Matchers._

/**
 * Created by masseloa on 2/20/14.
 */
class DataStaticSpec extends FlatSpec {

  "create a new" should
    "have the correct number of elements" in {
    val dd = DataStatic("pipo", 100, 10)
    dd.name should equal("pipo")
    dd should have size 10
    dd.maxValue should equal(100)
  }


}