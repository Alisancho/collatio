package ru.finance.analyst.config

import com.typesafe.config.{Config, ConfigFactory}

object Config {

  val config: Config = ConfigFactory.load()

  object ServerConfig   {
    val host: String = config.getString("server.host")
    val port: Int    = config.getInt("server.port")
  }

  object ElasticConfig  {
    val host: String = System.getenv("ELASTIC_HOST")
  }

  object TelegramConfig {
    val mainChatID: Long = System.getenv("TELEGRAM_MAIN_ID").toLong
    val name: String     = "MonitoringInvestBot"
    val token: String    = System.getenv("TELEGRAM_TOKEN")
  }
}

