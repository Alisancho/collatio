package ru.finance.analyst.entity.elastic

case class MonitoringTask (
    taskId: String,
    chatId: Long,
    status: String,
    yahooSymbol: String,
    yahooRegion: String,
    priceLimit: Float,
    priceUp: Boolean
)
