package models

import org.joda.time.DateTime

case class Project(
  id: Int,
  name: String,
  dateCreated: DateTime = new DateTime()
)

object ProjectModel {

}
