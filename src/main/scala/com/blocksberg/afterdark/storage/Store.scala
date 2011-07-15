package com.blocksberg.afterdark.storage

import java.security.PrivateKey
import com.blocksberg.afterdark.storage.scan.matcher.IsModifiedSince
import com.blocksberg.afterdark.storage.scan.{Rumba20HashCalculator, Scanner}
import scalax.file.{Path, PathMatcher}
import scala.collection.Iterator

/**
 * Created by IntelliJ IDEA.
 * User: jh
 * Date: 6/15/11
 * Time: 9:16 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Store is a container for shares
 */
class Store(private var shares:Seq[DBFolder]) extends Iterable[DBFolder]{
  private val scanner = new Scanner()
  private var lastScan:Long = 0L


  def iterator: Iterator[DBFolder] = shares.iterator

  def scanAllShares = {
    val newLastScan = System.currentTimeMillis
    val matcher = new IsModifiedSince(lastScan)

    shares.foreach(s => scanner.scan(matcher)(s.path))

    lastScan = newLastScan
  }


}