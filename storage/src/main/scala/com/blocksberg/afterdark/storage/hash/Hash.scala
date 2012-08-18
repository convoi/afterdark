package com.blocksberg.afterdark.storage.hash

/**
 * Created by IntelliJ IDEA.
 * User: jh
 * Date: 6/18/11
 * Time: 9:06 AM
 * To change this template use File | Settings | File Templates.
 */

trait Hashable {
  def hash:Option[Hash]
}

trait Hash {
  def value:BigInt
}

object Hash {
  implicit def hashable2Hash(x:Hashable) = x.hash
}


class MD5Hash(val value:BigInt) extends Hash

class Rumba20Hash(val value:BigInt) extends Hash

class CombinedHash[A<:Hash,B<:Hash](a:A, b:B) extends Hash {
  lazy val value = (a.value << b.value.bitLength) + b.value
}

class XorSumHash(toCombine: Seq[Hash]) extends Hash {
  lazy val value:BigInt = toCombine.foldLeft(BigInt(0))(_^_.value)
}
