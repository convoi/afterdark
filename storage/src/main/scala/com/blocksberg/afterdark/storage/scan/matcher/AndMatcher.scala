package com.blocksberg.afterdark.storage.scan.matcher

import scalax.file.{Path, PathMatcher}


/**
 * matches if all sub matchers match
 */
class AllMatcher(matcher: PathMatcher[Path]*) extends PathMatcher {
  def apply(path: Path): Boolean = matcher.forall(m => m(path))
}

/**
 * matches if any (at least one) sub matcher matches
 */
class AnyMatcher(matcher: PathMatcher[Path]*) extends PathMatcher {
  def apply(path: Path): Boolean = matcher.exists(m => m(path))
}