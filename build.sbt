val scala3Version = "3.0.0-RC3"
lazy val AkkaVersion = "2.6.14"
//enablePlugins(ScalaJSPlugin)

//enablePlugins(ScalaJSBundlerPlugin)

lazy val commonSettings = Seq(
  version := "0.1-SNAPSHOT",
  organization := "ru.home",
  scalaVersion := scala3Version,
  test in assembly := {}
)

lazy val back = (project in file("back")).
  settings(commonSettings: _*).
  settings(
    assemblyJarName in assembly := "collatio-back.jar",
    mainClass in assembly := Some("ru.finance.analyst.AppStart"),
    name := "collatio-back",
    version := "0.1.0",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      ("org.typelevel" %% "cats-core" % "2.6.0").withDottyCompat(scalaVersion.value),
      ("org.typelevel" %% "cats-effect" % "3.1.0").withDottyCompat(scalaVersion.value),
      ("org.typelevel" %% "cats-core" % "2.5.0").withDottyCompat(scalaVersion.value),
      ("io.monix" %% "monix" % "3.3.0").withDottyCompat(scalaVersion.value),
      ("com.typesafe.akka" %% "akka-stream" % AkkaVersion).withDottyCompat(scalaVersion.value),
      ("com.typesafe.akka" %% "akka-http" % "10.2.4").withDottyCompat(scalaVersion.value),
      ( "com.typesafe.akka" %% "akka-http-spray-json" % "10.2.4").withDottyCompat(scalaVersion.value),
      ("org.telegram" % "telegrambots" % "4.7"),
      ("org.jetbrains" % "annotations" % "19.0.0"),
    ),
  )
//lazy val front = (project in file("front")).
//  settings(commonSettingsFront: _*).
//  settings(
//    scalaJSUseMainModuleInitializer := true,
//    name := "collatio-front",
//    version := "0.1.0",
//    mainClass in assembly := Some("ru.example.Main"),
//    libraryDependencies ++= Seq(
//      "org.scala-js" %%% "scalajs-dom" % "1.1.0"
////      "com.github.japgolly.scalajs-react" %%% "core" % "1.7.7"
//    )
//  )
