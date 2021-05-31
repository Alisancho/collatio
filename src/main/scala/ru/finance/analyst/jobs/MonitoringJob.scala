package ru.finance.analyst.jobs


import com.typesafe.scalalogging.LazyLogging

object MonitoringJob extends LazyLogging{
//  def startMonitoringJob(elasticsearchRep: MonitoringTaskRep, yahooFinanceService:YahooFinanceServiceImpl)
//                        (implicit materializer: Materializer, ex: ExecutionContextExecutor): Source[Unit, Cancellable] ={
//    Source.tick(4.second,10.second,"")
//      .map(x => println("NEW_TICK"))
//      .flatMapConcat(_ => elasticsearchRep.getMonitoringTask)
//      .map(m => {logger.info("NEW_TASK="+ m.toString);m})
//      .mapAsync(parallelism = 1)(m => yahooFinanceService.getInfo(m.yahooSymbol, m.yahooRegion, SUMMARY))
//      .mapAsync(parallelism = 1)(n => n.entity.toStrict(5.seconds))
//      .map(_.data.utf8String)
//      .map(b => JsonParser(b).convertTo[YahooSummaryResponse])
//      .map(z => println(z.toString))
//
//
//  }
}
