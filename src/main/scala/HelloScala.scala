object HelloScala {

  def main(args: Array[String]) {

    val SPARKTEST = "sparktest"
    val HBASELOAD = "hbaseload"
    val HBASESPARK = "hbasespark"

    def P(s: String): Unit = println(s)

    def printHelp: Unit = {
      P("Run several tasks related to HBase and Spark")
      P("")
      P("Args: <param> <what>")
      P("   <param> : parameter file")
      P("   <what> : ")
      P("      " + SPARKTEST + " simple spark test, word count")
      P("      " + HBASELOAD + " load data into HBase table")
      P("      " + HBASESPARK + " run spark job using HBase table")
      System.exit(4)
    }

    if (args.length != 2) printHelp;

    val propname = args(0)

    val prop = new Props()
    L.info("Load properties from " + propname)
    prop.load(propname)
    args(1) match {
      case SPARKTEST => SparkTestJob.runSparkTest(prop)
      case HBASELOAD => LoadHbase.loadHbaseTable(prop)
      case HBASESPARK => SparkRunHBase.run(prop)
      case _ => printHelp
    }

    //SparkTestJob.runSparkTest(prop)
    //LoadHbase.loadHbaseTable(prop)
    //SparkHbase.run(prop)
    //HBaseSparkInsert.run(prop)
    //SparkRunHBase.run(prop)

  }

}