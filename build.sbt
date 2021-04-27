name := "learning-slick"

version := "0.1"

scalaVersion := "2.13.5"

libraryDependencies ++= Seq(
  "com.h2database" % "h2" % "1.4.200",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "ch.qos.logback" % "logback-classic" % "1.1.11",
  "org.scalatest" %% "scalatest" % "3.2.8" % Test

)
