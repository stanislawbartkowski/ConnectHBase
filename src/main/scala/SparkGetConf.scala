import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

object SparkGetConf {

  def get(prop: Props): SparkSession = {
    val conf = new SparkConf()
    if (prop.getMaster != null) {
      conf.setMaster(prop.getMaster)
      conf.setAppName("Shakespeare WordCount")
    }
    SparkSession.builder.config(conf).getOrCreate()
  }

}
