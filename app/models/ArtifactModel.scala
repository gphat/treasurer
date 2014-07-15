package models

import anorm._
import anorm.SqlParser._

import java.io.File
import org.joda.time.DateTime
// import play.api.db.DB
import play.api.Play.current
// import util.AnormExtension._

case class Artifact(
  id: String,
  version: String,
  url: String,
  dateCreated: Option[DateTime] = Some(new DateTime())
)

object ArtifactModel {

  // DONE
  // val allByProjectQuery = SQL("SELECT * FROM artifacts WHERE project_id={project_id}")
  // val insertQuery = SQL("INSERT INTO artifacts (id, project_id, version, url, date_created, date_internal) VALUES ({id}, {project_id}, {version}, {url}, {date_created}, {date_internal})")
  // val getByIdQuery = SQL("SELECT * FROM artifacts WHERE id={id} AND project_id={project_id}")

  // NOT
  // val getByDateQuery = SQL("SELECT * FROM artifacts WHERE project_id={project_id} AND date_created>={date} ORDER BY date_created ASC LIMIT 1")
  // val getByIndexQuery = SQL("SELECT * FROM artifacts WHERE project_id={project_id} ORDER BY date_created DESC OFFSET {offset} LIMIT 1")
  // val getLatestQuery = SQL("SELECT * FROM artifacts ORDER BY date_created DESC LIMIT 1")
  // val deleteQuery = SQL("DELETE FROM artifacts WHERE project_id={project_id} AND id={id}")

  // val artifact = {
  //   get[Pk[String]]("id") ~
  //   get[String]("version") ~
  //   get[String]("url") ~
  //   get[DateTime]("date_created") ~
  //   get[DateTime]("date_internal") map {
  //     case id~version~url~dateCreated~dateInternal => Artifact(
  //       id = id.get,
  //       version = version,
  //       url = url,
  //       dateCreated = Some(dateCreated),
  //       dateInternal = Some(dateInternal)
  //     )
  //   }
  // }

  def create(projectId: String, id: String, file: File): Option[Artifact] = {

    S3Model.createArtifact(projectId, id, file)
    Some(Artifact(id = id, version = "1.2.3", url = "http://www.example.com"))
    // DB.withConnection { implicit conn =>
    //   insertQuery.on(
    //     'id -> artifact.id,
    //     'project_id -> projectId,
    //     'version -> artifact.version,
    //     'url -> artifact.url,
    //     'date_created -> artifact.dateCreated.getOrElse(new DateTime()),
    //     'date_internal -> artifact.dateInternal.getOrElse(new DateTime())
    //   ).execute
    //   getById(projectId, artifact.id)
    // }
  }

  /**
   * Delete artifact.
   */
  def deleteById(projectId: String, id: String) {
    S3Model.deleteArtifact(projectId, id)
    // DB.withConnection { implicit conn =>
    //   deleteQuery.on(
    //     'id -> id,
    //     'project_id -> projectId
    //   ).execute
    // }
  }

  /**
   * Get all artifacts.
   */
  def getAllForProject(projectId: String): Seq[Artifact] = {

    S3Model.getArtifacts(projectId)
    // DB.withConnection { implicit conn =>
    //   allByProjectQuery.on('project_id -> projectId).as(artifact.*)
    // }
  }

  /**
   * Get artifact by date.
   */
  // def getByDate(projectId: Long, date: DateTime): Option[Artifact] = {

  //   // Hrm. Should this return the latest if it finds nothing else?
  //   DB.withConnection { implicit conn =>
  //     getByDateQuery.on(
  //       'project_id -> projectId,
  //       'date -> date
  //     ).as(artifact.singleOpt)
  //   }
  // }

  /**
   * Get an artifact by id.
   */
  def getById(projectId: String, id: String): Option[Artifact] = {

    S3Model.getArtifact(projectId, id)
    // DB.withConnection { implicit conn =>
    //   getByIdQuery.on(
    //     'id -> id,
    //     'project_id -> projectId
    //   ).as(artifact.singleOpt)
    // }
  }

  /**
   * Get artifact by index, offset from the current.
   */
  // def getByIndex(projectId: Long, offset: Int): Option[Artifact] = {

  //   DB.withConnection { implicit conn =>
  //     getByIndexQuery.on(
  //       'project_id -> projectId,
  //       'offset -> offset
  //     ).as(artifact.singleOpt)
  //   }
  // }

  /**
   * Get latest artifact.
   */
  // def getLatest(projectId: Long): Option[Artifact] = {

  //   DB.withConnection { implicit conn =>
  //     getLatestQuery.on(
  //       'project_id -> projectId
  //     ).as(artifact.singleOpt)
  //   }
  // }
}
