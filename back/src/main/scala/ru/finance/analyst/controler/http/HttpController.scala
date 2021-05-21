package ru.finance.analyst.controler.http

import akka.actor.ActorSystem
import akka.stream.scaladsl._
import akka.util.ByteString
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpRequest}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import monix.execution.schedulers.SchedulerService
import ru.finance.analyst.service.YahooFinanceServiceImpl
import ru.finance.analyst.entity.{JsonSupportYahoo, YahooStatisticsResponse, YahooSummaryResponse}
import spray.json.JsonParser
import spray.json._
import scala.concurrent.duration._
import scala.concurrent.Future
import scala.util.Random
import scala.io.StdIn
import ru.finance.analyst.service.YahooFinanceService._


class HttpController(yahooFinanceServiceImpl: YahooFinanceServiceImpl)
                    (using ex:SchedulerService, materializer: Materializer) extends JsonSupportYahoo {
  def getRout: Route = pathPrefix("api" / "v1") {
    path("statistics") {
      get {
        entity(as[HttpRequest]) { httpRequest =>
          complete(
            yahooFinanceServiceImpl
              .getInfo("AAPL", "US", STATISTIC)
              .flatMap(_.entity.toStrict(3.second))
              .map(_.data.utf8String)
              .map(JsonParser(_).convertTo[YahooStatisticsResponse])
              .map(_.toJson.toString)
          )
        }
      }
    } ~
      path("summary") {
        get {
          entity(as[HttpRequest]) { httpRequest =>
            complete(
              yahooFinanceServiceImpl
                .getInfo("^GSPC", "US", SUMMARY)
                .flatMap(_.entity.toStrict(3.second))
                .map(_.data.utf8String)
                .map(JsonParser(_).convertTo[YahooSummaryResponse])
                .map(_.toJson.toString)
            )
          }
        }
      }
    }
}
