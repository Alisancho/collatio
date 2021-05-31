package ru.finance.analyst.service

import com.typesafe.scalalogging.LazyLogging
import ru.finance.analyst.ropository.MonitoringTaskRep


class BuisnessTaskServiceImpl(yahooFinanceService:YahooFinanceServiceFutureImpl,
                              elasticsearchRep:MonitoringTaskRep) extends LazyLogging {
 logger.info("START_BuisnessTaskServiceImpl")

}
