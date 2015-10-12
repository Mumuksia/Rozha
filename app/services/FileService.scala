package services

import java.io._
import scala.io.Source
import play.api.libs.json.{JsArray, Json, JsObject}

/**
 * User: Muksia
 * Date: 04/10/15
 * Time: 09:21
 */
class FileService {

    val reservationFile = "reservations.txt"
    def saveToFile(row: String, fileName: String){

      val writer = new PrintWriter(new File(fileName))
      writer.write(row)
      writer.close()
    }

    def readFromFile(fileName: String) : String = {
      Source.fromFile(fileName).mkString
    }
    
    def saveReservation(row: JsObject){
        val fileContent = readFromFile(reservationFile)        
    }
    
    def updateReservations(reservationsList: String){
        saveToFile(reservationsList, reservationFile)        
    }
    
   def saveJsArrayToFile(arrayData: JsArray, fileName: String){
     saveToFile(JsArray.toString, fileName)
   } 

}
