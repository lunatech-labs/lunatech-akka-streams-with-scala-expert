## Building an FIR filter using a Factory

The objective of this exercise is to make it possible to build an `FIR` filter
by using an `FIR` filter factory that takes a specification of the delay and
scale factor for each filter stage.

So, we want to be able to end-up with the following minimalistic code to create
the `FIR` filter that we created in the previous exercise:

```scala
val firFilterStages: List[FilterStage] =
    List((3000, -0.3), (1500, -0.2), (4500, -0.35)).map(_.toFilterStage)

  val firFilter = BuildFIR(firFilterStages)

  // Run the flow and sink it to a wav file
  soundSource
    .via(firFilter)
```

Revisit the definition of `FilterStage` and have a look at `toFilterStage`. The latter is
a so-called extension method and it is defined on a `Tuple2[Int, Double]` (`(Int, Double)`).

Your task now is to implement the `BuildFIR` factory method:

- Add the `BuildFir` factory method to the object `FirElements`. `BuildFir` has two parameters:
  - `stages` of type `Seq[FilterStage]` which are the different FIR filter stages
  - `initialStage` of type `Flow[Double, (Double, Double), NotUsed]` which should have a
    default value set to `firInitial`. In this exercise we will invoke `buildFir` with the
    default value but in a later exercise the possibility to 'override' it will come in handy.
  - `BuildFir` should throw an `IllegalArgumentException` in the following cases:
    - `stages` should be non-empty
    - each stage in `stages` should have a delay of at least 1 (and of course, negative values
      for delay are not allowed)
  - Consider using `foldLeft` to implement the `BuildFir` functionality.
- Change the main method to utilise the factory method.

- Run the tests to check your solution

Run the main program and use your favorite tool to play the original and processed audio files on
your computer (for example, _QuickTime_ for Mac users)