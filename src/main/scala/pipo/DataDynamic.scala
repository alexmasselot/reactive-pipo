package pipo

import scala.util.Random


/**
 * A dynamic, iterator based, implementation of DataContainer
 * @param name
 * @param maxValue the maximum value in the list
 */
class DataDynamic(val name: String, val maxValue: Int, nbVal:Int) extends DataContainer {
  val rnd = new Random
  /*
   * having a def ensure that each call to dataIterator will restart from 0
   */
  def dataIterator = {
    var i = 0
    new Iterator[Int]{
      var i=0

      def hasNext = i<nbVal
      def next = {
        i+=1
        rnd.nextInt(maxValue)
      }
    }
  }

  def size = nbVal
}

object DataDynamic {
  /**
   * create a random DummyData
   * @param name
   * @param maxValue upper boundary for h random numbers
   * @param nbVal the numer of element given
   * @return a DataDynamic structure
   */
  def apply(name: String, maxValue: Int, nbVal: Int) = {
    val rnd = new Random()
    new DataDynamic(name, maxValue, nbVal)
  }
}
