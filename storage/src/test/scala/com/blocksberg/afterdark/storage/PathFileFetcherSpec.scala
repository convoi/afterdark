package com.blocksberg.afterdark.storage

import org.specs2.specification.Fragments
import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.main.ArgProperty
import scala.collection.Seq
import scalax.file.Path

class PathFileFetcherSpec extends SpecificationWithJUnit {

  "A PathFileFetcher" should {
    "find a file relative to the given root path" in new oneFileSystem{
      val pathFileFetcher = new PathFileFetcherImpl(root)
      pathFileFetcher find(Path("test")) must be some
    }
    "not find a non existing file" in new oneFileSystem {
      val pathFileFetcher = new PathFileFetcherImpl(root)
      pathFileFetcher find(Path("notexisting")) must be none
    }
    "find a file in a subfolder of the root path" in new oneFileSystem{
      todo
    }
    
  }

}