package com.blocksberg.afterdark.storage.scan

import scalax.io._
import java.security.MessageDigest
import javax.sound.midi.Patch
import scalax.file.PathMatcher.{IsDirectory, IsFile}
import com.blocksberg.afterdark.storage.scan.matcher.IsModifiedSince
import scalax.file.{PathMatcher, Path}
import com.blocksberg.afterdark.storage._
import com.blocksberg.afterdark.storage.hash.Rumba20Hash
import com.blocksberg.afterdark.storage.hash.MD5Hash
import com.blocksberg.afterdark.storage.hash.Hash




class IsUnknownToDatabase() extends PathMatcher {
  def apply(path: Path): Boolean = true
}


class NeedsHashing extends PathMatcher {
  def apply(path: Path): Boolean = {
    return true
  }
}

class Scanner() {


  def scan(matcher:PathMatcher[Path])(path:Path) =
    path.children(matcher)

  def scanRecursive(matcher:PathMatcher[Path])(path:Path) =
    path.descendants(matcher)

}


