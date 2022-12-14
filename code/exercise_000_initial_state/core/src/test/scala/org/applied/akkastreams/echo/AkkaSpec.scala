package org.applied.akkastreams.echo

import akka.actor.{ActorRefFactory, ActorSystem}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{BeforeAndAfterAll, Suite}

import scala.concurrent.duration._

trait AkkaSpec extends ScalaFutures with BeforeAndAfterAll { this: Suite =>
  implicit protected val system: ActorSystem = ActorSystem()
  implicit protected val actorRefFactory: ActorRefFactory = system

  override implicit def patienceConfig = super.patienceConfig.copy(timeout = 5.seconds)

  override protected def afterAll(): Unit = {
    super.afterAll()
    system.terminate()
  }
}
