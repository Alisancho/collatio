package ru.finance.analyst.entity.yahoo

import ru.finance.analyst.entity.elastic.MonitoringTask

trait JsonSupportYahoo {

  import spray.json._
  import DefaultJsonProtocol._

  implicit val sharesOutstandingJ: RootJsonFormat[SharesOutstanding]             = jsonFormat2(SharesOutstanding)
  implicit val summaryDetailJ: RootJsonFormat[SummaryDetail]                     = jsonFormat2(SummaryDetail)
  implicit val defaultKeyStatisticsJ: RootJsonFormat[DefaultKeyStatistics]       = jsonFormat5(DefaultKeyStatistics)
  implicit val yahooStatisticsResponseJ: RootJsonFormat[YahooStatisticsResponse] = jsonFormat3(YahooStatisticsResponse)
  implicit val quoteTypeJ: RootJsonFormat[QuoteType]                             = jsonFormat11(QuoteType)
  implicit val yahooSummaryResponseJ: RootJsonFormat[YahooSummaryResponse]       = jsonFormat1(YahooSummaryResponse)
  implicit val priceJ: RootJsonFormat[Price]                                     = jsonFormat1(Price)
  implicit val monitoringTaskJ: JsonFormat[MonitoringTask]                       = jsonFormat7(MonitoringTask)
}
