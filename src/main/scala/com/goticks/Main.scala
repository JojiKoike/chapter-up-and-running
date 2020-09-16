package com.goticks

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.util.Timeout
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

object Main extends App with RequestTimeout {

  val config = ConfigFactory.load()
  val host   = config.getString("http.host")
  val port   = config.getInt("http.port")

  implicit val system: ActorSystem          = ActorSystem()
  implicit val ec: ExecutionContextExecutor = system.dispatcher

  val api = new RestApi(system, requestTimeout(config)).routes

  val bindingFuture: Future[ServerBinding] =
    Http().newServerAt("localhost", port).bind(api) // Start HTTP Server

  val log = Logging(system.eventStream, "go-ticks")
  bindingFuture
    .map { serverBinding =>
      log.info(s"RestApi bound to ${serverBinding.localAddress}")
    }
    .onComplete {
      case Success(_) =>
        log.info("Success to bind to {}:{}", host, port)
      case Failure(exception) =>
        log.error(exception, "Failed to bind to {}:{}!", host, port)
        system.terminate()
    }
}

trait RequestTimeout {
  import scala.concurrent.duration._
  def requestTimeout(config: Config): Timeout = {
    val t = config.getString("akka.http.server.request-timeout")
    val d = Duration(t)
    FiniteDuration(d.length, d.unit)
  }
}
