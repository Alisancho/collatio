
val scala3Version    = "3.0.0-RC3"
lazy val akkaVersion = "2.6.14"
lazy val akkaHttp = "10.2.4"

lazy val commonSettings = Seq(
  version := "0.1-SNAPSHOT",
  organization := "ru.home",
  scalaVersion := scala3Version,
)

lazy val back = (project in file("back"))
  .settings(commonSettings: _*)
  .settings(
    assemblyJarName in assembly := "collatio-back.jar",
    mainClass in assembly := Some("ru.finance.analyst.AppStart"),
    name := "collatio-back",
    version := "0.1.0",
    scalaVersion := scala3Version,
    Global / onChangedBuildSource := IgnoreSourceChanges,
    libraryDependencies ++= Seq(
      ("com.typesafe.akka"   % "akka-stream_2.13"                       % akkaVersion).withDottyCompat(scalaVersion.value),
      ("com.typesafe.akka"   % "akka-http_2.13"                         % akkaHttp).withDottyCompat(scalaVersion.value),
      ("com.typesafe.akka"   % "akka-http-spray-json_2.13"              % akkaHttp).withDottyCompat(scalaVersion.value),
      ("com.lightbend.akka"  % "akka-stream-alpakka-elasticsearch_2.13" % "3.0.0").withDottyCompat(scalaVersion.value),
      ("org.telegram"        % "telegrambots"                           % "4.7").withDottyCompat(scalaVersion.value),
    )
  )
