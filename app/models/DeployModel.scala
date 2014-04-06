package models

import anorm._
import anorm.SqlParser._
import org.joda.time.DateTime
import play.api.db.DB
import play.api.Play.current
import util.AnormExtension._

case class Deploy(
  id: Option[Long] = None,
  device: String,
  artifactId: String,
  dateCreated: Option[DateTime] = Some(new DateTime()),
  dateInternal: Option[DateTime] = Some(new DateTime())
)

object DeployModel {

  val allByProjectQuery = SQL("SELECT * FROM deploys WHERE project_id={project_id}")
  val getByIdQuery = SQL("SELECT * FROM deploys WHERE id={id} AND project_id={project_id}")
  val deleteQuery = SQL("DELETE from deploys WHERE project_id={project_id} AND id={id}")
  val insertQuery = SQL("INSERT INTO deploys (project_id, device, artifact_id, date_created, date_internal) VALUES ({project_id}, {device}, {artifact_id}, {date_created}, {date_internal})")

  val deploy = {
    get[Pk[Long]]("id") ~
    get[String]("device") ~
    get[String]("artifact_id") ~
    get[DateTime]("date_created") ~
    get[DateTime]("date_internal") map {
      case id~device~artifactId~dateCreated~dateInternal => Deploy(
        id = Some(id.get),
        device = device,
        artifactId = artifactId,
        dateCreated = Some(dateCreated),
        dateInternal = Some(dateInternal)
      )
    }
  }

  def create(projectId: Long, deploy: Deploy): Option[Deploy] = {

    DB.withConnection { implicit conn =>
      insertQuery.on(
        'project_id -> projectId,
        'device -> deploy.device,
        'artifact_id -> deploy.artifactId,
        'date_created -> deploy.dateCreated.getOrElse(new DateTime()),
        'date_internal -> deploy.dateInternal.getOrElse(new DateTime())
      ).executeInsert() map { id =>
        getById(projectId, id)
      } getOrElse { None }
    }
  }

  /**
   * Delete deploy.
   */
  def deleteById(projectId: Long, id: Long) {
    DB.withConnection { implicit conn =>
      deleteQuery.on(
        'id -> id,
        'project_id -> projectId
      ).execute
    }
  }

  /**
   * Get all deploys for a project.
   */
  def getAllForProject(projectId: Long): List[Deploy] = {

    DB.withConnection { implicit conn =>
      allByProjectQuery.on('project_id -> projectId).as(deploy.*)
    }
  }

  /**
   * Get a deploy by id.
   */
  def getById(projectId: Long, id: Long): Option[Deploy] = {

    DB.withConnection { implicit conn =>
      getByIdQuery.on(
        'id -> id,
        'project_id -> projectId
      ).as(deploy.singleOpt)
    }
  }
}
