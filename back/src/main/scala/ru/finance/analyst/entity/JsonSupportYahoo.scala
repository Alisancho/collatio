package ru.finance.analyst.entity

import spray.json.DefaultJsonProtocol
import akka.http.scaladsl.server.Directives

trait JsonSupportYahoo:
  import spray.json._
  import DefaultJsonProtocol._

  given RootJsonFormat[SharesOutstanding] = jsonFormat2(SharesOutstanding.apply)
  given RootJsonFormat[SummaryDetail] = jsonFormat2(SummaryDetail.apply)
  given RootJsonFormat[DefaultKeyStatistics] = jsonFormat5(DefaultKeyStatistics.apply)
  given RootJsonFormat[YahooStatisticsResponse] = jsonFormat3(YahooStatisticsResponse.apply)
  given RootJsonFormat[QuoteType] = jsonFormat11(QuoteType.apply)
  given RootJsonFormat[YahooSummaryResponse] = jsonFormat1(YahooSummaryResponse.apply)
  given RootJsonFormat[Price] = jsonFormat1(Price.apply)
