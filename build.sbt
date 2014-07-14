name := "treasurer"

version := "1.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "joda-time" % "joda-time" % "2.3",
  "org.postgresql" % "postgresql" % "9.3-1100-jdbc41",
  "com.amazonaws" % "aws-java-sdk" % "1.8.4"
)

play.Project.playScalaSettings
