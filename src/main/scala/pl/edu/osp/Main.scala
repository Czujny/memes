package pl.edu.osp

import akka.actor.{Props, ActorSystem}
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.util.Timeout
import akka.pattern.ask
import scala.concurrent.duration._
import spray.json._
import akka.http.scaladsl.model.StatusCodes.MovedPermanently
import java.sql.DriverManager
import java.util.Date


object Main extends App {
  val serviceName = "Memes4U"

  protected def log: LoggingAdapter = Logging(system, serviceName)

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  Class.forName("org.h2.Driver")
  val baza = DriverManager.getConnection("jdbc:h2:./memes", "sa", "")

  val route =
    get {
      pathSingleSlash {
        val ask = baza.prepareStatement( """select * from MEMES where ID <= 5""")
        val a = ask.executeQuery()
        complete {
          <html>
            <head>
              <title>Memes4U</title>
            </head>
            <body>
              <center>
                <font size="7">Memes</font>
              </center>
              <hr color="red"/>
              <form action="Szukaj" method="post">
                <input type="text" name="Szukaj" value="Szukaj"/>
              </form>
              <label for="kategoria">Wybierz kategorie</label>
              <select id="kategoria">
                <option value="Shopenhauer">Shopenhauer</option>
                <option value="Medieval">Sredniowieczne</option>
                <option value="Komunizm">Komunistyczne</option>
            </select>
              {if (a.next() == true) {
              <center>
                <p>
                  <font size="4">
                    {a.getString("TYTUL")}
                  </font>
                </p>
                <p>
                  <img src={"img/" + a.getString("NAZWA_PLIKU")} style="width:350px;height:350px;"/>
                </p>
                <p>
                  <img src="img/like.png" style="width:25px;height:25px"/>{a.getInt("KEBAB")}<img src="img/dislike.jpg" style="width:25px;height:25px"/>{a.getInt("BRUKSELKA")}
                </p>
              </center>
            }}{if (a.next() == true) {
              <center>
                <p>
                  <font size="4">
                    {a.getString("TYTUL")}
                  </font>
                </p>
                <p>
                  <img src={"img/" + a.getString("NAZWA_PLIKU")} style="width:350px;height:350px;"/>
                </p>
                <p>
                  <img src="img/like.png" style="width:25px;height:25px"/>{a.getInt("KEBAB")}<img src="img/dislike.jpg" style="width:25px;height:25px"/>{a.getInt("BRUKSELKA")}
                </p>
              </center>
            }}{if (a.next() == true) {
              <center>
                <p>
                  <font size="4">
                    {a.getString("TYTUL")}
                  </font>
                </p>
                <p>
                  <img src={"img/" + a.getString("NAZWA_PLIKU")} style="width:350px;height:350px;"/>
                </p>
                <p>
                  <img src="img/like.png" style="width:25px;height:25px"/>{a.getInt("KEBAB")}<img src="img/dislike.jpg" style="width:25px;height:25px"/>{a.getInt("BRUKSELKA")}
                </p>
              </center>
            }}{if (a.next() == true) {
              <center>
                <p>
                  <font size="4">
                    {a.getString("TYTUL")}
                  </font>
                </p>
                <p>
                  <img src={"img/" + a.getString("NAZWA_PLIKU")} style="width:350px;height:350px;"/>
                </p>
                <p>
                  <img src="img/like.png" style="width:25px;height:25px"/>{a.getInt("KEBAB")}<img src="img/dislike.jpg" style="width:25px;height:25px"/>{a.getInt("BRUKSELKA")}
                </p>
              </center>
            }}{if (a.next() == true) {
              <center>
                <p>
                  <font size="4">
                    {a.getString("TYTUL")}
                  </font>
                </p>
                <p>
                  <img src={"img/" + a.getString("NAZWA_PLIKU")} style="width:350px;height:350px;"/>
                </p>
                <p>
                  <img src="img/like.png" style="width:25px;height:25px"/>{a.getInt("KEBAB")}<img src="img/dislike.jpg" style="width:25px;height:25px"/>{a.getInt("BRUKSELKA")}
                </p>

              </center>
            }}<center>
              <form action={"/strona/" + 1}>
                <input type="submit" value="Nastepna strona"/>
              </form>
            </center>
            </body>
          </html>
        }
      } ~
      path("strona" / IntNumber){ (numer) =>
        val ask = baza.prepareStatement( """select * from MEMES where ID <= ? and ID >= ?""")
        if(numer==0){
          ask.setInt(1, numer+6)
          ask.setInt(2, numer+1)
        }else{
          ask.setInt(1, numer+11)
          ask.setInt(2, numer+5)
        }
        val a = ask.executeQuery()
        complete {
          <html>
            <head>
              <title>Memes4U</title>
            </head>
            <body>
              <center>
                <font size="7">Memes</font>
              </center>
              <hr color="red"/>
              <form action="Szukaj" method="post">
                <input type="text" name="Szukaj" value="Szukaj"/>
              </form>
              <label for="kategoria">Wybierz kategorie</label>
              <select id="kategoria">
                <option value="Shopenhauer">Shopenhauer</option>
                <option value="Medieval">Sredniowieczne</option>
                <option value="Komunizm">Komunistyczne</option>
                </select>
                {if (a.next() == true){
              <center>
                <p>
                  <font size="4">
                    {a.getString("TYTUL")}
                  </font>
                </p>
                <p>
                  <img src={"img/" + a.getString("NAZWA_PLIKU")} style="width:350px;height:350px;"/>
                </p>
                <p>
                  <img src="img/like.png" style="width:25px;height:25px"/>{a.getInt("KEBAB")}<img src="img/dislike.jpg" style="width:25px;height:25px"/>{a.getInt("BRUKSELKA")}
                </p>
              </center>
            }}{if (a.next() == true) {
              <center>
                <p>
                  <font size="4">
                    {a.getString("TYTUL")}
                  </font>
                </p>
                <p>
                  <img src={"img/" + a.getString("NAZWA_PLIKU")} style="width:350px;height:350px;"/>
                </p>
                <p>
                  <img src="img/like.png" style="width:25px;height:25px"/>{a.getInt("KEBAB")}<img src="img/dislike.jpg" style="width:25px;height:25px"/>{a.getInt("BRUKSELKA")}
                </p>
              </center>
            }}{if (a.next() == true) {
              <center>
                <p>
                  <font size="4">
                    {a.getString("TYTUL")}
                  </font>
                </p>
                <p>
                  <img src={"img/" + a.getString("NAZWA_PLIKU")} style="width:350px;height:350px;"/>
                </p>
                <p>
                  <img src="img/like.png" style="width:25px;height:25px"/>{a.getInt("KEBAB")}<img src="img/dislike.jpg" style="width:25px;height:25px"/>{a.getInt("BRUKSELKA")}
                </p>
              </center>
            }}{if (a.next() == true) {
              <center>
                <p>
                  <font size="4">
                    {a.getString("TYTUL")}
                  </font>
                </p>
                <p>
                  <img src={"img/" + a.getString("NAZWA_PLIKU")} style="width:350px;height:350px;"/>
                </p>
                <p>
                  <img src="img/like.png" style="width:25px;height:25px"/>{a.getInt("KEBAB")}<img src="img/dislike.jpg" style="width:25px;height:25px"/>{a.getInt("BRUKSELKA")}
                </p>
              </center>
            }}{if (a.next() == true) {
              <center>
                <p>
                  <font size="4">
                    {a.getString("TYTUL")}
                  </font>
                </p>
                <p>
                  <img src={"img/" + a.getString("NAZWA_PLIKU")} style="width:350px;height:350px;"/>
                </p>
                <p>
                  <img src="img/like.png" style="width:25px;height:25px"/>{a.getInt("KEBAB")}<img src="img/dislike.jpg" style="width:25px;height:25px"/>{a.getInt("BRUKSELKA")}
                </p>

              </center>
            }}<center>
              {if(numer!=0) {
                <form action={"/strona/" + (numer - 1)}>
                  <input type="submit" value="Poprzednia strona"/>
                </form>
              }}
              {if(a.next==true) {
                <form action={"/strona/" + (numer + 1)}>
                  <input type="submit" value="Nastepna strona"/>
                </form>
              }}
            </center>
            </body>
          </html>
        }
      }~
        pathPrefix("img") {
          getFromResourceDirectory("img")
        }
    }~
      post {
        (path("Szukaj") & formFields('Szukaj.as[String])) { (Szukaj) => {
          val wyszukaj = Szukaj
          var wyszukane = "img/"
          var kebab = 0
          var brukselka = 0
          val ask = baza.prepareStatement( """select NAZWA_PLIKU, KEBAB, BRUKSELKA from MEMES where TYTUL=?""")
          ask.setString(1, wyszukaj)
          val a = ask.executeQuery()
          while (a.next()) {
            wyszukane += a.getString("NAZWA_PLIKU")
            kebab = a.getInt("KEBAB")
            brukselka = a.getInt("BRUKSELKA")
            println("WYSZUKAL")
          }
          if (wyszukane != "img/") complete {
            <html>
              <head>
                <title>Memes4U</title>
              </head>
              <body>
                <center>
                  <font size="7">Memes</font>
                </center>
                <hr color="red"/>
                <form action="Szukaj" method="post">
                  <input type="text" name="Szukaj" value="Szukaj"/>
                </form>
                <center>
                  <p>
                    <font size="4">
                      {Szukaj}
                    </font>
                  </p>
                  <p>
                    <img src={wyszukane} style="width:350px;height:350px;"/>
                  </p>
                  <p>
                    <button type="submit" method="post" name="Like" value={wyszukane}>
                      <img src="img/like.png" style="width:25px;height:25px"/>{kebab}
                    </button> <button type="submit" method="post" name="Dislike" value={wyszukane}>
                    <img src="img/dislike.jpg" style="width:25px;height:25px"/>{brukselka}
                  </button>
                  </p>
                </center>
              </body>
            </html>
          }
          else complete {
            <html>
              <head>
                <title>Memes4U</title>
              </head>
              <body>
                <center>
                  <font size="7">
                    <p>ERROR 404</p> <p>MEME NOT FOUND</p>
                  </font>
                  <form action="/">
                    <input type="submit" value="Powrot"/>
                  </form>
                </center>
              </body>
            </html>
          }
        }
        }
      }

  Http().bindAndHandle(route, "localhost", 9000)
}
