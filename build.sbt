name := "treasurer"

version := "0.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.29"
)

play.Project.playScalaSettings
