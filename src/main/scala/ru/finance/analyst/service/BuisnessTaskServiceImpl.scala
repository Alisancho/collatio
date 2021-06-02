package ru.finance.analyst.service

import akka.{Done, NotUsed}
import akka.stream.Materializer
import akka.stream.alpakka.elasticsearch.scaladsl.ElasticsearchFlow
import akka.stream.alpakka.elasticsearch.{WriteMessage, WriteResult}
import akka.stream.scaladsl.{Sink, Source}
import com.typesafe.scalalogging.LazyLogging
import ru.finance.analyst.entity.elastic.JsonHelper.itemFormat
import ru.finance.analyst.entity.elastic.MonitoringTask
import ru.finance.analyst.ropository.MonitoringTaskRep
import ru.finance.analyst.ropository.MonitoringTaskRep.{ALL_ACTIVE_TASK_CHAT_ID, DELETE_MY_ACTIVE_TASK}
import spray.json.JsonReader

import scala.collection.immutable
import scala.concurrent.Future

class BuisnessTaskServiceImpl(yahooFinanceService: YahooFinanceServiceFutureImpl, elasticsearchRep: MonitoringTaskRep)
    extends LazyLogging {
  logger.info("START_BuisnessTaskServiceImpl")

  def getMyActiveTask(
      chatId: Long
  )(implicit k: JsonReader[MonitoringTask], materializer: Materializer): Future[List[MonitoringTask]] =
    elasticsearchRep
      .getOnlyActiveTask(ALL_ACTIVE_TASK_CHAT_ID(chatId))
      .filter(p => p._2.status == "ACTIVE")
      .map(_._2)
      .runWith(Sink.collection)

  def deleteMyTask(chatId: Long, taskId: String)(implicit
      k: JsonReader[MonitoringTask],
      materializer: Materializer
  ): Future[Done] = {
    elasticsearchRep
      .getOnlyActiveTask(DELETE_MY_ACTIVE_TASK(taskId))
      .filter(k => k._2.chatId == chatId)
      .filter(k => k._2.status == "ACTIVE")
      .map(v => (v._1, v._2.copy(status = "DELETE")))
      .map(p => WriteMessage.createUpdateMessage(id = p._1, source = p._2))
      .mapAsync(1)(l => elasticsearchRep.writeMonitoringTask(l))
      .runWith(Sink.ignore)

  }

}
