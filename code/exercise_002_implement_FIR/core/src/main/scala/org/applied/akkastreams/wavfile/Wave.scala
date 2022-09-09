package org.applied.akkastreams.wavfile

import akka.NotUsed
import akka.stream.scaladsl.Source

final case class WaveSettings(numChannels: Int, numFrames: Long, validBits: Int, sampleRate: Long)
final case class WaveSource(source: Source[Double, NotUsed], waveSetting: WaveSettings)
