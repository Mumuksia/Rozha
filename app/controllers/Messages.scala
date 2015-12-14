package controllers

import models.War
import play.api.mvc._
import java.util.Calendar
import jp.t2v.lab.play2.auth.AuthConfig
import jp.t2v.lab.play2.auth.AuthElement
import jp.t2v.lab.play2.stackc.StackableController
import models.Participation
import models.Reservations
import models.Role._
import services.JsonService
import services.Pjax
import views.html

class Messages extends Controller with Pjax with AuthElement with AuthConfigImpl {

  val jsonService = new JsonService

  def createReservations = StackAction(AuthorityKey -> NormalUser) { implicit request =>
    Reservations.createSample()
    Ok(html.rozha())
  }

  def cleanReservations = StackAction(AuthorityKey -> Administrator) { implicit request =>
    Reservations.clearAll("accepted")
    Ok(html.rozha())
  }
  
  def cleanReservationsTable = StackAction(AuthorityKey -> Administrator) { implicit request =>
    Reservations.clearAllTable()
    Ok(html.rozha())
  }
  
  def cleanParticipantsForDay(day: String) = StackAction(AuthorityKey -> Administrator) { implicit request =>
    Participation.clearForDay(day)
    Ok(html.rozha())            
  }
  
  protected val fullTemplate: User => Template = html.fullTemplate.apply

}

