package com.blocksberg.afterdark.storage

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import scan.Scanner

/**
 * Created by IntelliJ IDEA.
 * User: jh
 * Date: 6/19/11
 * Time: 11:49 AM
 * To change this template use File | Settings | File Templates.
 */

class ScannerTest extends WordSpec with ShouldMatchers{
  "A Scanner" when {
    "scanning" in {
      val scanner = new Scanner()
    }
  }
}