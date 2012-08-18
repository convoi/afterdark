package com.blocksberg.afterdark.storage

import org.specs2.mutable._
import scan.Scanner
import com.blocksberg.afterdark.storage.scan.matcher.AllMatcher
import scalax.file.Path
import org.specs2.mutable.SpecificationWithJUnit
import org.junit.runner._
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import scalax.file.PathMatcherFactory
import scalax.file.PathMatcher

@RunWith(classOf[JUnitRunner])
class ScannerTest extends SpecificationWithJUnit {
  "A Scanner" should {
    "find no matching objects in an empty path" in new emptyFileSystem {
      new AllMatcherScanner {
        scanAll(root) must have size 0
      }
    }
    "find one matching objects in a path with one file" in new oneFileSystem {
    	new AllMatcherScanner {
    	  scanAll(root) must have size 1
    	}
    }
  }
}

trait AllMatcherScanner {
  val scanner = new Scanner()
  val matcher = PathMatcher.All
  val scanAll = scanner.scan(matcher)(_)
}
