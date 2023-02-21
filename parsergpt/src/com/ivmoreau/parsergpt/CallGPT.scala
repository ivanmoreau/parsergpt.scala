package com.ivmoreau.parsergpt

import scala.concurrent.ExecutionContext
import akka.stream.Materializer
import akka.actor.ActorSystem
import io.cequence.openaiscala.service.OpenAIServiceFactory
import io.cequence.openaiscala.domain.settings.CreateCompletionSettings
import io.cequence.openaiscala.domain.ModelId
import scala.concurrent.Await
import scala.concurrent.duration._


private[parsergpt] object CallGPT {
  def apply(a: String)(implicit apiKey: String): String =
    implicit val ec = ExecutionContext.global
    implicit val materializer = Materializer(ActorSystem())
    val service = OpenAIServiceFactory(
     apiKey = apiKey,
     orgId = None
    )
    val futurea = service.createCompletion(a, CreateCompletionSettings(
      model = ModelId.code_cushman_001,
      stop = Seq("\n", "%%")
    )).map { a =>
      a.choices.head.text  
    }

    Await.result(futurea, Duration(10000, MILLISECONDS))
}
