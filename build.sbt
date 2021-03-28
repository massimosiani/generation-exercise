val dottyVersion = "0.24.0-RC1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "generation-algo",
    version := "0.1.0",

    scalaVersion := dottyVersion,

    libraryDependencies += "org.scalameta" %% "munit" % "0.7.7" % Test,
    testFrameworks += new TestFramework("munit.Framework")
  )
