package ru.finance.analyst.entity.yahoo.summary

case class YahooSummaryResponse(
                                 symbol: String,
                                 price: PriceSummary,
                               )

case class AverageDailyVolume3Month(
                                     raw: Int,
                                     fmt: String,
                                     longFmt: String
                                   )

case class PriceSummary(
//                         quoteSourceName: String,
//                         currency: String,
                         regularMarketPrice: RegularMarketOpen,
//                         regularMarketVolume: AverageDailyVolume3Month,
                       )


case class RegularMarketOpen(
                              raw: Float,
                              fmt: String
                            )

