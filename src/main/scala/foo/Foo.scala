package foo

/**
 * User: hej
 * Date: 03.06.11
 * Time: 15:42
 */

class Foo {

  def some = "bla"

  def add(x:Int, y:Int) = x+y

}

class Fasel {
  val some = new Foo

  def doIt = some.add(2,3)
}