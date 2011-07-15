package com.blocksberg.afterdark.storage

import javax.naming.spi.Resolver
import scalax.file.Path
import org.squeryl.PrimitiveTypeMode._
import com.blocksberg.afterdark.storage.db.DBSchema._

/**
 * Created by IntelliJ IDEA.
 * User: jh
 * Date: 6/17/11
 * Time: 11:07 PM
 * To change this template use File | Settings | File Templates.
 */

trait StorableResolver {
  def resolve(path:Path):Option[Storable]
}

class DatabaseFileResolver extends StorableResolver {

  override def resolve(path:Path):Option[Storable] = inTransaction {
    //from(files)(where(f => f.))
    println("unsupported")
    None

  }

}