package com.blocksberg.afterdark.storage.scan

import scalax.io._
import java.security.MessageDigest
import org.bouncycastle.asn1.cmp.ProtectedPart
import javax.sound.midi.Patch
import scalax.file.PathMatcher.{IsDirectory, IsFile}
import com.blocksberg.afterdark.storage.scan.matcher.IsModifiedSince
import scalax.file.{PathMatcher, Path}
import com.blocksberg.afterdark.storage._

/**
 * Created by IntelliJ IDEA.
 * User: jh
 * Date: 6/18/11
 * Time: 9:03 AM
 * To change this template use File | Settings | File Templates.
 */


trait HashCalculator {
  val digest:MessageDigest

  def hash(file:Path):Hash = hash(file.bytes)
  def hash(resource: ResourceView[Byte]):Hash

  protected def toBigInt(resource: ResourceView[Byte]):BigInt = synchronized{
    digest.reset()
    resource.foreach(b => digest.update(b))

    val result = digest.digest()
    BigInt(result)
  }
}


class Rumba20HashCalculator extends HashCalculator {
  val digest = MessageDigest.getInstance("Rumba20")

  override def hash(resource: ResourceView[Byte]) = {
    new Rumba20Hash(toBigInt(resource))
  }

}

class IsUnknownToDatabase() extends PathMatcher {
  def apply(path: Path): Boolean = true
}


class NeedsHashing extends PathMatcher {
  def apply(path: Path): Boolean = {
    return true
  }
}

class Scanner() {


  def scan(matcher:PathMatcher)(path:Path) =
    path.children(matcher)

  def scanRecursive(matcher:PathMatcher)(path:Path) =
    path.descendants(matcher)





}


