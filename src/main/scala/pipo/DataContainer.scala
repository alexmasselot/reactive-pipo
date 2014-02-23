package pipo

import scala.util.Random


/**
 * Int number container
 * @param name a name (used as a kind of id...
 * @param maxValue the maximum value in the list
 * @param numbers just a list of Int
 */
class DataContainer(val name: String, val maxValue: Int, val numbers: List[Int]){

  def size = numbers.size
}


object DataContainer {
  /**
   * create a random DataContainer of nbVal values between 0 and maxValue-1
   * @param name identifier
   * @param maxValue upper boundary for h random numbers
   * @param nbVal the number of element given
   * @return a DataStatic structure
   */
  def apply(name: String, maxValue: Int, nbVal: Int) = {
    val rnd = new Random()
    new DataContainer(name, maxValue, (0 until nbVal).toList.map({
      _ => rnd.nextInt(maxValue)
    }))
  }
}