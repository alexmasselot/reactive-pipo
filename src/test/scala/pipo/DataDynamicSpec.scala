package pipo

import org.scalatest.FlatSpec
import org.scalatest.Matchers._

/**
 * Created by Alexandre Masselot on 2/21/14.
 */
class DataDynamicSpec extends FlatSpec{
  "getting twice the iterator" should "reset it" in {
    val data = DataDynamic("pipo", 100, 1000)

    data should have size(1000)

    data.dataIterator.toList should have size(1000)
    //and the second time should also go
    data.dataIterator.toList should have size(1000)

  }

  "check median value" should "be around 50" in {
    val n =10000
    val data = DataDynamic("pipo", 100, n)

    val med = data.dataIterator.toList.sorted
    med(n/2) should (be > 30 and be <70)
  }
}
