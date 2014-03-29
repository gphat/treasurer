package models

import org.joda.time.DateTime

case class Project(
  id: Option[Int],
  name: String,
  dateCreated: DateTime = new DateTime()
)

object ProjectModel {

}
