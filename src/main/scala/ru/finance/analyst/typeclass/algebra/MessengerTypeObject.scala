package ru.finance.analyst.typeclass.algebra

import ru.finance.analyst.entity.elastic.MonitoringTask

import ru.finance.analyst.typeclass.MessengerType
import yahoofinance.Stock

object MessengerTypeObject {
  implicit class MessengerTypeYahooStock(stock:Stock)
    extends MessengerType[Stock]{
     override def getTelegramMessenger: String =
       s"""
         |ShortName: ${stock.getName}
         |Price: ${stock.getQuote.getPrice.toString}
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
