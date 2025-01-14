package views

import org.goingok.server.data.UiMessage
import org.goingok.server.data.models.User
import play.twirl.api.Html
import scalatags.Text
import scalatags.Text.all._
import scalatags.Text.{TypedTag, tags, tags2}

trait GenericPage {

  def getHtml(page:Text.TypedTag[String]):Html = Html("""<!DOCTYPE html>""" +page.render)

  def render(title:String,user:Option[User]=None,message:String="") :Html = Html("""<!DOCTYPE html>""" +page(title,user,message).render)

  def page(titleStr:String,user:Option[User],message:String) :Text.TypedTag[String] = tags.html(head(tags2.title(titleStr)))

  def card(heading:String,content:TypedTag[String]) = div(`class`:="card",
    h5(`class`:="card-header",heading),
    div(`class`:="card-body",content)
  )

  def bundleUrl: String = Seq("client-opt-bundle.js", "client-fastopt-bundle.js")
    .find(name => getClass.getResource(s"/public/$name") != null)
    .map(name => controllers.routes.Assets.versioned(s"$name").url).getOrElse("BUNDLE_NOT_FOUND")

  def showMessage(message:Option[UiMessage]): TypedTag[String] = message match {
    case Some(msg) => div(id:="message",`class`:=s"alert alert-${msg.style}",attr("role"):="alert",msg.text)
    case None => div()
  }

}