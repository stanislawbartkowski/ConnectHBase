name := "HelloSpark"

version := "0.1"

scalaVersion := "2.11.8"
val sparkVersion = "2.3.2"

// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion
// https://mvnrepository.com/artifact/org.apache.spark/spark-sql
libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-streaming" % sparkVersion

// https://mvnrepository.com/artifact/org.apache.hbase.connectors.spark/hbase-spark
//libraryDependencies += "org.apache.hbase.connectors.spark" % "hbase-spark" % "1.0.0"

resolvers += "Horton repository" at "http://repo.hortonworks.com/content/repositories/releases"


// https://mvnrepository.com/artifact/org.apache.hbase/hbase-spark
libraryDependencies += "org.apache.hbase" % "hbase-spark" % "2.0.2.3.1.0.6-1"


// https://mvnrepository.com/artifact/com.hortonworks/shc-core
libraryDependencies += "com.hortonworks" % "shc-core" % "1.1.1-2.1-s_2.11"

// https://mvnrepository.com/artifact/org.apache.hbase.connectors.spark/hbase-spark
//libraryDependencies += "org.apache.hbase.connectors.spark" % "hbase-spark" % "1.0.0.7.0.3.0-79"









