name := "afterdark-storage"


version := "0.1"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq("org.specs2" %% "specs2" % "1.12" % "test"
  )


libraryDependencies += "com.github.scala-incubator.io" %% "scala-io-core" % "0.4.1-seq"

libraryDependencies += "com.github.scala-incubator.io" %% "scala-io-file" % "0.4.1-seq"

libraryDependencies += "junit" % "junit" % "4.7"

libraryDependencies += "org.mockito" % "mockito-all" % "1.9.0"

  // Read here for optional dependencies: 
  // http://etorreborre.github.com/specs2/guide/org.specs2.guide.Runners.html#Dependencies

  resolvers ++= Seq("snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
                    "releases"  at "http://oss.sonatype.org/content/repositories/releases")
// libraryDependencies += "com.github.oetzi" %% "echo" % "1.1.0"
