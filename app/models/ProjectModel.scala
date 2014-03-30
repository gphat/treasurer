package models

import anorm._
import anorm.SqlParser._
import org.joda.time.DateTime
import play.api.db.DB
import play.api.Play.current
import util.AnormExtension._

case class Project(
  id: Option[Long] = None,
  name: String,
  dateCreated: Option[DateTime] = Some(new DateTime())
)

object ProjectModel {

  val allQuery = SQL("SELECT * FROM projects")
  val getByIdQuery = SQL("SELECT * FROM projects WHERE id={id}")
  val insertQuery = SQL("INSERT INTO projects (name, date_created) VALUES ({name}, {date_created})")
  val deleteQuery = SQL("DELETE FROM projects WHERE id={id}")

  val project = {
    get[Pk[Long]]("id") ~
    get[String]("name") ~
    get[DateTime]("date_created") map {
      case id~name~dateCreated => Project(
        id = id.toOption,
        name = name,
        dateCreated = Some(dateCreated)
      )
    }
  }

  def create(project: Project): Option[Project] = {

    DB.withConnection { implicit conn =>
      insertQuery.on(
        'name -> project.name,
        'date_created -> project.dateCreated.getOrElse(new DateTime())
      ).executeInsert() map { id =>
        getById(id)
      } getOrElse(None)
    }
  }

  /**
   * Delete project.
   */
  def deleteById(id: Long) {
    DB.withConnection { implicit conn =>
      deleteQuery.on('id -> id).execute
    }
  }

  /**
   * Get all projects.
   */
  def getAll: List[Project] = {

    DB.withConnection { implicit conn =>
      allQuery.as(project.*)
    }
  }

  /**
   * Get a project by id.
   */
  def getById(id: Long): Option[Project] = {

    DB.withConnection { implicit conn =>
      getByIdQuery.on(
        'id -> id
      ).as(project.singleOpt)
    }
  }
}
