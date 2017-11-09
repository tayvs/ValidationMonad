
normalizedName := "validation-utils"
organization := "com.github.tayvs"

scalaVersion := "2.12.3"
crossScalaVersions := Seq("2.10.6", "2.11.8", "2.12.3")
scalacOptions := Seq(
  "-target:jvm-1.8",
  "-unchecked",
  "-deprecation",
  "-encoding",
  "utf8",
  "-feature",
  "-language:implicitConversions",
  "-language:reflectiveCalls",
  "-language:postfixOps"
)

autoCompilerPlugins := true
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)