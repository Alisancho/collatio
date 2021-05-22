package ru.finance.analyst.boot
import ru.finance.analyst.config.Config.TelegramConfig
import ru.finance.analyst.service.{BuisnessTaskServiceImpl, TelegramServiceImpl}

trait InvestInfoBotBoot:
  val buisnessTaskServiceImpl:BuisnessTaskServiceImpl
  val telegrammServiceImpl = new TelegramServiceImpl(
    TelegramConfig.token,
    TelegramConfig.name,
    TelegramConfig.mainChatID)(buisnessTaskServiceImpl)
