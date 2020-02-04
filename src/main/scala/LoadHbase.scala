
import org.apache.hadoop.hbase.TableName
import org.apache.hadoop.hbase.client.{ColumnFamilyDescriptorBuilder, Put, Table, TableDescriptorBuilder}

import scala.io.Source

object LoadHbase {

  def loadHbaseTable(prop: Props): Unit = {

    val tableName = prop.getHBaseTable
    val fName = prop.getHBaseFamily
    val cName = prop.getHBaseColumn

    val (_,connection) = HbaseGetConn.get(prop)
    L.info("Check the existence of table " + tableName)
    val tableN = TableName.valueOf(tableName)
    if (connection.getAdmin.tableExists(tableN)) {
      L.info("Table exists, drop the table " + tableName)
      connection.getAdmin.disableTable(tableN)
      connection.getAdmin.deleteTable(tableN)
    }
    L.info("Now create table  " + tableName)
    val descr: TableDescriptorBuilder = TableDescriptorBuilder.newBuilder(tableN)
    val colB = ColumnFamilyDescriptorBuilder.of(fName)
    descr.setColumnFamily(colB)
    connection.getAdmin.createTable(descr.build())
    L.info("Table created. Now add lines to table " + tableName + " " + fName + ":" + cName)

    val filename = prop.getInput
    val table: Table = connection.getTable(tableN)
    var i: Integer = 0
    for (line <- Source.fromFile(filename).getLines) {
      i = i + 1;
      val rowkey = String.format("%010d", i);
      if (i % 1000 == 0) L.info(rowkey)
      val p: Put = new Put(rowkey.getBytes())
      p.addColumn(fName.getBytes(), cName.getBytes(), line.getBytes())
      table.put(p)
    }
  }

}
