package ru.finance.analyst.service

import com.typesafe.scalalogging.LazyLogging
import ru.finance.analyst.ropository.MonitoringTaskRep


class BuisnessTaskServiceImpl(yahooFinanceService:YahooFinanceServiceImpl,
                              elasticsearchRep:MonitoringTaskRep) extends LazyLogging {
 logger.info("START_BuisnessTaskServiceImpl")

}
