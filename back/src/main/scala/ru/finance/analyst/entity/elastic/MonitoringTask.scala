package ru.finance.analyst.entity.elastic

import spray.json.DefaultJsonProtocol.jsonFormat7
import spray.json.{DefaultJsonProtocol, JsonFormat}

case class MonitoringTask(
    taskId: String,
    chatId: Long,
    status: String,
    yahooSymbol: String,
    yahooRegion: String,
    priceLimit: Float,
    priceUp: Boolean
)
