import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

object SparkTestJob {

  def numberofwords(ss: SparkSession, prop: Props, textFile: RDD[String]) = {
    //word count
    L.info("Now calculate number of words")
    val counts = textFile.flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)

    counts.foreach(
      r => L.info(r._1 + " " + r._2)
    )
    L.info("Total words: " + counts.count());
    counts.saveAsTextFile(prop.getOutput);
  }

  def runSparkTest(prop: Props): Unit = {

    val ss = SparkGetConf.get(prop)

    // Load the text into a Spark RDD, which is a distributed representation of each line of text
    val textFile = ss.sparkContext.textFile(prop.getInput);
    numberofwords(ss, prop, textFile)
  }

}
