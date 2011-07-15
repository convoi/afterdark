package com.blocksberg.afterdark.service.http

/**
 * User: hej
 * Date: 03.06.11
 * Time: 11:21
 */


import unfiltered.request._
import unfiltered.response._
import org.eclipse.jetty.server.ssl.SslConnector
import com.sun.java.swing.plaf.windows.WindowsTableHeaderUI
import unfiltered.jetty.{Trusted, Server, Https, Ssl}
import java.security.cert.X509Certificate
import javax.management.remote.rmi._RMIConnection_Stub
import unfiltered.filter.Planify
import sun.security.provider.certpath.OCSPResponse.ResponseStatus

trait NeedClientCert { self : Server with Ssl =>
  sslConn.setNeedClientAuth(true)
  sslConn.setWantClientAuth(true)
  sslConn.setResolveNames(false)
  sslConn.setPassword("test")
  //sslConn.set
}

object MyHttps {
  /** bind to the given port for any host */
  def apply(port: Int): MyHttps = MyHttps(port, "0.0.0.0")
  /** bind to a the loopback interface only */
  def local(port: Int) = MyHttps(port, "127.0.0.1")
  /** bind to any available port on the loopback interface */
  def anylocal = local(unfiltered.util.Port.any)
}
case class MyHttps(port: Int, host: String) extends Server with Ssl with Trusted with NeedClientCert{
  val url = "http://%s:%d/" format (host, port)
  def sslPort = port
  sslConn.setHost(host)
}


object MyServer extends App{

  val sslEcho = Planify {


    case GET(i) => {
      val certs = i.underlying.getAttribute("javax.servlet.request.X509Certificate").asInstanceOf[Array[X509Certificate]];
      val res = certs.foldLeft("Hello: ")(_ + " name: "+_.getSubjectDN.getName)

      ResponseString(res)

    }
  }

  val encrypted = Planify {
    case req @ Path(Seg("enc":: tail)) => (req,tail) match {
      case (req,"file" :: tail) => (req,tail) match {
        case (GET(_), id::Nil) => ResponseString("retrieve File with id : "+id)
        case (PUT(_), id::Nil) => ResponseString("store File with id : "+ id)
        case _ => MethodNotAllowed ~> ResponseString("not allowed")
      }
      case (req, "permission":: tail) => (req, tail) match {
        case (PUT(_), id::Nil) => ResponseString("set FilePermissions for id : "+id)
        case _ => MethodNotAllowed ~> ResponseString("not allowed")
      }
      case _ => MethodNotAllowed
    }

  }
/*  val encryptedFileRequest = Planify {
    case req @ GET(Path(Seg("enc":: "file":: id :: Nil))) => {   req
      ResponseString("unsupported")
    }
    case req @ PUT(Path(Seg("enc" :: "file":: id :: Nil))) => {
      ResponseString("unsupported")
    }
    case req @ PUT(Path(Seg("enc" :: "permissions":: id :: Nil))) => {
      ResponseString("unsupported")
    }
  }

  val unencryptedFileRequest = Planify {
    case req @ GET(Path(Seg("unenc" :: "browse":: path :: Nil))) => {
      ResponseString("unsupported")
    }
    case req @ GET(Path(Seg("unenc" :: "file" :: path :: Nil))) => {
      ResponseString("unsupported")
    }
    case req @ PUT(Path(Seg("unenc" :: "file" :: path :: Nil))) => {
      ResponseString("unsupported")
    }

  }*/

  java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
  System.setProperty("jetty.ssl.keyStore", "keystore.jks")
  System.setProperty("jetty.ssl.trustStore", "keystore.jks")
  System.setProperty("jetty.ssl.keyStorePassword", "test")
  System.setProperty("jetty.ssl.trustStorePassword", "test")
  MyHttps.local(8080).filter(encrypted).filter(sslEcho).run()

}