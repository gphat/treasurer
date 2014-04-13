package controllers.api

import controllers.AuthenticatedAction
import models._
import play.api._
import play.api.libs.json.{Json,JsError}
import play.api.mvc._
import util.JsonFormats._

object Project extends Controller {

  def create = AuthenticatedAction(BodyParsers.parse.json) { request =>
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

  def delete(id: Long) = AuthenticatedAction {
    ProjectModel.deleteById(id)
    NoContent
  }

  def index = Action {
    Ok(Json.toJson(ProjectModel.getAll))
  }

  def item(id: Long) = Action {
    ProjectModel.getById(id) map { project =>
      Ok(Json.toJson(project))
    } getOrElse(NotFound)
  }
}
