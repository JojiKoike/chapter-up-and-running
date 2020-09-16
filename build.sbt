ThisBuild / organization := "com.goticks"
ThisBuild / version := "0.1"

ThisBuild / scalaVersion := "2.13.2"
ThisBuild / scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-Xlint",
  "-Ywarn-unused",
  "-Ywarn-dead-code",
  "-feature",
  "-language:_"
)

val akkaVersion     = "2.6.9"
val akkaHttpVersion = "10.2.0"
lazy val root = (project in file("."))
  .settings(
    name := "chapter-up-and-running",
    libraryDependencies ++= Seq(
        "com.typesafe.akka" %% "akka-actor"           % akkaVersion,
        "com.typesafe.akka" %% "akka-stream"          % akkaVersion,
        "com.typesafe.akka" %% "akka-http-core"       % akkaHttpVersion,
        "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
        "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
        "com.typesafe.akka" %% "akka-slf4j"           % akkaVersion,
        "ch.qos.logback"     % "logback-classic"      % "1.2.3",
        "com.typesafe.akka" %% "akka-testkit"         % akkaVersion % "test",
        "org.scalatest"     %% "scalatest"            % "3.2.2"     % "test"
      )
  )

ThisBuild / scalafmtConfig := file(".scalafmt.conf")
