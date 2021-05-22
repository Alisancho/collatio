import sbtassembly.MergeStrategy

val scala3Version    = "3.0.0-RC3"
lazy val AkkaVersion = "2.6.14"

lazy val commonSettings = Seq(
  version := "0.1-SNAPSHOT",
  organization := "ru.home",
  scalaVersion := scala3Version
)

lazy val back = (project in file("back"))
  .settings(commonSettings: _*)
  .settings(
    assemblyJarName in assembly := "collatio-back.jar",
    mainClass in assembly := Some("ru.finance.analyst.AppStart"),
    name := "collatio-back",
    version := "0.1.0",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      ("io.monix"            % "monix_2.13"                             % "3.3.0").withDottyCompat(scalaVersion.value),
      ("com.typesafe.akka"   % "akka-stream_2.13"                       % AkkaVersion).withDottyCompat(scalaVersion.value),
      ("com.typesafe.akka"   % "akka-http_2.13"                         % "10.2.4").withDottyCompat(scalaVersion.value),
      ("com.typesafe.akka"   % "akka-http-spray-json_2.13"              % "10.2.4").withDottyCompat(scalaVersion.value),
      ("com.lightbend.akka"  % "akka-stream-alpakka-elasticsearch_2.13" % "3.0.0").withDottyCompat(scalaVersion.value),
      ("org.telegram"        % "telegrambots"                           % "4.7"),
      "ch.qos.logback"       % "logback-classic"                        % "1.2.3",
      "ch.qos.logback"       % "logback-classic"                        % "1.2.3" % Test,
      "net.logstash.logback" % "logstash-logback-encoder"               % "6.6"
    )
  )
