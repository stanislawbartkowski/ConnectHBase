import java.util.logging.Logger

object L {

  private val logger = Logger.getLogger(L.toString)

  def info(mess : String) : Unit = logger.info(mess)


}
