package com.blocksberg.afterdark.storage.scan.matcher

import scalax.file.{Path, PathMatcher}

/**
 * Created by IntelliJ IDEA.
 * User: jh
 * Date: 6/19/11
 * Time: 10:30 AM
 * To change this template use File | Settings | File Templates.
 */

class IsModifiedSince(since:Long) extends PathMatcher {
  def apply(path: Path): Boolean = path.lastModified >= since
}

object IsModifiedSince {
  def IsModifiedSince(since:Long) = new IsModifiedSince(since)

}
