package me.jerrypeng.urlshorten

import akka.actor.Actor
import com.redis.RedisClient
import spray.http.MediaTypes._
import spray.http.StatusCodes
import spray.routing._

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class URLShortenServiceActor extends Actor with URLShortenService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(routes)
}


// this trait defines our service behavior independently from the service actor
trait URLShortenService extends HttpService {

  val LastIdKey = "urlshorten.lastid"
  val redisClient = new RedisClient("localhost", 6379)

  val routes =
    path("") {
      get {
        index
      } ~
      post {
        shortenURL
      }
    } ~
    path(Segment) { id =>
      get {
        redirectToOrigin(id)
      }
    }


  def index = {
    respondWithMediaType(`text/html`) {
      complete {
        <html>
          <body><title>Spray URL Shortener</title></body>
          <body>
            <h1>Welcome to Spray URL Shortener</h1>
            <form action="/" method="POST" enctype="application/x-www-form-urlencoded">
              <p>Input URL: <input width="200" name="url" type="text"></input>
                <input type="submit"/>
              </p>
            </form>
          </body>
        </html>
      }
    }
  }

  val Alphabetics = "ab0cd1AB2CD3ef4gh5EF6GH7ij8kl9IJ0KLmnopMNOPqrstQRSTuvwxUVWXyzYZ"
  val AlphaLen = Alphabetics.length

  def encodeId(id: Long): String = {
    val code = new StringBuilder
    var n = id
    while (n != 0) {
      code.append(Alphabetics.charAt((n % AlphaLen).toInt))
      n = n / AlphaLen
    }
    code.toString()
  }

  def allocateId(url: String): Option[String] = {
    redisClient.get[String](url) orElse {
      for {
        id <- redisClient.incr(LastIdKey)
        encodedId <- {
          val encodedId = encodeId(id)
          if (redisClient.setnx(url, encodedId)) {
            redisClient.set(s"id:$encodedId", url)
            Some(encodedId)
          } else {
            redisClient.get(url)
          }
        }
      } yield encodedId
    }
  }

  def getUrl(id: String): Option[String] = {
    redisClient.get(s"id:$id")
  }

  def shortenURL = {
    formField('url) { url =>
      respondWithMediaType(`text/html`) {
        complete {
          <html>
            <body><title>Spray URL Shortener</title></body>
            <body>{ allocateId(url) match {
              case Some(id) =>
                <h1>Welcome to Spray URL Shortener</h1>
                  <p>Shortened URL: <a href="http://localhost:8080/{id}">http://localhost:8080/{id}</a></p>
              case None =>
                <h1>Failed to shorten URL</h1>
            }}
            </body>
          </html>
        }
      }
    }
  }

  def redirectToOrigin(id: String) = {
    getUrl(id) match {
      case Some(url) =>
        redirect(url, StatusCodes.TemporaryRedirect)
      case None =>
        respondWithMediaType(`text/html`) {
          complete {
            <html>
              <body>
                <h1>URL not found: {id}</h1>
              </body>
            </html>
          }
        }
    }
  }

}