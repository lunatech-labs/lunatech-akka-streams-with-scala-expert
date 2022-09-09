package org.applied.akkastreams.echo

import akka.NotUsed
import akka.stream.scaladsl.{Flow, Sink, Source}
import org.applied.akkastreams.wavfile.AkkaSpec
import org.scalatest.freespec._

import scala.util.Random

trait FIRTestData {

  val testFilter: Flow[Double, Double, NotUsed] = FIR.firFilter

  val unitPulse: Source[Double, NotUsed] = Source(1.0d +: List.fill[Double](10_000)(0.0d))
}

class FIRElementsSpec extends AnyFreeSpec with AkkaSpec with FIRTestData {
  "An FIR filter when fed a unit pulse" -  {
    "should produce delayed and scaled pulses at the expected delays" in {
      val (delay1, delay2, delay3) = (3000, 1500, 4500)
      val expectedResponse =
        (1.0d +: Vector.fill[Double](delay1 - 1)(0.0d)) ++
        (-0.3d +: Vector.fill[Double](delay2 - 1)(0.0d)) ++
        (-0.24d +: Vector.fill[Double](delay3 - 1)(0.0d)) ++
        (-0.35d +: Vector.fill[Double](10_000 - delay1 - delay2 - delay3)(0.0d))


      val firResponse = unitPulse
        .via(testFilter)
        .runWith(Sink.seq)
        .futureValue
      assert(firResponse == expectedResponse)
    }
  }

  "An FIR output selection element, 'firInitial'" -  {
    "should duplicate its input elements in tuples" in {
      val inputElements = (1 to 100).map(_ => Random.nextDouble())
      val source = Source(inputElements)

      val expectedResponse = inputElements.zip(inputElements)

      val response = source
        .via(FilterElements.firInitial)
        .runWith(Sink.seq)
        .futureValue
      assert(response == expectedResponse)
    }
  }

  "An initial FIR output selector element, 'firSelectOut'" -  {
    "should retain the second tuple element from its input elements" in {
      val inputElements1 = (1 to 100).map(_ => Random.nextDouble())
      val inputElements2 = (1 to 100).map(_ => Random.nextDouble())
      val source1 = Source(inputElements1)
      val source2 = Source(inputElements2)
      val source = source1.zip(source2)

      val expectedResponse = inputElements2

      val response = source
        .via(FilterElements.firSelectOut)
        .runWith(Sink.seq)
        .futureValue
      assert(response == expectedResponse)
    }
  }
}
