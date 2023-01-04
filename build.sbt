ThisBuild / version := "0.1"

ThisBuild / scalaVersion := "2.13.8"

libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0"

lazy val root = (project in file("."))
  .settings(
    name := "KMeans_vol2.0"
  )
