package services

import java.util.UUID

import models.{Account, Notes, Participation, Reservations, User, War}
import play.api.libs.json.{JsArray, JsObject, Json}

/**
 * User: Muksia
 * Date: 04/10/15
 * Time: 09:21
 */
class JsonService {
  
  val JSON_KEY_Number = "Number"
  val JSON_KEY_Name = "Name"
  val JSON_KEY_Email = "Email"
  val JSON_KEY_Role = "Role"
  val JSON_KEY_APPROVED_BY = "ApprovedBy"
  val JSON_KEY_ID = "id"
  val JSON_KEY_NOTE = "Note"
  val JSON_KEY_NOTE_BY = "NoteBy"
  val JSON_KEY_STATUS = "Status"
  val JSON_KEY_SCORE = "Score"
  val JSON_KEY_START_DATE = "StartDate"
  val JSON_KEY_START_DESC = "Description"
  val JSON_KEY_CLAN_ID = "ClanId"
  val JSON_KEY_REMOTE_ADDRESS = "RemoteAddress"
  val JSON_KEY_USERS = "Users"
  val JSON_KEY_PENALTIES = "Penalties"

    
    def transformToReservationRow(name: String, number: String, approvedBy: String) : JsObject = {
        Json.obj(JSON_KEY_ID -> UUID.randomUUID().toString, JSON_KEY_Name -> name, JSON_KEY_Number -> number, 
        JSON_KEY_STATUS -> "approved");
    }
    
      def transformToReservationRow(res: Reservations) : JsObject = {
        Json.obj(JSON_KEY_ID -> res.id.toString, JSON_KEY_Name -> res.name, JSON_KEY_Number -> res.number, 
        JSON_KEY_STATUS -> res.status, JSON_KEY_REMOTE_ADDRESS -> res.remoteAddress);
    }
    
    def transformNoteToJsonRow(note: Notes) : JsObject = {
      Json.obj(JSON_KEY_ID -> note.id.toString, JSON_KEY_Name -> note.name, JSON_KEY_NOTE_BY -> note.addedby, JSON_KEY_STATUS -> note.status,
      JSON_KEY_START_DESC-> note.description);
    }
    
      def transformUserToJsonRow(user: User) : JsObject = {
      Json.obj(JSON_KEY_ID -> user.id.toString, JSON_KEY_Name -> user.name, JSON_KEY_STATUS -> user.status,
      JSON_KEY_CLAN_ID-> user.clanId, JSON_KEY_REMOTE_ADDRESS-> user.remoteAddress);
    }
    
        def transformAccountToJsonRow(account: Account) : JsObject = {
      Json.obj(JSON_KEY_ID -> account.id.toString, JSON_KEY_Name -> account.name, JSON_KEY_Email -> account.email,
      JSON_KEY_Role-> account.role.toString);
    }
    
    def transformUserDTOToJsonRow(user: UserDTO) : JsObject = {
      Json.obj(JSON_KEY_ID -> user.id.toString, JSON_KEY_Name -> user.name, JSON_KEY_STATUS -> user.status,
      JSON_KEY_CLAN_ID-> user.clanId, JSON_KEY_REMOTE_ADDRESS-> user.remoteAddress, JSON_KEY_Number -> user.penalties.size);
    }
    
      def transformParticipationToJsonRow(participation: Participation) : JsObject = {
      Json.obj(JSON_KEY_ID -> participation.id.toString, JSON_KEY_Name -> participation.name, JSON_KEY_STATUS -> participation.status);
    }
    
  def transformToWishRow(name: String, number: String) : JsObject = {
    Json.obj(JSON_KEY_ID -> UUID.randomUUID().toString, JSON_KEY_Name -> name, JSON_KEY_Number -> number, JSON_KEY_STATUS -> "temporary");
  }
  
  def transformReservationsToJsArray(reservations: Seq[Reservations]) : JsArray = {
    val jsData = reservations.map( x => transformToReservationRow(x)):Seq[JsObject]
    jsData.foldLeft(JsArray())((acc, x) => acc ++ Json.arr(x))
  }
  
  def transformNotesToJsArray(notes: Seq[Notes]) : JsArray = {
    val jsData = notes.map( x => transformNoteToJsonRow(x)):Seq[JsObject]
    jsData.foldLeft(JsArray())((acc, x) => acc ++ Json.arr(x))
  }
  
  def transformParticipationsToJsArray(participations: Seq[Participation]) : JsArray = {
    val jsData = participations.map( x => transformParticipationToJsonRow(x)):Seq[JsObject]
    jsData.foldLeft(JsArray())((acc, x) => acc ++ Json.arr(x))
  }
  
  def transformUsersToJsArray(users: Seq[User]) : JsArray = {
    val jsData = users.map( x => transformUserToJsonRow(x)):Seq[JsObject]
    jsData.foldLeft(JsArray())((acc, x) => acc ++ Json.arr(x))
  }
  
  def transformUsersDTOToJsArray(users: Seq[UserDTO]) : JsArray = {
    val jsData = users.map( x => transformUserDTOToJsonRow(x)):Seq[JsObject]
    jsData.foldLeft(JsArray())((acc, x) => acc ++ Json.arr(x))
  }
  
  def transformUsersDTOAndPenaltiesToJsObject(users: Seq[UserDTO], penalties: Seq[Notes]) : JsObject = {    
    Json.obj(JSON_KEY_USERS->transformUsersDTOToJsArray(users), JSON_KEY_PENALTIES-> transformNotesToJsArray(penalties))
  }
  
  def transformAccountsToJsonArray(accounts: Seq[Account]): JsArray = {    
    val jsData = accounts.map( x => transformAccountToJsonRow(x)):Seq[JsObject]
    jsData.foldLeft(JsArray())((acc, x) => acc ++ Json.arr(x))
  }
  
  def transformWarToJsObject(optionWar: Option[War]) : JsObject = {
    optionWar match {
      case Some(war) => Json.obj(JSON_KEY_ID -> war.id.toString, JSON_KEY_Name -> war.name, JSON_KEY_NOTE -> war.note, JSON_KEY_STATUS -> war.status, 
      JSON_KEY_SCORE -> war.score, JSON_KEY_START_DATE -> war.startdate);
      case None => null
    }    
  }

}
