package ru.finance.analyst.service

import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{HttpHeader, HttpMethod, HttpRequest, HttpResponse, Uri}
import akka.http.scaladsl.model.Uri.{Path, Query}
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Future

case class YahooFinanceConfig(
    uriHost: Uri,
    xRapidapiHost: String,
    token: String,
    f: HttpRequest => Future[HttpResponse]
)

object YahooFinanceService {
  val STATISTIC: Path = Path("/stock/v2/get-statistics")
  val SUMMARY: Path   = Path("/stock/v2/get-summary")
}

class YahooFinanceServiceImpl(config: YahooFinanceConfig) extends  LazyLogging{
  logger.info("START_YahooFinanceServiceImpl")

  def getInfo(symbol: String, region: String = "US", path: Path): Future[HttpResponse] = {
    val header1 = RawHeader("x-rapidapi-key", config.token)
    val header2 = RawHeader("x-rapidapi-host", config.xRapidapiHost)
    val headers = header1 :: header2 :: Nil

    val query  = Query(Map("symbol" -> symbol, "region" -> region))
    val newUri = config.uriHost.withPath(path).withQuery(query)

    config.f(HttpRequest(uri = newUri, headers = headers))
  }
}
