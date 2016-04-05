package controllers

import jp.t2v.lab.play2.auth.AuthElement
import models.{Participation, Reservations}
import models.Role._
import play.api.mvc._
import services.{JsonService, Pjax}
import views.html

class ReservationController extends Controller with Pjax with AuthElement with AuthConfigImpl {

  val jsonService = new JsonService

  def main = StackAction(AuthorityKey -> KVHost) { implicit request =>
    Ok(html.rozha())
  }

  def createReservations = StackAction(AuthorityKey -> KVHost) { implicit request =>
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

