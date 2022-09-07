# Implement a delay element

## Implementing the core streams element - the DelayLine

If we implement the `DelayLine` building block in a literal manner,
it would be an Akka Stream `Junction` with two inputs and two outputs.

The first output is a stream of the first input delayed by a specified
number of samples (`delay`). The second output is the sum of the second
input and the first output multiplied by a specified constant (`scaleFactor`).

An FIR filter can be constructed trivially by chaining `DelayLine`s.

Instead of implementing the DelayLine as an
Akka Streams junction, we will instead use a linear `Flow`
component which takes a tuple of `Double`s as input and a tuple
of `Double`s as output.

Your task is to implement the `DelayLine` Flow component using
`Flow.statefulMapConcat`.

> Note: as the name of `statefulMapConcat` implies, a flow stage
> based on it can keep track of a state that can be updated
> when processing stream elements.

Question: what data should be kept in the `DelayLine` flow?

## Step by step implementation

- Create a new Scala object named `FilterElements` in package
  `org.applied.akkastreams.echo`

- In the `FilterElements` object, create a new Scala object named
  `DelayLineFlow` with an `apply` method that takes two parameters:

  - `delay` of type `Int`
  - `scaleFactor` of type `Double`

  The `apply` method should return a `Flow[(Double, Double), (Double, Double), NotUsed]`

- In order to implement the required delay, you will have to choose
  a data structure that can store a number of samples equal to the
  value of `delay`.

  Two possible approaches that can work:

  - Use `scala.collection.mutable.Queue`
  - Use `scala.Array` to implement a circular buffer

- Implement the flow element based on `statefulMapConcat`:

  - This method takes one argument: a function from `Unit` to
    a function that processes the input (`Tuple2[Double, Double]`)
    and that returns a `Tuple2[Double, Double]`:

  - The first element of the returned tuple should be the
    input element delayed by `delay` samples

  - The second element of the returned tuple should be the
    sum of the second element of the input tuple and the
    product of the input element delayed by `delay` samples and
    the `scaleFactor`.

Note: The state of the `statefulMapConcat` flow component is
      kept in the body of the function.

- Run the provided test by executing `test` in sbt.

- One the tests pass, move to the next exercise by running `cmtc next-exercise <exercise-folder>`.

