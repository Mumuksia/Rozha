package controllers

import java.util.Calendar
import models.Notes
import models.Participation
import play.api.libs.json.{ JsArray, Json }
import play.api.mvc._
import services.JsonService

class ParticipationController extends Controller {

  val jsonService = new JsonService

  def loadParticipants = Action {
    Ok(jsonService.transformParticipationsToJsArray(Participation.findAllByStatusAndWeekAndDay("new", Calendar.getInstance.getWeekYear.toString, "Tuesday")))
  }

  def loadParticipantsSecond = Action {
    Ok(jsonService.transformParticipationsToJsArray(Participation.findAllByStatusAndWeekAndDay("new", Calendar.getInstance.getWeekYear.toString, "Saturday")))
  }

  def addParticipant(name: String) = Action {
    Participation.create(name, "new", Calendar.getInstance.getWeekYear.toString, "Tuesday")
    Ok(jsonService.transformParticipationsToJsArray(Participation.findAllByStatusAndWeekAndDay("new", Calendar.getInstance.getWeekYear.toString, "Tuesday")))
  }

  def addParticipantSecond(name: String) = Action {
    Participation.create(name, "new", Calendar.getInstance.getWeekYear.toString, "Saturday")
    Ok(jsonService.transformParticipationsToJsArray(Participation.findAllByStatusAndWeekAndDay("new", Calendar.getInstance.getWeekYear.toString, "Saturday")))
  }
}

