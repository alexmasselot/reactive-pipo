package pipo

import scala.util.Random


/**
 * A static (list based at once) implementation of DataContainer
 * @param name
 * @param maxValue the maximum value in the list
 * @param numbers just a list of Int
 */
class DataStatic(val name: String, val maxValue: Int, val numbers: List[Int]){//} extends DataContainer {
  /*
   * having a def ensure that each call to dataIterator will restart from 0
   */
//  def dataIterator = numbers.toIterator

  def size = numbers.size
}


object DataStatic {
  /**
   * create a random DummyData
   * @param name
   * @param maxValue upper boundary for h random numbers
   * @param nbVal the number of element given
   * @return a DataStatic structure
   */
  def apply(name: String, maxValue: Int, nbVal: Int) = {
    val rnd = new Random()
    new DataStatic(name, maxValue, (0 until nbVal).toList.map({
      _ => rnd.nextInt(maxValue)
    }))
  }
}