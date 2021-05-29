package ru.finance.analyst.entity.yahoo

import ru.finance.analyst.entity.elastic.MonitoringTask
import ru.finance.analyst.entity.yahoo.statistic.{DefaultKeyStatistics, QuoteType, SharesOutstanding, SummaryDetail, YahooStatisticsResponse}
import ru.finance.analyst.entity.yahoo.summary.{AverageDailyVolume3Month, PriceSummary, RegularMarketOpen, YahooSummaryResponse, YahooSummaryResponseQuoteType}

trait JsonSupportYahoo {

  import spray.json._
  import DefaultJsonProtocol._

  implicit lazy val sharesOutstandingJ: RootJsonFormat[SharesOutstanding]             = jsonFormat2(SharesOutstanding.apply)
  implicit lazy val summaryDetailJ: RootJsonFormat[SummaryDetail]                     = jsonFormat1(SummaryDetail.apply)
  implicit lazy val defaultKeyStatisticsJ: RootJsonFormat[DefaultKeyStatistics]       = jsonFormat5(DefaultKeyStatistics.apply)
  implicit lazy val yahooStatisticsResponseJ: RootJsonFormat[YahooStatisticsResponse] = jsonFormat3(
    YahooStatisticsResponse.apply
  )
  implicit lazy val quoteTypeJ: RootJsonFormat[QuoteType]                             = jsonFormat11(QuoteType.apply)
  implicit lazy val yahooSummaryResponseJ: RootJsonFormat[YahooSummaryResponse]       = jsonFormat3(YahooSummaryResponse.apply)
  implicit lazy val monitoringTaskJ: JsonFormat[MonitoringTask]                       = jsonFormat7(MonitoringTask.apply)
  implicit lazy val priceJ: RootJsonFormat[PriceSummary]                              = jsonFormat1(PriceSummary.apply)
  implicit lazy val regularMarketOpenJ: JsonFormat[RegularMarketOpen]                 = jsonFormat2(RegularMarketOpen.apply)
  implicit lazy val averageDailyVolume3MonthJ: JsonFormat[AverageDailyVolume3Month]   = jsonFormat3(
    AverageDailyVolume3Month.apply
  )
  implicit lazy val yahooSummaryResponseQuoteType: RootJsonFormat[YahooSummaryResponseQuoteType]= jsonFormat9(YahooSummaryResponseQuoteType.apply)


}
