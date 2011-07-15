package com.blocksberg.afterdark.storage

import org.squeryl.KeyedEntity
import scan.{Hashable, Hash}
import scalax.file.Path
import com.blocksberg.afterdark.storage.db.DBSchema

/**
 * Created by IntelliJ IDEA.
 * User: jh
 * Date: 6/15/11
 * Time: 9:16 PM
 * To change this template use File | Settings | File Templates.
 */

trait File extends Storable with Named with Hashable with PathLike

class DBFile(var id: Long = 0L, val parentId: Long, var name: String, val pathName: String, var hash: Option[Hash]) extends File with KeyedEntity[Long] {
  lazy val parent = DBSchema.folderToFiles.right(this).headOption
  lazy val path = parent match {
    case None => Path(pathName)
    case Some(p) => p.path / pathName
  }


}