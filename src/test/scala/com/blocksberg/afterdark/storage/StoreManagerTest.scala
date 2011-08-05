package com.blocksberg.afterdark.storage

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import scan.{Scanner, Rumba20HashCalculator, Rumba20Hash}

/**
 * User: hej
 * Date: 15.07.11
 * Time: 13:35
 */

class StoreManagerTest extends WordSpec with ShouldMatchers {
  "A StoreManager" when {
    "updating Share Hashes" in {
      val scanner = new Scanner()
      val hashStrategy = new Rumba20HashCalculator()
      val manager = new StoreManager(scanner, hashStrategy)
      manager.updateShareHashes()
    }
  }
}