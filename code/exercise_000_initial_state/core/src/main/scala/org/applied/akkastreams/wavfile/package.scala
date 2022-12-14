package org.applied.akkastreams

import akka.stream.scaladsl.Source
import uk.co.labbookpages.WavFile

import java.io.File

package object wavfile {

  implicit class FilterStageOps(val s: (Int, Double)) extends AnyVal {
    def toFilterStage: FilterStage = FilterStage(s._1, s._2)
  }

  object WaveOutputFile {
    def apply(wavFileName: String, wavSettings: WaveSettings): WavFile = {
      WavFile.newWavFile(
        new File(wavFileName),
        wavSettings.numChannels,
        wavSettings.numFrames,
        wavSettings.validBits,
        wavSettings.sampleRate)
    }
  }

  object WaveSourceFromFile {
    def apply(wavFileName: String): WaveSource = {
      val BUFSIZE = 256
      val wavFile = WavFile.openWavFile(new File(wavFileName))
      val numChannels = wavFile.getNumChannels
      val numFrames = wavFile.getNumFrames
      val validBits = wavFile.getValidBits
      val sampleRate = wavFile.getSampleRate
      println(s"Number of channels = $numChannels, number of frames: $numFrames, sampleRate: $sampleRate")
      val buffer = new Array[Double](256 * numChannels)

      @scala.annotation.tailrec
      def rf(wavFile: WavFile, buf: Vector[Double] = Vector.empty[Double]): Vector[Double] = {
        wavFile.readFrames(buffer, BUFSIZE) match {
          case 0 =>
            buf
          case BUFSIZE =>
            rf(wavFile, buf ++ buffer.toVector)
          case n =>
            buf ++ buffer.toVector.take(n)
        }
      }

      val source = Source(rf(wavFile))
      wavFile.close()
      println(s"Source audio from $wavFileName")
      WaveSource(source, WaveSettings(numChannels, numFrames, validBits, sampleRate))
    }
  }
}
