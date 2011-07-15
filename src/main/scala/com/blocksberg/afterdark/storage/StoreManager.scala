package com.blocksberg.afterdark.storage

import scalax.file.{PathMatcherFactory, Path, PathMatcher}
import org.eclipse.jetty.util.PatternMatcher
import com.blocksberg.afterdark.storage.scan.matcher.{IsModifiedSince, AllMatcher, AnyMatcher}
import com.blocksberg.afterdark.storage.scan.{Hashable, HashCalculator, Scanner}
import scalax.file.PathMatcher.{IsDirectory, IsFile}
import scala.annotation.tailrec
import com.blocksberg.afterdark.storage.db.DBSchema
import scala.collection.mutable.{Set,HashSet}


/**
 * @author Justin Heesemann
 */
class StoreManager(scanner:Scanner, hashStrategy: HashCalculator) {
  private var lastScan = 0l
  private var shares:Set[DBFolder] = new HashSet[DBFolder]


  def scanAllShares = {
    val newLastScan = System.currentTimeMillis
    val matcher = new IsModifiedSince(lastScan)

    shares.foreach(s => scanner.scan(matcher)(s.path))

    lastScan = newLastScan
  }


  def updateFileHash(storable: Storable) = storable match {
    case file: DBFile =>
      file.hash = Some(hashStrategy.hash(file.path))
  }

  def storeFile(storable: Storable) = {
    import org.squeryl.PrimitiveTypeMode._
    storable match {

      case file: DBFile =>

        inTransaction {
          DBSchema.files.insertOrUpdate(file)
        }
      case folder: DBFolder =>
        inTransaction {
          DBSchema.folders.insertOrUpdate(folder)
        }
    }
  }

  /**
   * maps all found paths to Storables (and eventually creates them if they are missing) and runs updateFunc for each Storable
   * @param matcher: a PathMatcher
   * @param updateFunc: the function run for each Storable
   */
  final def updateFolder(matcher: PathMatcher)(updateFunc: (Storable) => Unit)(parentFolder: DBFolder): Unit = {
    import org.squeryl.PrimitiveTypeMode._
    scanner.scan(matcher)(parentFolder.path).foreach(f => f match {
      case IsFile(filePath) => {
        //find file in db,
        val file =
          inTransaction {
            DBSchema.files.where(f => (f.parentId === Some(parentFolder.id)) and (f.pathName === filePath.name)).headOption match {
              case None => new DBFile(0L, parentFolder.id, filePath.name, filePath.name, None)
              case Some(f:DBFile) => f
            }
          }
        updateFunc(file)

      }
      case IsDirectory(dirPath) => {
        //find folder in db,
        val nextFolder:DBFolder =
          inTransaction {
            val folderOption = DBSchema.folders.where(f => (f.parentId === Some(parentFolder.id)) and (f.pathName === dirPath.name)).headOption
            folderOption match {
              case None => new DBFolder(0L, Some(parentFolder.id), dirPath.name, dirPath.name)
              case Some(f:DBFolder) => f
            }
          }
        updateFunc(nextFolder)
        updateFolder(matcher)(updateFunc)(nextFolder)
      }
    })
  }

  def updateShareHashes() = {

    val matcher = new AnyMatcher(new IsModifiedSince(lastScan))

    val updater = updateFolder(matcher)(updateFileHash) _
    shares.foreach(s => updater(s))
  }

}