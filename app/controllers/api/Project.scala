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
        ProjectModel.create(project) map { result =>
          Created(Json.toJson(result))
        } getOrElse {
          InternalServerError
        }
      },
      invalid = { errors =>
        BadRequest(Json.obj(
          "message" -> JsError.toFlatJson(errors)
        ))
      }
    )
  }

  def delete(id: Long) = Action {
    ProjectModel.deleteById(id)
    NoContent
  }
}
