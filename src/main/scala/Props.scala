import java.io.FileInputStream
import java.util.Properties

class Props {

  private val prop : Properties = new Properties();
  private val inputfile = "infile";
  private val outputfile = "outfile"
  private val hbasetable = "hbasetable"
  private val hbasefamily = "hbasefamily"
  private val hbasecolumn = "hbasecolumn"
  private val master = "master"

  def load(propfilename : String): Unit = {
    prop.load(new FileInputStream(propfilename));
  }

  def getInput : String = prop.getProperty(inputfile)

  def getOutput: String = prop.getProperty(outputfile)

  def getHBaseTable : String = prop.getProperty(hbasetable)

  def getHBaseFamily : String = prop.getProperty(hbasefamily)

  def getHBaseColumn : String = prop.getProperty(hbasecolumn)

  def getMaster : String = prop.getProperty(master)


}
