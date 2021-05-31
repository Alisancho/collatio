package ru.finance.analyst.service

import yahoofinance.Stock

trait YahooFinanceService[F[_]] {
  def getStock(ticker: String): F[Stock]
}
