import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory}
import org.apache.hadoop.security.UserGroupInformation

object HbaseGetConn {

  def get(prop : Props) : Connection = {
    val conf = HBaseConfiguration.create()
    conf.set("hadoop.security.authentication", "kerberos")
    conf.set("hbase.security.authentication", "kerberos")

    //Now you need to login/authenticate using keytab:
    UserGroupInformation.setConfiguration(conf);
    val ugi: UserGroupInformation = UserGroupInformation.loginUserFromKeytabAndReturnUGI("sb@CENTOS.COM.REALM", "/home/sbartkowski/bin/keytabs/sb.keytab");
    ugi.reloginFromKeytab

    ConnectionFactory.createConnection(conf)
  }


}
