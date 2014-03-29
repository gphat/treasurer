package controllers

import models._
import play.api._
import play.api.libs.json.{Json,JsError}
import play.api.mvc._
import util.JsonFormats._

object Project extends Controller {

  def index = Action {
    Ok("ok")
  }

  def create = Action(BodyParsers.parse.json) { request =>
    // request.body map { json =>
    //   Json.fromJson[Project](json) fold(
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
    // } getOrElse(BadRequest("Expected JSON body"))
  }
}
