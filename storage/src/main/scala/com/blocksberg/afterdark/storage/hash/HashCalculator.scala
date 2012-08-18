package com.blocksberg.afterdark.storage.hash

import java.security.MessageDigest
import scalax.file.Path
import scalax.io.LongTraversable

/**
 * calculates a hash for a file or a resource
 */
trait HashCalculator {
  val digest:MessageDigest

  def hash(file:Path):Hash = hash(file.bytes)
  def hash(resource: LongTraversable[Byte]):Hash

  protected def toBigInt(resource: LongTraversable[Byte]):BigInt = synchronized{
    digest.reset()
    resource.foreach(b => digest.update(b))

    val result = digest.digest()
    BigInt(result)
  }
}


class Rumba20HashCalculator extends HashCalculator {
  val digest = MessageDigest.getInstance("Rumba20")

  override def hash(resource: LongTraversable[Byte]) = {
    new Rumba20Hash(toBigInt(resource))
  }
}


class MD5HashCalculator extends HashCalculator {
  val digest = MessageDigest.getInstance("MD5")
  override def hash(resource: LongTraversable[Byte]) = {
    new MD5Hash(toBigInt(resource))
  }
}
