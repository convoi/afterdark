package com.blocksberg.afterdark.storage

import scalax.file.Path
import com.blocksberg.afterdark.storage.hash.Hash
import scalax.io.LongTraversable
import scalax.io.ResourceTraversable
import scalax.io.Resource

/**
 * finds a file and returns a handle/inputstream...
 */
trait HashFileFetcher {

  def find(hash: Hash) : Option[LongTraversable[Byte]]
}
trait PathFileFetcher {
  def find(path: Path) : Option[LongTraversable[Byte]]
}

/**
 * simple finds a file by its path relative to an initially given root dir
 */
class PathFileFetcherImpl(root:Path) extends PathFileFetcher {
  // TODO write test
  def find(path:Path) = {
    val absolutePath = root / path
    if (absolutePath.isFile) {
      Some(absolutePath bytes)
    } else {
      None
    }
  }
}
