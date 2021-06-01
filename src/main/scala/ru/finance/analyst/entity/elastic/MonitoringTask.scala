package ru.finance.analyst.entity.elastic

case class MonitoringTask (
    taskId: String,
    chatId: Long,
    status: String,
    name:String,
    yahooSymbol: String,
    priceLimit: Float,
    priceUp: Boolean
)



