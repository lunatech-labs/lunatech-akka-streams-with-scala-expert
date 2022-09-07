## Initial state - explore supporting code

In this course, we are going to build audio echo generators in two different
ways. We will do this by implementing what are in essence digital signal
processors based on so-called 
[Finite Impulse Response](https://en.wikipedia.org/wiki/Finite_impulse_response)-
and Infinite Impulse Response filters.

We will use Scala as programming language and leverage the
[Akka Streams Library](https://akka.io/docs/).

We will process WAV audio files using the 
[WavFile Java library](http://www.labbookpages.co.uk/audio/javaWavFiles.html)
that was written by Dr. Andrew Greensted. Andrew is the maintainer of an
interesting website called
[The Lab Book Pages](http://www.labbookpages.co.uk/index.html).

This project is set up as a multi-project sbt build consisting of a `core` and
an `exercise` project.

The `core` project contains the `WavFile` (Java) library and a couple of
helpers classes and factory methods in Scala.

When writing code to solve the course exercises, you shouldn't need to work
directly with the `WavFile` Java library. Instead, use the Scala interfaces
provided in the `org.applied.akkastreams.wavfile` package. Before jumping to
the first exercise, you should first have a look at these interfaces.

## Exercise instructions

Have a look at the code in the `org.applied.akkastreams.wavfile` package.

Find the methods and supporting [case] classes that allow you to create
a `Source` of audio samples (encoded as `Double`) given a WAV-file.

Also, given that you will have audio data, given as a collection of `Double`
sample values, that needs to be written to a WAV-file, find out how what's
available to achieve this. 

HINT: You will also need to look at the `WavFile` Java class.