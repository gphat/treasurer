package controllers.api

import models._
import play.api._
import play.api.libs.json.{Json,JsError}
import play.api.mvc._
import util.JsonFormats._

object Project extends Controller {

  def create = Action(BodyParsers.parse.json) { request =>
    request.body.validate[Project] fold(
      valid = { project =>
        Created("ok")
      },
      invalid = { errors =>
        BadRequest(Json.obj(
          "status" -> "KO",
          "message" -> JsError.toFlatJson(errors)
        ))
      }
    )
  }
}
