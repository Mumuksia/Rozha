package controllers

import models.War
import play.api.mvc._
import java.util.Calendar
import jp.t2v.lab.play2.auth.AuthConfig
import jp.t2v.lab.play2.auth.AuthElement
import jp.t2v.lab.play2.stackc.StackableController
import models.Account
import models.Participation
import models.Reservations
import models.Role._
import services.JsonService
import services.Pjax
import views.html

class AccountController extends Controller with Pjax with AuthElement with AuthConfigImpl {

  val jsonService = new JsonService

  def createAccount(name: String, email: String, password: String) = Action { implicit request =>
    Account.create(name, email, password, NormalUser.toString)
    Ok(jsonService.transformAccountsToJsonArray(Account.findAllDB))
  }

  def updateAccount(email: String) = StackAction(AuthorityKey -> Administrator) { implicit request =>
    Account.updateRoleAccount(email, if (Account.findByEmail(email).get.role.toString.equals("NormalUser")) "KVHost" else "NormalUser")
    Ok(jsonService.transformAccountsToJsonArray(Account.findAllDB))
  }

  def deleteAccount(email: String) = StackAction(AuthorityKey -> Administrator) { implicit request =>
    Account.deleteAccountById(email)
    Ok(jsonService.transformAccountsToJsonArray(Account.findAllDB))
  }

  def loadAccounts = Action { implicit request =>
    Ok(jsonService.transformAccountsToJsonArray(Account.findAllDB))
  }
    
  protected val fullTemplate: User => Template = html.fullTemplate.apply

}
