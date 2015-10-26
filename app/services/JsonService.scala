package services

import java.io._
import scala.io.Source
import models.Reservations
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
  val JSON_KEY_NOTE = "Note"
  val JSON_KEY_NOTE_BY = "NoteBy"
  val JSON_KEY_STATUS = "Status"

    
    def transformToReservationRow(name: String, number: String, approvedBy: String) : JsObject = {
        Json.obj(JSON_KEY_ID -> UUID.randomUUID().toString, JSON_KEY_Name -> name, JSON_KEY_Number -> number, 
        JSON_KEY_STATUS -> "approved");
    }
    
      def transformToReservationRow(res: Reservations) : JsObject = {
        Json.obj(JSON_KEY_ID -> res.id.toString, JSON_KEY_Name -> res.name, JSON_KEY_Number -> res.number, 
        JSON_KEY_STATUS -> res.status);
    }
    
    def transformNoteToJsonRow(name: String, note: String, noteBy: String) : JsObject = {
      Json.obj(JSON_KEY_ID -> UUID.randomUUID().toString, JSON_KEY_Name -> name, JSON_KEY_NOTE_BY -> noteBy, JSON_KEY_NOTE -> note);
    }
    
  def transformToWishRow(name: String, number: String) : JsObject = {
    Json.obj(JSON_KEY_ID -> UUID.randomUUID().toString, JSON_KEY_Name -> name, JSON_KEY_Number -> number, JSON_KEY_STATUS -> "temporary");
  }

}
