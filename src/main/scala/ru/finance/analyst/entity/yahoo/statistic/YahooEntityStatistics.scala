package ru.finance.analyst.entity.yahoo.statistic

case class YahooStatisticsResponse(
    defaultKeyStatistics: DefaultKeyStatistics,
    summaryDetail: SummaryDetail,
    symbol: String
)

case class DefaultKeyStatistics(
    profitMargins: SharesOutstanding,
    enterpriseToEbitda: SharesOutstanding,
    forwardEps: SharesOutstanding,
    trailingEps: SharesOutstanding,
    beta: SharesOutstanding
)

case class PageViews(
    shortTermTrend: String,
    midTermTrend: String,
    longTermTrend: String,
    maxAge: Int
)

case class QuoteType(
    exchange: String,
    shortName: String,
    longName: String,
    exchangeTimezoneName: String,
    exchangeTimezoneShortName: String,
    isEsgPopulated: Boolean,
    gmtOffSetMilliseconds: String,
    quoteType: String,
    symbol: String,
    messageBoardId: String,
    market: String
)

case class SharesOutstanding(
    raw: Float,
    fmt: String
//    longFmt: Option[String]
)

case class SummaryDetail(
    forwardPE: SharesOutstanding
)
