package com.blocksberg.afterdark.storage

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.WordSpec
import scalax.file.Path

/**
 * Created by IntelliJ IDEA.
 * User: jh
 * Date: 6/15/11
 * Time: 10:17 PM
 * To change this template use File | Settings | File Templates.
 */

class FolderTest extends WordSpec with ShouldMatchers {
  "A Folder" when {
    "empty" should {
      "yield one element when iterated" in {
        val folder = new DBFolder(0, None, "foo", "foo")
        folder should have size 1
      }
    }
  }
}