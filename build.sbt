scriptClasspath := Seq("*")

scriptClasspath := "../conf" +: scriptClasspath.value

name := """Rozha"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, SbtWeb)
.settings(
scalaVersion := "2.11.6",
libraryDependencies ++= Seq(
  jdbc,
  cache,  
  ws,
  "org.webjars" %% "webjars-play" % "2.4.0-1",
  "org.webjars" % "react" % "0.13.3",
  "org.webjars" % "marked" % "0.3.2",
  "org.webjars" % "jquery" % "2.1.4",
  "org.webjars" % "bootstrap" % "3.1.1",
  "jp.t2v" %% "play2-auth"        % "0.14.1",
  "jp.t2v" %% "play2-auth-social" % "0.14.1",  
   "org.postgresql" % "postgresql" % "9.3-1102-jdbc41",
   "com.typesafe.play" %% "anorm" % "2.4.0",
"org.apache.poi" % "poi" % "3.9",
    "org.apache.poi" % "poi-ooxml" % "3.9"
),
    initialCommands := """
      import models._, utils._
      implicit val autoSession = AutoSession
    """
  )

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
