name := """treasurer"""

version := "2.0.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "joda-time" % "joda-time" % "2.3",
  "org.postgresql" % "postgresql" % "9.3-1100-jdbc41"
)
