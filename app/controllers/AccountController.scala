package controllers

import jp.t2v.lab.play2.auth.AuthElement
import models.Account
import models.Role._
import play.api.mvc._
import services.{JsonService, Pjax}
import views.html

class AccountController extends Controller with Pjax with AuthElement with AuthConfigImpl {

  val jsonService = new JsonService

  def createAccount(name: String, email: String, password: String) = Action { implicit request =>
    Account.create(name, email, password, NormalUser.toString)
    Ok(jsonService.transformAccountsToJsonArray(Account.findAllDB))
  }

  def updateAccount(email: String) = StackAction(AuthorityKey -> Administrator) { implicit request =>
    Account.updateRoleAccount(email, changeAccountRole(email))
    Ok(jsonService.transformAccountsToJsonArray(Account.findAllDB))
  }

  def changeAccountRole(email: String) = {
    if (Account.findByEmail(email).get.role.toString.equals("NormalUser")) "KVHost" else "NormalUser"
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
