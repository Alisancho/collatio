package ru.finance.analyst.boot

import akka.stream.alpakka.elasticsearch.{ApiVersion, ElasticsearchConnectionSettings, ElasticsearchParams, ElasticsearchWriteSettings, RetryAtFixedRate}
import scala.concurrent.duration._
trait ElasticsearchBoot:
  val connectionSettings = ElasticsearchConnectionSettings("http://localhost:9200")
  val elasticsearchParamsV7 = ElasticsearchParams.V7("collatioindex")

  val sinkSettings =
    ElasticsearchWriteSettings(connectionSettings)
      .withBufferSize(10)
      .withVersionType("internal")
      .withRetryLogic(RetryAtFixedRate(maxRetries = 5, retryInterval = 1.second))
      .withApiVersion(ApiVersion.V7)
