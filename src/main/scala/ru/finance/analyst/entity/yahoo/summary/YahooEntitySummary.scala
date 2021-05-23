package ru.finance.analyst.entity.yahoo.summary

case class YahooSummaryResponse(
    price: PriceSummary,
    symbol: String
)

case class PriceSummary(
    regularMarketPrice: FinancialsTemplateSummary
//      quoteSourceName:String
)

case class FinancialsTemplateSummary(
    fmt: String,
    raw: Double
)
