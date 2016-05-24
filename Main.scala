package pl.edu.osp

import akka.actor.{Props, ActorSystem}
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport._
import akka.http.scaladsl.model.Multipart
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.util.Timeout
import akka.pattern.ask
import scala.concurrent.duration._
import spray.json._
import akka.http.scaladsl.model.StatusCodes.MovedPermanently
import java.sql.DriverManager
import java.util.Date
import scala.concurrent.ExecutionContext.Implicits.global
import java.io.{FileOutputStream, ByteArrayOutputStream, File}
import akka.http.scaladsl.model.{StatusCodes, Multipart, StatusCode, HttpResponse}
import akka.util.ByteString

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
        val ask = baza.prepareStatement( """select * from MEMES1 where ID <= 5""")
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
              <hr color="black"/>
              <form action="szukaj" name="Szukaj" method="post">
                <input type="text" name="Szukaj" value="Szukaj"/>
              </form>
              <form action="kategoria" name="kategoria" method="post">
                <select name="kategoria">
                  <option value="Shopenhauer">Shopenhauer</option>
                  <option value="Medieval">Sredniowieczne</option>
                  <option value="Komunizm">Komunistyczne</option>
                </select>
                <input type="submit" value="OK"/>
              </form>
              <div align="right">
                <form action="/upload">
                  <input type="submit" value="Upload"/>
                </form>
              </div>
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
      }~
        path("strona" / IntNumber) { (numer) =>
          val ask = baza.prepareStatement( """select * from MEMES1 where ID <= ? and ID >= ?""")
          if (numer == 0) {
            ask.setInt(1, numer + 6)
            ask.setInt(2, numer + 1)
          } else {
            ask.setInt(1, numer + 11)
            ask.setInt(2, numer + 5)
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
                <hr color="black"/>
                <form action="/szukaj" name="Szukaj" method="post">
                  <input type="text" name="Szukaj" value="Szukaj"/>
                </form>
                <form action="/kategoria" name="kategoria" method="post">
                  <select name="kategoria">
                    <option value="Shopenhauer">Shopenhauer</option>
                    <option value="Medieval">Sredniowieczne</option>
                    <option value="Komunizm">Komunistyczne</option>
                  </select>
                  <input type="submit" value="OK"/>
                </form>
                <div align="right">
                  <form action="/upload">
                    <input type="submit" value="Upload"/>
                  </form>
                </div>{if (a.next() == true) {
                <center>
                  <p>
                    <font size="4">
                      {a.getString("TYTUL")}
                    </font>
                  </p>
                  <p>
                    <img src={"/img/" + a.getString("NAZWA_PLIKU")} style="width:350px;height:350px;"/>
                  </p>
                  <p>
                    <img src="/img/like.png" style="width:25px;height:25px"/>{a.getInt("KEBAB")}<img src="/img/dislike.jpg" style="width:25px;height:25px"/>{a.getInt("BRUKSELKA")}
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
                    <img src={"/img/" + a.getString("NAZWA_PLIKU")} style="width:350px;height:350px;"/>
                  </p>
                  <p>
                    <img src="/img/like.png" style="width:25px;height:25px"/>{a.getInt("KEBAB")}<img src="/img/dislike.jpg" style="width:25px;height:25px"/>{a.getInt("BRUKSELKA")}
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
                    <img src={"/img/" + a.getString("NAZWA_PLIKU")} style="width:350px;height:350px;"/>
                  </p>
                  <p>
                    <img src="/img/like.png" style="width:25px;height:25px"/>{a.getInt("KEBAB")}<img src="/img/dislike.jpg" style="width:25px;height:25px"/>{a.getInt("BRUKSELKA")}
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
                    <img src={"/img/" + a.getString("NAZWA_PLIKU")} style="width:350px;height:350px;"/>
                  </p>
                  <p>
                    <img src="/img/like.png" style="width:25px;height:25px"/>{a.getInt("KEBAB")}<img src="/img/dislike.jpg" style="width:25px;height:25px"/>{a.getInt("BRUKSELKA")}
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
                    <img src={"/img/" + a.getString("NAZWA_PLIKU")} style="width:350px;height:350px;"/>
                  </p>
                  <p>
                    <img src="/img/like.png" style="width:25px;height:25px"/>{a.getInt("KEBAB")}<img src="/img/dislike.jpg" style="width:25px;height:25px"/>{a.getInt("BRUKSELKA")}
                  </p>

                </center>
              }}<center>
                {if (numer != 0) {
                  <form action={"/strona/" + (numer - 1)}>
                    <input type="submit" value="Poprzednia strona"/>
                  </form>
                }}{if (a.next == true) {
                  <form action={"/strona/" + (numer + 1)}>
                    <input type="submit" value="Nastepna strona"/>
                  </form>
                }}
              </center>
              </body>
            </html>
          }
        } ~
        path("upload") {
          complete {
            <html>
              <head>
                <title>Memes4U</title>
              </head>
              <body>
                <form action="plik" enctype="multipart/form-data" method="post">
                  <p>Wpisz tytuł:<input type="text" name="Tytul"/>
                </p>
                  <p>Wybierz kategorię:
                    <select name="kategoria">
                      <option value="Shopenhauer">Shopenhauer</option>
                      <option value="Medieval">Sredniowieczne</option>
                      <option value="Komunizm">Komunistyczne</option>
                      <option value="Other">Inne</option>
                    </select>
                  </p>
                <p>
                  <input type="file" name="plik"/>
                </p>
                <p>
                  <input type="submit" value="Upload"/>
                </p>
              </form>
              </body>
            </html>
          }
        } ~
        pathPrefix("img") {
          getFromResourceDirectory("img")
        }
    } ~
      post {
        (path("szukaj") & formFields('Szukaj.as[String])) { (Szukaj) => {
          val wyszukaj = Szukaj
          var wyszukane = "img/"
          var kebab = 0
          var brukselka = 0
          val ask = baza.prepareStatement( """select NAZWA_PLIKU, KEBAB, BRUKSELKA from MEMES1 where TYTUL=?""")
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
                <hr color="black"/>
                <form action="szukaj" name="Szukaj" method="post">
                  <input type="text" name="Szukaj" value="Szukaj"/>
                </form>
                <form action="kategoria" name="kategoria" method="post">
                  <select name="kategoria">
                    <option value="Shopenhauer">Shopenhauer</option>
                    <option value="Medieval">Sredniowieczne</option>
                    <option value="Komunizm">Komunistyczne</option>
                  </select>
                  <input type="submit" value="OK"/>
                </form>
                <div align="right">
                  <form action="/upload">
                    <input type="submit" value="Upload"/>
                  </form>
                </div>
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
                  <form action="/">
                    <input type="submit" value="Powrot"/>
                  </form>
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
        } ~
          (path("kategoria") & formFields('kategoria.as[String])) { (kategoria) => {
            val ask = baza.prepareStatement( """select TYTUL, NAZWA_PLIKU, KEBAB, BRUKSELKA from MEMES1 where KATEGORIA=?""")
            ask.setString(1, kategoria)
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
                  <hr color="black"/>
                  <form action="/szukaj" name="Szukaj" method="post">
                    <input type="text" name="Szukaj" value="Szukaj"/>
                  </form>
                  <form action="kategoria" name="kategoria" method="post">
                    <select name="kategoria">
                      <option value="Shopenhauer">Shopenhauer</option>
                      <option value="Medieval">Sredniowieczne</option>
                      <option value="Komunizm">Komunistyczne</option>
                    </select>
                    <input type="submit" value="OK"/>
                  </form>
                  <div align="right">
                    <form action="/upload">
                      <input type="submit" value="Upload"/>
                    </form>
                  </div>{if (a.next() == true) {
                  <center>
                    <p>
                      <font size="4">
                        {a.getString("TYTUL")}
                      </font>
                    </p>
                    <p>
                      <img src={"/img/" + a.getString("NAZWA_PLIKU")} style="width:350px;height:350px;"/>
                    </p>
                    <p>
                      <img src="/img/like.png" style="width:25px;height:25px"/>{a.getInt("KEBAB")}<img src="/img/dislike.jpg" style="width:25px;height:25px"/>{a.getInt("BRUKSELKA")}
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
                      <img src={"/img/" + a.getString("NAZWA_PLIKU")} style="width:350px;height:350px;"/>
                    </p>
                    <p>
                      <img src="/img/like.png" style="width:25px;height:25px"/>{a.getInt("KEBAB")}<img src="/img/dislike.jpg" style="width:25px;height:25px"/>{a.getInt("BRUKSELKA")}
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
                      <img src={"/img/" + a.getString("NAZWA_PLIKU")} style="width:350px;height:350px;"/>
                    </p>
                    <p>
                      <img src="/img/like.png" style="width:25px;height:25px"/>{a.getInt("KEBAB")}<img src="/img/dislike.jpg" style="width:25px;height:25px"/>{a.getInt("BRUKSELKA")}
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
                      <img src={"/img/" + a.getString("NAZWA_PLIKU")} style="width:350px;height:350px;"/>
                    </p>
                    <p>
                      <img src="/img/like.png" style="width:25px;height:25px"/>{a.getInt("KEBAB")}<img src="/img/dislike.jpg" style="width:25px;height:25px"/>{a.getInt("BRUKSELKA")}
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
                      <img src={"/img/" + a.getString("NAZWA_PLIKU")} style="width:350px;height:350px;"/>
                    </p>
                    <p>
                      <img src="/img/like.png" style="width:25px;height:25px"/>{a.getInt("KEBAB")}<img src="/img/dislike.jpg" style="width:25px;height:25px"/>{a.getInt("BRUKSELKA")}
                    </p>
                  </center>
                }}<center>
                  {if (a.next() == true) {
                    <form action="/kategoria">
                      <input type="submit" value="Nastepna strona"/>
                    </form>
                  }}
                </center>
                </body>
              </html>
            }
          }
          } ~
          (path("plik") & entity(as[Multipart.FormData]) &
            formFields('Tytul.as[String], 'kategoria.as[String])) { (fileData, tytul, kategoria) => {
            complete {
              {
                val fileName = processFile(fileData)
               // println(fileName)
                //val askd = baza.prepareStatement(
                //  """insert into memes1(TYTUL,
                //  |KATEGORIA, KEBAB, BRUKSELKA, NAZWA_PLIKU) values(?,?,0,0,?)""".stripMargin)
               // askd.setString(1, tytul)
               // askd.setString(2, kategoria)
               // askd.setString(3, fileName)
               // askd.executeUpdate()
              }
              HttpResponse(StatusCodes.OK, entity = s"File  successfully uploaded.")
              //redirect("/", MovedPermanently)
            }
          }
          }
      }
  private def processFile(fileData: Multipart.FormData):String = {
    var fileUploadName="4"
    var optionFile:Option[FileOutputStream] = None
    val length = fileData.parts.mapAsync(1) {
      bodyPart =>
        fileUploadName = bodyPart.filename.getOrElse("noname.jpg")
        val filePath =  System.getProperty("user.home") + "/Pulpit/Scala/memes/src/main/resources/img/" + fileUploadName
        optionFile = Some( new FileOutputStream(filePath))
        def writeFileOnLocal(array: Array[Byte], byteString: ByteString): Array[Byte] = {
          val byteArray: Array[Byte] = byteString.toArray
          optionFile.foreach(_.write(byteArray))
          array ++ byteArray
        }
        bodyPart.entity.dataBytes.runFold(Array[Byte]())(writeFileOnLocal)
    }.runFold(0)(_ + _.length)
    optionFile.foreach(_.close)
    return fileUploadName
  }




  Http().bindAndHandle(route, "localhost", 9000)
}
