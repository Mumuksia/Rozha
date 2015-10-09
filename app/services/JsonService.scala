package services

import java.io._
import scala.io.Source
import play.api.libs.json.{JsArray, Json, JsObject}
import java.util.UUID

/**
 * User: Muksia
 * Date: 04/10/15
 * Time: 09:21
 */
class JsonService {
  
  val JSON_KEY_Number = "Number"
  val JSON_KEY_Name = "Name"
  val JSON_KEY_APPROVED_BY = "ApprovedBy"
  val JSON_KEY_ID = "id"

    
    def transformToReservationRow(name: String, number: String, approvedBy: String) : JsObject = {
        Json.obj(JSON_KEY_ID -> UUID.randomUUID().toString, JSON_KEY_Name -> name, JSON_KEY_APPROVED_BY -> approvedBy, JSON_KEY_Number -> number);
    }

}
