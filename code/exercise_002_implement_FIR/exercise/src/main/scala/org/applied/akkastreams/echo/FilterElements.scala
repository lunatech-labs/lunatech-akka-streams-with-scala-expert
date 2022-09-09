package org.applied.akkastreams.echo

import akka.NotUsed
import akka.stream.scaladsl.Flow

import scala.collection.mutable.{Queue => MutableQueue}

object FilterElements {

  def delayLineFlow(delay: Int, scaleFactor: Double): Flow[(Double, Double), (Double, Double), NotUsed] =
    Flow[(Double, Double)].statefulMapConcat { () =>
      // mutable state needs to be kept inside the stage
      val eq = Array.fill(delay)(0.0d)
      var idx = 0

      { case (sample, ff) =>
        val delayedSample = eq(idx)
        eq(idx) = sample
        idx = (idx + 1) % delay
        Iterable((delayedSample, ff + delayedSample * scaleFactor))
      }
    }

  def delayLineFlowAlt(delay: Int, scaleFactor: Double): Flow[(Double, Double), (Double, Double), NotUsed] =
    Flow[(Double, Double)].statefulMapConcat { () =>
      // mutable state needs to be kept inside the stage
      val eq = MutableQueue(List.fill(delay)(0.0d): _*)

      { case (sample, ff) =>
        eq.enqueue(sample)
        val delayedSample = eq.dequeue()
        Iterable((delayedSample, ff + delayedSample * scaleFactor))
      }
    }

  val firInitial: Flow[Double, (Double, Double), NotUsed] =
    Flow[Double].map(sample => (sample, sample))

  val firSelectOut: Flow[(Double, Double), Double, NotUsed] =
    Flow[(Double, Double)].map { case (_, out) => out}
}
