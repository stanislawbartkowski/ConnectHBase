import org.apache.hadoop.hbase.TableName
import org.apache.hadoop.hbase.client.Table
import org.apache.spark
import org.apache.spark.sql.datasources.hbase.HBaseTableCatalog
import org.apache.hadoop.hbase.spark.HBaseContext
import org.apache.spark.sql.execution.datasources
import org.apache.hadoop.hbase.spark
//import org.json4s.jackson.JsonMethods.pa
import org.json4s
import org.apache.hadoop.hbase.spark
import org.apache.hadoop.conf.Configuration


object SparkHbase {

  val catalog=
    s"""{
       |"table":{"namespace":"default","name":"shakespearex"},
       |"rowkey":"key",
       |"columns":{
       |"key":{"cf":"rowkey","col":"key","type":"string"},
       |"line":{"cf":"cf1","col":"col","type":"string"}
       |}
       |}""".stripMargin

  def run(prop: Props): Unit = {
    val tableName = prop.getHBaseTable
    val fName = prop.getHBaseFamily
    val cName = prop.getHBaseColumn
    val tableN = TableName.valueOf(tableName)

    L.info("Open table " + tableName)
    val ss = SparkGetConf.get(prop)
    val connection = HbaseGetConn.get(prop)
    val table: Table = connection.getTable(tableN)
    import org.apache.hadoop.hbase.spark.HBaseContext

    val hbaseContext = new HBaseContext(ss.sparkContext,new Configuration())
    val hbaseDF = ss.read
      .options(Map(HBaseTableCatalog.tableCatalog -> catalog))
      .format("org.apache.hadoop.hbase.spark")
      .load()
    val num = hbaseDF.count()
    L.info("NUmber of " + num)

  }

}
