package controllers

import models.Notes
import models.Role.Administrator
import models.User
import play.api.libs.json.{ JsArray, Json }
import jp.t2v.lab.play2.auth.AuthConfig
import jp.t2v.lab.play2.auth.AuthElement
import jp.t2v.lab.play2.stackc.StackableController
import play.api.mvc._
import services.JsonService
import services.Pjax
import services.UserDTO
import views.html

class NoteController extends Controller with Pjax with AuthElement with AuthConfigImpl {

  val jsonService = new JsonService

  def initFile = Action {
    Ok(jsonService.transformToReservationRow("SampleName", "777", "approvedBy"))
  }

  def loadNotes = Action {
    Ok(jsonService.transformNotesToJsArray(Notes.findAll))
  }

  def addNote(name: String, descrption: String, addedBy: String) = StackAction(AuthorityKey -> Administrator) { implicit request =>
    Notes.create(name, descrption, "status", addedBy)
    Ok(jsonService.transformUsersDTOAndPenaltiesToJsObject(UserDTO.getAllUsersDTO(Notes.findAll, User.findAll), Notes.findAllForUser(name)))
  }

  protected val fullTemplate: User => Template = html.fullTemplate.apply

}
