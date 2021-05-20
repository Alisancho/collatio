package ru.finance.analyst.config

import com.typesafe.config.ConfigFactory

object Config:

  val config = ConfigFactory.load()
  
  object ServerConfig:
    val host:String = config.getString("server.host")
    val port:Int = config.getInt("server.port")

  object YahooConfig:
    val uriHost:String = "https://apidojo-yahoo-finance-v1.p.rapidapi.com"
    val xRapidapiHost:String = "apidojo-yahoo-finance-v1.p.rapidapi.com"
    val token:String = System.getenv.get("YAHOO_TOKEN")

  object ElasticConfig:
    val host:String = config.getString("elastic.host")
    val port:Int = config.getInt("elastic.port")

  object TelegramConfig:
    val mainChatID:Long = System.getenv.get("TELEGRAM_MAIN_ID").toLong
    val name:String = "MonitoringInvestBot"
    val token:String  = System.getenv.get("TELEGRAM_TOKEN")

