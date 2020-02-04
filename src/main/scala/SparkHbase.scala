import org.apache.hadoop.hbase.TableName
import org.apache.hadoop.hbase.client.{Connection, Table}
import org.apache.spark.sql.datasources.hbase.HBaseTableCatalog
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.spark.HBaseContext
import org.apache.spark.SparkConf
import org.apache.hadoop.security.UserGroupInformation
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.Row


object SparkHbase {

  val catalogpatt =
    s"""{
       |"table":{"namespace":"default","name":"#table"},
       |"rowkey":"key",
       |"columns":{
       |"key":{"cf":"rowkey","col":"key","type":"string"},
       |"line":{"cf":"#columnfamily","col":"#columnname","type":"string"}
       |}
       |}""".stripMargin


  def run(prop: Props): Unit = {
    val tableName = prop.getHBaseTable
    val fName = prop.getHBaseFamily
    val cName = prop.getHBaseColumn
    val tableN = TableName.valueOf(tableName)

    val catalog = catalogpatt.replaceFirst("#table", tableName)
      .replace("#columnfamily", fName)
      .replace("#columnname", cName);


    L.info("Open table " + tableName)
    val ss = SparkGetConf.get(prop)

    val (confh: Configuration, connection: Connection) = HbaseGetConn.get(prop)
    //    val table: Table = connection.getTable(tableN)


    val hbaseContext = new HBaseContext(ss.sparkContext, confh)
    val hbaseDF = ss.read
      .options(Map(HBaseTableCatalog.tableCatalog -> catalog))
      .format("org.apache.hadoop.hbase.spark")
      .load()
    val num = hbaseDF.count()
    L.info("Number of " + num)
    // DataFrame is load, transform to RDD<String> to run WordCount
    hbaseDF.printSchema()
    var rdd: RDD[Row] = hbaseDF.rdd
    //    val num2 = rdd.count()
    //    val r : Array[Row] = rdd.take(5)
    //    println(r(2).getAs(1))
    val textfile: RDD[String] = hbaseDF.rdd.map({
      row => if (row.getAs(1) == null) "" else row.getAs(1).toString
    })
    //    val i = 1;
    //    val num1 = textfile.count()
    SparkTestJob.numberofwords(ss, prop, textfile)
  }

}
