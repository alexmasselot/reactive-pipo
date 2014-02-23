
version := "0.1.0"

scalaVersion := "2.10.3"

organization := "pipo"

name := """reactive-pipo"""


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.2.1",
  "com.typesafe.akka" %% "akka-testkit" % "2.2.1",
  "org.specs2" %% "specs2" % "2.3.4" % "test",
  "org.scalatest" % "scalatest_2.10" % "2.0" % "test",
  "org.apache.commons" % "commons-math3" % "3.2"
)

// if you have more than one main method, you can specify which is used when typing 'run' in sbt
//mainClass := Some("com.mycode.App")

resolvers ++= Seq("snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "releases" at "http://oss.sonatype.org/content/repositories/releases"
)

scalacOptions ++= Seq("-unchecked", "-deprecation")

logLevel := Level.Info

