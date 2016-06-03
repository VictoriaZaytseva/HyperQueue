import java.io.{PrintWriter, StringWriter}

import play.api.Play.current
import play.api.http.HeaderNames._
import play.api.i18n.Messages
import play.api.mvc.Results._
import play.api.mvc._
import play.api.{Application, GlobalSettings, Logger}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Global extends GlobalSettings {
  /**
    * Global action composition.
    */
  override def doFilter(action: EssentialAction): EssentialAction = EssentialAction { request =>


    Logger.debug("Request: " + request.toString())
        val ACAOheader = request.headers.get("Origin") match {
          case Some(origin: String) => origin
          case _ => "NULL"
        }
    if (request.toString().startsWith("GET ") ||
      request.toString().startsWith("POST ")) {
      action.apply(request).map(_.withHeaders(
        PRAGMA -> "no-cache",
        CACHE_CONTROL -> "no-store, no-cache, must-revalidate, max-age=0",
        EXPIRES -> "Sat, 26 Jul 1997 05:00:00 GMT",
        ACCESS_CONTROL_ALLOW_ORIGIN -> ACAOheader,
        ACCESS_CONTROL_ALLOW_METHODS -> "GET POST",
        ACCESS_CONTROL_ALLOW_HEADERS ->  s"$ORIGIN, X-Requested-With, $CONTENT_TYPE, $ACCEPT, $AUTHORIZATION, X-Auth-Token"
      ))
    }
    else {
      action.apply(request).map(_.withHeaders(
      ACCESS_CONTROL_ALLOW_ORIGIN -> ACAOheader,
      ACCESS_CONTROL_ALLOW_METHODS -> "GET POST",
      ACCESS_CONTROL_ALLOW_HEADERS ->  s"$ORIGIN, X-Requested-With, $CONTENT_TYPE, $ACCEPT, $AUTHORIZATION, X-Auth-Token"))
    }
  }
}