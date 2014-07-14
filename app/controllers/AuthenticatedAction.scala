package controllers

import play.api._
import play.api.Logger
import play.api.mvc._
import play.api.mvc.Results._
import play.api.Play.current
import scala.concurrent.Future

object AuthenticatedAction extends ActionBuilder[Request] {

  val maybeToken = Play.configuration.getString("treasurer.token")

  def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    maybeToken map { authToken =>
      // If we have a token in the config, we'll check it!
      request.headers.get("Authorization") map { token =>
        if(token == authToken) {
          block(request)
        } else {
          Future.successful(Unauthorized)
        }
      } getOrElse {
        Future.successful(Unauthorized)
      }
    } getOrElse {
      block(request)
    }
  }
}
