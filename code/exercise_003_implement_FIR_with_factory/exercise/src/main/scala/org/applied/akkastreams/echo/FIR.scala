package org.applied.akkastreams.echo

import akka.actor.ActorSystem
import akka.stream.scaladsl.Flow
import akka.{Done, NotUsed}
import org.applied.akkastreams.wavfile._

import scala.concurrent.Future

object FIR {

  import FilterElements._

  val firFilterStages: List[FilterStage] =
    List((3000, -0.3), (1500, -0.2), (4500, -0.35)).map(_.toFilterStage)

  val firFilter: Flow[Double, Double, NotUsed] = BuildFIR(firFilterStages)

  def main(args: Array[String]): Unit = {
    // Get some sample audio data as a Source
    val waveFileName = "welcome.wav"
    val WaveSource(soundSource, waveSettings) = WaveSourceFromFile(waveFileName)

    // Create an output wave file with the same settings and the sample Audio
    val waveOutputFileName = "welcome-out.wav"
    val wavOutputFile = WaveOutputFile(waveOutputFileName, waveSettings)

    implicit val actorSystem: ActorSystem = ActorSystem()
    import actorSystem.dispatcher

    // Run the flow and sink it to a wav file
    val runFlow: Future[Done] =
      soundSource
        .via(firFilter)
        .grouped(1000)
        .runForeach(d => wavOutputFile.writeFrames(d.map(_ / 2.0).toArray, d.length))

    runFlow flatMap { _ => actorSystem.terminate() } onComplete { _ => wavOutputFile.close() }
  }

}
