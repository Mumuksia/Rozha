package controllers

import play.api.mvc.RequestHeader
import play.api.mvc.Results._

import scala.concurrent.{Future, ExecutionContext}

trait AuthConfigImpl extends BaseAuthConfig {

  def loginSucceeded(request: RequestHeader)(implicit ctx: ExecutionContext) = Future.successful(Redirect(routes.Messages.main))

  def logoutSucceeded(request: RequestHeader)(implicit ctx: ExecutionContext) = Future.successful(Redirect(routes.Application.index))

  def authenticationFailed(request: RequestHeader)(implicit ctx: ExecutionContext) = {
    println("AUTH FAILED ************")
    Future.successful(Redirect(routes.Sessions.login))
  }


}
