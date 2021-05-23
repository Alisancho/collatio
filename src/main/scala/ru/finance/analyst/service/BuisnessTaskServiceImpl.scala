package ru.finance.analyst.service

import com.typesafe.scalalogging.LazyLogging
import ru.finance.analyst.ropository.ElasticsearchRep


class BuisnessTaskServiceImpl(yahooFinanceService:YahooFinanceServiceImpl,
                              elasticsearchRep:ElasticsearchRep) extends LazyLogging {
 logger.info("START_BuisnessTaskServiceImpl")

}
