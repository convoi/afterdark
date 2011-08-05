package com.blocksberg.afterdark.storage.db

import org.squeryl.adapters.H2Adapter
import java.sql.DriverManager
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.{Schema, Table, Session, SessionFactory}
import com.blocksberg.afterdark.storage.{DBFolder, DBFile}

/**
 * Created by IntelliJ IDEA.
 * User: jh
 * Date: 6/19/11
 * Time: 12:09 PM
 * To change this template use File | Settings | File Templates.
 */


/*trait DBSchema {
  val files:Table[DBFile]
}     */

object DBSchema extends Schema {

  val files:Table[DBFile] = table[DBFile]()
  val folders:Table[DBFolder] = table[DBFolder]()

  val folderToFiles = oneToManyRelation(folders, files).via((folder,file) => folder.id === file.parentId)
  val folderToFolders = oneToManyRelation(folders, folders).via((parent,child) => parent.id === child.parentId.get)

  def init = {
    SessionFactory.concreteFactory = Some(()=>
      Session.create(DriverManager.getConnection("..."), new H2Adapter)
    )
    create
  }

}