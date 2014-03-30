package util

import models._
import org.joda.time.DateTime
import play.api.i18n.Messages

import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

object JsonFormats {

  val artifactReads: Reads[Artifact] = (
    (JsPath \ "id").read[String] and
    (JsPath \ "version").read[String] and
    (JsPath \ "dateCreated").readNullable[DateTime] and
    (JsPath \ "dateInternal").readNullable[DateTime]
  )(Artifact.apply _)

  val artifactWrites: Writes[Artifact] = (
    (JsPath \ "id").write[String] and
    (JsPath \ "version").write[String] and
    (JsPath \ "dateCreated").writeNullable[DateTime] and
    (JsPath \ "dateInternal").writeNullable[DateTime]
  )(unlift(Artifact.unapply))

  implicit val artifactFormat: Format[Artifact] = Format(
    artifactReads, artifactWrites
  )

  val deployReads: Reads[Deploy] = (
    (JsPath \ "id").readNullable[Long] and
    (JsPath \ "device").read[String] and
    (JsPath \ "artifactId").read[String] and
    (JsPath \ "dateCreated").readNullable[DateTime] and
    (JsPath \ "dateInternal").readNullable[DateTime]
  )(Deploy.apply _)

  val deployWrites: Writes[Deploy] = (
    (JsPath \ "id").writeNullable[Long] and
    (JsPath \ "device").write[String] and
    (JsPath \ "artifactId").write[String] and
    (JsPath \ "dateCreated").writeNullable[DateTime] and
    (JsPath \ "dateInternal").writeNullable[DateTime]
  )(unlift(Deploy.unapply))

  implicit val deployFormat: Format[Deploy] = Format(
    deployReads, deployWrites
  )

  val projectReads: Reads[Project] = (
    (JsPath \ "id").readNullable[Long] and
    (JsPath \ "name").read[String] and
    (JsPath \ "dateCreated").readNullable[DateTime]
  )(Project.apply _)

  val projectWrites: Writes[Project] = (
    (JsPath \ "id").writeNullable[Long] and
    (JsPath \ "name").write[String] and
    (JsPath \ "dateCreated").writeNullable[DateTime]
  )(unlift(Project.unapply))

  implicit val projectFormat: Format[Project] = Format(
    projectReads, projectWrites
  )
}
