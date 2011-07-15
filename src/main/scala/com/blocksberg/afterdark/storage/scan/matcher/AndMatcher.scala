package com.blocksberg.afterdark.storage.scan.matcher

import scalax.file.{Path, PathMatcher}

/**
 * Created by IntelliJ IDEA.
 * User: jh
 * Date: 6/23/11
 * Time: 12:24 PM
 * To change this template use File | Settings | File Templates.
 */

class AllMatcher(matcher: PathMatcher*) extends PathMatcher {
  def apply(path: Path): Boolean = matcher.forall(m => m(path))
}

class AnyMatcher(matcher: PathMatcher*) extends PathMatcher {
  def apply(path: Path): Boolean = matcher.exists(m => m(path))
}