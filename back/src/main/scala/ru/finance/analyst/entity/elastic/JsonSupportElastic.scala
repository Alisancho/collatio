package ru.finance.analyst.entity.elastic

import ru.finance.analyst.entity.elastic._

trait JsonSupportElastic:
  import spray.json._
  import DefaultJsonProtocol._

  given JsonFormat[MonitoringTask] = jsonFormat7(MonitoringTask.apply)
