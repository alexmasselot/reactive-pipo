package pipo

/**
 * Created by Alexandre Masselot on 2/21/14.
 */
trait DataContainer {
  /**
   *
   * @return
   */
  def dataIterator:Iterator[Int]

  /**
   * the maximum value an integer can have
   */
  val maxValue:Int

  /**
   * the name the container
   */
  val name:String

  def size:Int
}
