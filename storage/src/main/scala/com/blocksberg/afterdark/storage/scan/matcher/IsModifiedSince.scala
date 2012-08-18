package com.blocksberg.afterdark.storage.scan.matcher

import scalax.file.{Path, PathMatcher}

/**
 * matches if path was modified since a given time
 */
class IsModifiedSince(since:Long) extends PathMatcher {
  def apply(path: Path): Boolean = path.lastModified >= since
}

object IsModifiedSince {
  def IsModifiedSince(since:Long) = new IsModifiedSince(since)

}
