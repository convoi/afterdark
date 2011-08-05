package com.blocksberg.afterdark.configuration

import java.util.Properties

/**
 * User: hej
 * Date: 05.08.11
 * Time: 11:56
 */

class Property[T](private var value: T) {

  def apply() = value

  def update(newValue: T) = {
    value = newValue
  }
}

object Property {
  def apply[T](x: T) = new Property(x)
  implicit def readProperty[T](x: Property[T]):T = x()
}




trait Configurable[K] {
  def apply(x:K)
}

class Configuration extends Configurable[String]{
  val DB_CONNECTION = "db"
  private var properties: Properties = new Properties()

  private def loadProperties:Unit= {
    // TODO implement
  }
  private def init = {
    loadProperties
    val x = Property(45)
    val foo:Int = x
  }

  def apply(x: String) = properties.get(x)
}