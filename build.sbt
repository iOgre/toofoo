
val scala3Version = "3.0.0"
val scala2Version = "2.13.6"

lazy val root = project
  .in(file("."))
  .settings(   
    name := "toofoo",
    version := "0.1.0",
    scalaVersion := scala2Version,
    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test"
//    libraryDependencies += "org.typelevel" %% "kind-projector" % "0.13.0"
 )

addCompilerPlugin("org.typelevel" % "kind-projector" % "0.13.0" cross CrossVersion.full)
