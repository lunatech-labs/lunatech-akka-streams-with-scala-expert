## Construction of a simple FIR based echo generator using DelayLine

We're going to implement 2 components that, in combination with the `DelayLine`
component, will allow us to build an FIR filter.

We will put these components in `FIRElements`.

The first component will allow us to select the "output signal" from the last
`DelayLine` component in an FIR filter. The other component will be used to feed
the "input signal" into the first `DelayLine` component of a filter.

Here is a step by step description of the exercise.

- Create a new val named `firInitial`  of type `Flow[Double, (Double, Double), NotUsed]`
  which should output a tuple of 2 `Double`s where each value is the same as the input
  value.

- Create a second new val named `firSelectOut` of type `Flow[(Double, Double), Double, NotUsed]`.
  Implement it as a Flow element that select the desired `Double` from
  from each processed element.


- Run the tests to check your solution

- You can now "hear" the result of your work by running a sample application.
  A working solution is provided for you: you just have to pull-in the code
  by running the following command from the sbt prompt:

```scala
pullTemplate scala/org/applied/akkastreams/FIR.scala
```

Have a look at the code in this file. It performs the following steps:

- create a `Source[Double]` from a sound file named `welcome.wav` located
  in the root folder of the project (and extracts some sound metadata)

- run the audio samples through an FIR filter which adds an echo to the sound

- write the processed signal to a file name `welcome-out.wav`

Use your favorite tool to play the original and processed audio files on
your computer (for example, _QuickTime_ for Mac users)