## Construction of a simple FIR based echo generator using DelayLine

## Step 1 - create a few extra Akka Streams components 

We're going to implement 2 components that, in combination with the `DelayLine`
component, will allow us to build an FIR filter.

We will put these components in `FIRElements`.

The first component will be used to feed the "input signal" into the first
`DelayLine` component of a filter.

The second component will allow us to select the "output signal" from the last
`DelayLine` component in an FIR filter. 

Here is a step by step description of the exercise.

- Create a new val named `firInitial`  of type `Flow[Double, (Double, Double), NotUsed]`
  which should output a tuple of 2 `Double`s where each input value duplicated
  in each of the two tuple elements.

- Create a second new val named `firSelectOut` of type `Flow[(Double, Double), Double, NotUsed]`.
  Implement it as a Flow element that select the desired `Double` from
  from each processed element.

- Run the tests to check your solution

## Step 2 - Process an audio file and observe echo generation

You can now "hear" the result of your work by running a sample application you're
now going to build.

- Create a new Scala object named `FIR` in package `org.applied.akkastreams.echo`.

- Define a val `firFilter` of type `Flow[Double, Double, NotUsed]`. Using the
  `FilterElements` Streams stages, construct a `Flow` that implements
  an FIR filter with three stages with the following parameters:
  - Stage 1: delay = `3000`, scalaFactor = `-0.3`
  - Stage 2: delay = `1500`, scalaFactor = `-0.2`
  - Stage 3: delay = `4500`, scalaFactor = `-0.35`

- Add a main method to object `FIR` that creates a `Source` from a file named
  `welcome.wav` and extracts the WAV audio settings from that file.
- Create a WAV output file named `welcome-out.wav`. You will need to apply the
  input file's extracted WAV settings.
- Create a new `ActorSystem` and bring it in scope implicitly.
- Import the dispatcher defined in the `ActorSystem`.
- Connect the WAV source to the `firFilter` flow.
- You should connect the output of the `firFilter` to a `Sink` that will
  write the processed data to the WAV output file and `run` it.
  As the size of the processed audio stream may be very large, we should
  split it up in manageable chunks and write those to the output file.

  Thinks about how to do this. Also think about how you will `terminate`
  the `ActorSystem` and closing the output file.

Hints:
  - Consider using the following components:
    - `runForeach`
    - `Flow.grouped`
  - Note that when running the Runnable Graph, the materialized value's type
    is `Future[Done]`. Remember that this `Future` is completed when the 
    Stream is completed.


Run the main program and use your favorite tool to play the original and processed audio files on
your computer (for example, _QuickTime_ for Mac users)