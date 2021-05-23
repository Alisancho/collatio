import sbtassembly.MergeStrategy

val dottyVersion     = "2.13.6"
lazy val akkaVersion = "2.6.14"
lazy val akkaHttp    = "10.2.4"

name := "collatio"
version := "0.1.0"

scalaVersion := dottyVersion

scalacOptions += "-target:jvm-1.8"

mainClass in assembly := Some("ru.finance.analyst.AppStart")

assemblyMergeStrategy in assembly := {
  case x if Assembly.isConfigFile(x)                                                      =>
    MergeStrategy.concat
  case PathList(ps @ _*) if Assembly.isReadme(ps.last) || Assembly.isLicenseFile(ps.last) =>
    MergeStrategy.rename
  case PathList("META-INF", xs @ _*)                                                      =>
    (xs map { _.toLowerCase }) match {
      case ("manifest.mf" :: Nil) | ("index.list" :: Nil) | ("dependencies" :: Nil) =>
        MergeStrategy.discard
      case ps @ (x :: xs) if ps.last.endsWith(".sf") || ps.last.endsWith(".dsa")    =>
        MergeStrategy.discard
      case "plexus" :: xs                                                           =>
        MergeStrategy.discard
      case "services" :: xs                                                         =>
        MergeStrategy.filterDistinctLines
      case ("spring.schemas" :: Nil) | ("spring.handlers" :: Nil)                   =>
        MergeStrategy.filterDistinctLines
      case _                                                                        => MergeStrategy.first
    }
  case _                                                                                  => MergeStrategy.first
}

libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"
libraryDependencies ++= Seq(
  ("com.typesafe.akka"  %% "akka-stream"                       % akkaVersion).cross(CrossVersion.for3Use2_13),
  ("com.typesafe.akka"  %% "akka-http"                         % akkaHttp).cross(CrossVersion.for3Use2_13),
  ("com.typesafe.akka"  %% "akka-http-spray-json"              % akkaHttp).cross(CrossVersion.for3Use2_13),
  ("com.lightbend.akka" %% "akka-stream-alpakka-elasticsearch" % "3.0.0").cross(CrossVersion.for3Use2_13),
  ("org.telegram"        % "telegrambots"                      % "4.7")
)
