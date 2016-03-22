package controllers

import play.api.mvc.RequestHeader
import play.api.mvc.Results._
import jp.t2v.lab.play2.auth.{ CookieTokenAccessor, TokenAccessor }

import scala.concurrent.{ Future, ExecutionContext }

trait AuthConfigImpl extends BaseAuthConfig {

  def loginSucceeded(request: RequestHeader)(implicit ctx: ExecutionContext) = Future.successful(Redirect(routes.ReservationController.main))

  def logoutSucceeded(request: RequestHeader)(implicit ctx: ExecutionContext) = Future.successful(Redirect(routes.Application.index))

  def authenticationFailed(request: RequestHeader)(implicit ctx: ExecutionContext) = {
    Future.successful(Redirect(routes.Sessions.login))
  }

  override lazy val tokenAccessor: TokenAccessor = new CookieTokenAccessor(
    cookieName = "PLAY2AUTH_SESS_ID",
    cookieSecureOption = false,
    cookieHttpOnlyOption = true,
    cookieDomainOption = None,
    cookiePathOption = "/",
    cookieMaxAge = None
  )

}
