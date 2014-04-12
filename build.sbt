name := "treasurer"

version := "1.0"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "joda-time" % "joda-time" % "2.3",
  "org.postgresql" % "postgresql" % "9.3-1100-jdbc41"
)

play.Project.playScalaSettings
