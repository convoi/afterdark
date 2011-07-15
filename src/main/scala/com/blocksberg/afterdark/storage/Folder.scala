package com.blocksberg.afterdark.storage

import collection.Iterator
import org.squeryl.KeyedEntity
import com.blocksberg.afterdark.storage.scan.{XorSumHash, Hash, Hashable}
import scala.annotation.tailrec
import scalax.file.Path
import com.blocksberg.afterdark.storage.db.DBSchema

/**
 * Created by IntelliJ IDEA.
 * User: jh
 * Date: 6/15/11
 * Time: 9:19 PM
 * To change this template use File | Settings | File Templates.
 */

/*class Folder(val name:String) extends Storable with Named with Iterable[Storable] {
  var children:Seq[Storable] = Nil

  def toStream(in: Seq[Storable]):Stream[Storable] = in match {
    case Nil => Stream.empty
    case head::tail => (head,tail) match {
      case (head:Folder,tail) => Stream(head) ++ toStream (head.children) ++ toStream (tail)
      case (head,tail) => Stream(head) ++ toStream (tail)
    }
  }

  def iterator: Iterator[Storable] = {
    (Stream(this) ++ toStream(children)) iterator
  }
} */

trait PathLike {
  def parent: Option[PathLike]

  def path: Path

  def fullPath: Path = parent match {
    case None => path
    case Some(p) => p.fullPath / path
  }
}

trait Folder extends Storable with Named with Iterable[Storable] with PathLike {
  def path: Path
}


class DBFolder(var id: Long, val parentId: Option[Long], var name: String, val pathName: String) extends Folder with KeyedEntity[Long] with Hashable {
  private var children: Seq[Storable] = Nil

  def parent: Option[DBFolder] = DBSchema.folderToFolders.right(this).headOption

  lazy val path = parent match {
    case None => Path(pathName)
    case Some(p: Path) => p / pathName
  }


  /**
   * return a xor sum hash for all child hashes or none if there are no hashable descendants
   */
  def hash: Option[Hash] = {
    if (children.isEmpty) return None
    val childHashes = children.filter(p => p.isInstanceOf[Hashable]).flatMap(p => p match {
      case h: Hashable => h.hash;
      case _ => None
    })
    if (childHashes.isEmpty) return None
    return Some(new XorSumHash(childHashes))
  }

  override def toStream: Stream[Storable] = {
    Stream.cons(this,toStream(children))
  }


  private def toStream(in: Seq[Storable]): Stream[Storable] = in match {
    case Nil => Stream.empty
    case (head: Folder) :: tail => head.toStream ++ toStream(tail)
    case head :: tail => Stream.cons(head,toStream(tail))
  }

  def iterator: Iterator[Storable] = {
    (Stream(this) ++ toStream(children)) iterator
  }

}
