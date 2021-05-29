package ru.finance.analyst.typeclass.algebra

import ru.finance.analyst.entity.elastic.MonitoringTask
import ru.finance.analyst.entity.yahoo.summary.YahooSummaryResponse
import ru.finance.analyst.typeclass.MessengerType

object MessengerTypeObject {
  implicit class MessengerTypeYahooSummaryResponse(yahooSummaryResponse:YahooSummaryResponse)
    extends MessengerType[YahooSummaryResponse]{
     override def getTelegramMessenger: String =
       s"""
         |ShortName: ${yahooSummaryResponse.quoteType.shortName}
         |Price: ${yahooSummaryResponse.price.regularMarketPrice.fmt}
         |""".stripMargin
   }
  implicit class MessengerTypeMonitoringTask(monitoringTask:MonitoringTask)
    extends MessengerType[MonitoringTask]{
    override def getTelegramMessenger: String =
      s"""
         |Task id: ${monitoringTask.taskId}
         |Yahoo symbol: ${monitoringTask.yahooSymbol}
         |Yahoo region: ${monitoringTask.yahooRegion}
         |Price limit: ${monitoringTask.priceLimit}
         |Price up: ${monitoringTask.priceUp}
         |""".stripMargin
  }
}
