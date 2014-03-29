package models

import org.joda.time.DateTime

case class Project(
  id: String,
  name: String,
  dateCreated: DateTime
)

object ProjectModel {

}
