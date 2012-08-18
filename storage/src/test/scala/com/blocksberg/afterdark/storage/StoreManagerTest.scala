package com.blocksberg.afterdark.storage

import hash.Rumba20HashCalculator
import scan.Scanner
import com.blocksberg.afterdark.storage.hash.Rumba20Hash
import org.specs2.mutable.SpecificationWithJUnit
import org.junit.rules.TemporaryFolder
import scalax.file.defaultfs.DefaultFileOps
import scalax.file.defaultfs.DefaultFileSystem
import scalax.file.defaultfs.DefaultFileSystem
import scalax.file.Path
import scalax.file.FileSystem
import hash.MD5HashCalculator

class StoreManagerTest extends SpecificationWithJUnit {
  "A StoreManager updating Share Hashes" in {
      val scanner = new Scanner()
      val hashStrategy = new MD5HashCalculator()
//      val manager = new StoreManager(scanner, hashStrategy)
//      manager.updateShareHashes()
  }
}

