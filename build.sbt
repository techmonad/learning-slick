name := "learning-slick"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "com.h2database" % "h2" % "1.4.187",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.0",
  "com.typesafe.slick" %% "slick" % "3.3.0",
  "ch.qos.logback" % "logback-classic" % "1.1.3",
  "org.scalatest" %% "scalatest" % "3.0.5" % Test

)
