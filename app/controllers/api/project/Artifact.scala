package controllers.api.project

import controllers.AuthenticatedAction
import java.io.File
import models._
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat
import play.api._
import play.api.libs.json.{Json,JsError}
import play.api.mvc._
import scala.util.Try
import util.JsonFormats._

object Artifact extends Controller {

  val dateFormat = ISODateTimeFormat.dateTimeParser()

  def create(projectId: String, id: String) = AuthenticatedAction(parse.temporaryFile) { request =>
    // XXX FIX ME
    request.body.moveTo(new File("/tmp/uploaded"))
    ArtifactModel.create(projectId, id, new File("/tmp/uploaded"))
    Ok("File uploaded")
  }

  def delete(projectId: String, id: String) = AuthenticatedAction {
    ArtifactModel.deleteById(projectId, id)
    NoContent
  }

  def item(projectId: String, id: String) = Action {
    ArtifactModel.getById(projectId, id) map { artifact =>
      Ok(Json.toJson(artifact))
    } getOrElse(NotFound)
  }
}
