package ru.finance.analyst.service

import akka.NotUsed
import akka.stream.Materializer
import akka.stream.scaladsl.{Sink, Source}
import com.typesafe.scalalogging.LazyLogging
import ru.finance.analyst.entity.elastic.MonitoringTask
import ru.finance.analyst.ropository.MonitoringTaskRep
import ru.finance.analyst.ropository.MonitoringTaskRep.ALL_ACTIVE_TASK_CHAT_ID
import spray.json.JsonReader

import scala.concurrent.Future

class BuisnessTaskServiceImpl(yahooFinanceService: YahooFinanceServiceFutureImpl, elasticsearchRep: MonitoringTaskRep)
    extends LazyLogging {
  logger.info("START_BuisnessTaskServiceImpl")

  def getMyActiveTask(chatId: Long)(implicit k: JsonReader[MonitoringTask], materializer: Materializer): Future[List[MonitoringTask]] =
    elasticsearchRep
      .getOnlyActiveTask(ALL_ACTIVE_TASK_CHAT_ID(chatId))
      .map(t => {logger.info(t.toString);t})
      .runWith(Sink.collection)

}
