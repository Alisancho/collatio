package ru.finance.analyst.entity.yahoo

import ru.finance.analyst.entity.elastic.MonitoringTask
import ru.finance.analyst.entity.yahoo.statistic.{
  DefaultKeyStatistics,
  QuoteType,
  SharesOutstanding,
  SummaryDetail,
  YahooStatisticsResponse
}
import ru.finance.analyst.entity.yahoo.summary.{FinancialsTemplateSummary, PriceSummary, YahooSummaryResponse}

trait JsonSupportYahoo {

  import spray.json._
  import DefaultJsonProtocol._

  implicit val sharesOutstandingJ: RootJsonFormat[SharesOutstanding]             = jsonFormat2(SharesOutstanding)
  implicit val summaryDetailJ: RootJsonFormat[SummaryDetail]                     = jsonFormat1(SummaryDetail)
  implicit val defaultKeyStatisticsJ: RootJsonFormat[DefaultKeyStatistics]       = jsonFormat5(DefaultKeyStatistics)
  implicit val yahooStatisticsResponseJ: RootJsonFormat[YahooStatisticsResponse] = jsonFormat3(YahooStatisticsResponse)
  implicit val quoteTypeJ: RootJsonFormat[QuoteType]                             = jsonFormat11(QuoteType)
  implicit val yahooSummaryResponseJ: RootJsonFormat[YahooSummaryResponse]       = jsonFormat2(YahooSummaryResponse)
  implicit val monitoringTaskJ: JsonFormat[MonitoringTask]                       = jsonFormat7(MonitoringTask)
  implicit val financialsTemplateSummaryJ: JsonFormat[FinancialsTemplateSummary] = jsonFormat2(FinancialsTemplateSummary)
  implicit val priceJ: RootJsonFormat[PriceSummary]                              = jsonFormat1(PriceSummary)
}
