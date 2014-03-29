package util

import models._
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.api.i18n.Messages
import play.api.libs.json.Json._
import play.api.libs.json._

object JsonFormats {

  val dateFormatter = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss'Z'")
  val dateFormatterUTC = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmss'Z'").withZoneUTC()

  implicit object ArtifactFormat extends Format[Artifact] {
    def reads(json: JsValue): JsResult[Artifact] = {
      JsSuccess(Artifact(
        id = (json \ "id").as[String],
        version = (json \ "id").as[String],
        dateCreated = (json \ "dateCreated").as[Option[String]] map { d =>
          dateFormatterUTC.parseDateTime(d)
        }  getOrElse new DateTime(),
        dateInternal = new DateTime()
      ))
    }

    def writes(obj: Artifact): JsValue = {
      toJson(Map(
        "id" -> JsString(obj.id),
        "version" -> JsString(obj.version),
        "dateCreated" -> JsString(dateFormatter.print(obj.dateCreated)),
        "dateInternal" -> JsString(dateFormatter.print(obj.dateCreated))
      ))
    }
  }

  implicit object DeployFormat extends Format[Deploy] {
    def reads(json: JsValue): JsResult[Deploy] = {
      JsSuccess(Deploy(
        id = (json \ "id").as[String],
        device = (json \ "device").as[String],
        artifactId = (json \ "artifactId").as[String],
        dateCreated = (json \ "dateCreated").as[Option[String]] map { d =>
          dateFormatterUTC.parseDateTime(d)
        }  getOrElse new DateTime(),
        dateInternal = new DateTime()
      ))
    }

    def writes(obj: Deploy): JsValue = {
      toJson(Map(
        "id" -> JsString(obj.id),
        "device" -> JsString(obj.device),
        "artifactId" -> JsString(obj.artifactId),
        "dateCreated" -> JsString(dateFormatter.print(obj.dateCreated)),
        "dateInternal" -> JsString(dateFormatter.print(obj.dateCreated))
      ))
    }
  }

implicit object ProjectFormat extends Format[Project] {
    def reads(json: JsValue): JsResult[Project] = {
      JsSuccess(Project(
        id = (json \ "id").as[String],
        name = (json \ "name").as[String],
        dateCreated = (json \ "dateCreated").as[Option[String]] map { d =>
          dateFormatterUTC.parseDateTime(d)
        }  getOrElse new DateTime()
      ))
    }

    def writes(obj: Project): JsValue = {
      toJson(Map(
        "id" -> JsString(obj.id),
        "name" -> JsString(obj.name),
        "dateCreated" -> JsString(dateFormatter.print(obj.dateCreated))
      ))
    }
  }
}
