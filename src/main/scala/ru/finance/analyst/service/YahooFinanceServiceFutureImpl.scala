package ru.finance.analyst.service

import com.typesafe.scalalogging.LazyLogging
import yahoofinance.Stock
import yahoofinance.YahooFinance

import scala.concurrent.{ExecutionContextExecutor, Future}

class YahooFinanceServiceFutureImpl(implicit ex:ExecutionContextExecutor) extends LazyLogging with YahooFinanceService[Future] {
  logger.info("START_YahooFinanceServiceImpl")

  override def getStock(ticker: String): Future[Stock] =
    for {
      stock <- Future(YahooFinance.get(ticker))
      res    = if (stock == null) throw new RuntimeException("Ticker not found") else stock
    } yield res
}
