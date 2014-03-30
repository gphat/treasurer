package models

import anorm._
import anorm.SqlParser._
import org.joda.time.DateTime
import play.api.db.DB
import play.api.Play.current
import util.AnormExtension._

case class Artifact(
  id: String,
  version: String,
  url: String,
  dateCreated: Option[DateTime] = Some(new DateTime()),
  dateInternal: Option[DateTime] = Some(new DateTime())
)

object ArtifactModel {

  val allByProjectQuery = SQL("SELECT * FROM artifacts WHERE project_id={project_id}")
  val getByIdQuery = SQL("SELECT * FROM artifacts WHERE id={id} AND project_id={project_id}")
  val insertQuery = SQL("INSERT INTO artifacts (id, project_id, version, url, date_created, date_internal) VALUES ({id}, {project_id}, {version}, {url}, {date_created}, {date_internal})")
  val deleteQuery = SQL("DELETE FROM artifacts WHERE project_id={project_id} AND id={id}")

  val artifact = {
    get[Pk[String]]("id") ~
    get[String]("version") ~
    get[String]("url") ~
    get[DateTime]("date_created") ~
    get[DateTime]("date_internal") map {
      case id~version~url~dateCreated~dateInternal => Artifact(
        id = id.get,
        version = version,
        url = url,
        dateCreated = Some(dateCreated),
        dateInternal = Some(dateInternal)
      )
    }
  }

  def create(projectId: Long, artifact: Artifact): Option[Artifact] = {

    DB.withConnection { implicit conn =>
      insertQuery.on(
        'id -> artifact.id,
        'project_id -> projectId,
        'version -> artifact.version,
        'url -> artifact.url,
        'date_created -> artifact.dateCreated.getOrElse(new DateTime()),
        'date_internal -> artifact.dateInternal.getOrElse(new DateTime())
      ).execute
      getById(projectId, artifact.id)
    }
  }

  /**
   * Delete artifact.
   */
  def deleteById(projectId: Long, id: String) {
    DB.withConnection { implicit conn =>
      deleteQuery.on(
        'id -> id,
        'project_id -> projectId
      ).execute
    }
  }

  /**
   * Get all artifacts.
   */
  def getAllForProject(projectId: Long): List[Artifact] = {

    DB.withConnection { implicit conn =>
      allByProjectQuery.on('project_id -> projectId).as(artifact.*)
    }
  }

  /**
   * Get an artifact by id.
   */
  def getById(projectId: Long, id: String): Option[Artifact] = {

    DB.withConnection { implicit conn =>
      getByIdQuery.on(
        'id -> id,
        'project_id -> projectId
      ).as(artifact.singleOpt)
    }
  }
}
