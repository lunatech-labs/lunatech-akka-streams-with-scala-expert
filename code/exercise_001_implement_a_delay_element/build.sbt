Global / onChangedBuildSource := ReloadOnSourceChanges

Global / onLoad := {
    (Global / onLoad).value andThen (state => "project exercise" :: state)
  }

lazy val `advanced-akka-streams` = (project in file("."))
  .aggregate(
    core,
    exercise
  )
  .settings(ThisBuild / scalaVersion := Version.scalaVersion)
  .settings(CommonSettings.commonSettings: _*)

lazy val core = project
  .settings(CommonSettings.commonSettings: _*)

lazy val exercise = project
  .settings(CommonSettings.commonSettings: _*)
  .dependsOn(core % "test->test;compile->compile")
