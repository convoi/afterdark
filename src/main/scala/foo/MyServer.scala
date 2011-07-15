package foo


/**
 * User: hej
 * Date: 03.06.11
 * Time: 11:21
 */


import unfiltered.request._
import unfiltered.response._

object MyServer extends App{

  val sslEcho = unfiltered.filter.Planify {
    case _ => ResponseString("foo")
  }

  System.setProperty("jetty.ssl.keyStore", "keystore.bks")
  System.setProperty("jetty.ssl.keyStorePassword")
  unfiltered.jetty.Https.anylocal.filter(sslEcho).run()

}