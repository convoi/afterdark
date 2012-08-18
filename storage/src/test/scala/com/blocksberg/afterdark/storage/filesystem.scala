package com.blocksberg.afterdark.storage

import scalax.file.FileSystem
import scalax.file.Path
import org.specs2.mutable.BeforeAfter
import org.specs2.mutable.After
import org.specs2.mutable.Before

trait TestFileSystem extends BeforeAfter {
  val root: Path
  override def after = root.deleteRecursively(true, false)
  override def before {}
}
trait emptyFileSystem extends TestFileSystem {
  override val root = FileSystem.default.createTempDirectory()
}
trait oneFileSystem extends emptyFileSystem{
  override def before {
    val singleFile = root / "test"
    singleFile.createFile(false, true)
    }
  }