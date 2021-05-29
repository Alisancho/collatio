package ru.finance.analyst.entity.yahoo

import ru.finance.analyst.entity.elastic.MonitoringTask
import ru.finance.analyst.entity.yahoo.statistic.{
  DefaultKeyStatistics,
  QuoteType,
  SharesOutstanding,
  SummaryDetail,
  YahooStatisticsResponse
}
import ru.finance.analyst.entity.yahoo.summary.{
  AverageDailyVolume3Month,
  PriceSummary,
  RegularMarketOpen,
  YahooSummaryResponse,
  YahooSummaryResponseQuoteType
}

trait JsonSupportYahoo {

  import spray.json._
  import DefaultJsonProtocol._

  implicit lazy val sharesOutstandingJ: RootJsonFormat[SharesOutstanding]             = jsonFormat2(SharesOutstanding)
  implicit lazy val summaryDetailJ: RootJsonFormat[SummaryDetail]                     = jsonFormat1(SummaryDetail)
  implicit lazy val defaultKeyStatisticsJ: RootJsonFormat[DefaultKeyStatistics]       = jsonFormat5(DefaultKeyStatistics)
  implicit lazy val yahooStatisticsResponseJ: RootJsonFormat[YahooStatisticsResponse] = jsonFormat3(YahooStatisticsResponse)

  implicit lazy val quoteTypeJ: RootJsonFormat[QuoteType]                                        = jsonFormat11(QuoteType)
  implicit lazy val yahooSummaryResponseJ: RootJsonFormat[YahooSummaryResponse]                  = jsonFormat3(YahooSummaryResponse)
  implicit lazy val monitoringTaskJ: JsonFormat[MonitoringTask]                                  = jsonFormat7(MonitoringTask)
  implicit lazy val priceJ: RootJsonFormat[PriceSummary]                                         = jsonFormat1(PriceSummary)
  implicit lazy val regularMarketOpenJ: JsonFormat[RegularMarketOpen]                            = jsonFormat2(RegularMarketOpen)
  implicit lazy val averageDailyVolume3MonthJ: JsonFormat[AverageDailyVolume3Month]              = jsonFormat3(AverageDailyVolume3Month)
  implicit lazy val yahooSummaryResponseQuoteType: RootJsonFormat[YahooSummaryResponseQuoteType] = jsonFormat9(
    YahooSummaryResponseQuoteType
  )

}
