package services

import java.io._
import scala.io.Source

/**
 * User: Muksia
 * Date: 04/10/15
 * Time: 09:21
 */
class FileService {

    def saveToFile(clanWarLog: String, clanWarLogName: String){

      val writer = new PrintWriter(new File(clanWarLogName ))
      writer.write(clanWarLog)
      writer.close()
    }

    def readFromFile(clanWarLogName: String) : String = {
      Source.fromFile(clanWarLogName ).mkString
    }

}
