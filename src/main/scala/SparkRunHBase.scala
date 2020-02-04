import org.apache.hadoop.hbase.TableName
import org.apache.hadoop.hbase.client.{Result, ResultScanner, Scan, Table}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.spark.rdd.RDD

object SparkRunHBase {

  private def readList(prop: Props): List[String] = {
    val tableName = prop.getHBaseTable
    val fName = prop.getHBaseFamily
    val cName = prop.getHBaseColumn

    val (_,connection) = HbaseGetConn.get(prop)
    val tableN = TableName.valueOf(tableName)
    val table: Table = connection.getTable(tableN)

    val scan = new Scan()
    scan.addColumn(Bytes.toBytes(fName), Bytes.toBytes(cName))
    L.info("Start scanning " + tableName + " " + fName + ":" + cName)
    val scanner = table.getScanner(scan)
    var res: Result = scanner.next()
    var reslist: List[String] = Nil
    var i : Int = 0
    while (res != null) {
      //      println("Scan")
      val row: Array[Byte] = res.getRow
      // println(row.length)
      val key = (row.map(_.toChar)).mkString
      if (i % 1000== 0) L.info(key)
      i = i+1
      val offset = res.getColumnLatestCell(Bytes.toBytes(fName), Bytes.toBytes(cName)).getValueOffset
      val len = res.getColumnLatestCell(Bytes.toBytes(fName), Bytes.toBytes(cName)).getValueLength
      //      println(len)
      //      println(offset)
      val cell = new Array[Byte](len)
      Array.copy(res.getColumnLatestCell(Bytes.toBytes(fName), Bytes.toBytes(cName)).getValueArray, offset, cell, 0, len)
      val s = (cell.map(_.toChar)).mkString
      //      println(s)
      res = scanner.next()
      reslist = reslist :+ s
    }
    scanner.close()
    reslist

  }

  def run(prop: Props): Unit = {

    val reslist: List[String] = readList(prop)

    val ss = SparkGetConf.get(prop)

    val textfile: RDD[String] = ss.sparkContext.parallelize(reslist)
    SparkTestJob.numberofwords(ss, prop, textfile)

  }

}
