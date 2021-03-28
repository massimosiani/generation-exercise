package io.github.massimosiani.generationalgo

type ValueOfChar = Int
type Individual = Vector[ValueOfChar]
type Population = List[Individual]

object Main:

  private val generationRetainment = 32
  private val generationSize = 512
  private val generationProficiency = generationSize / generationRetainment

  def main(args: Array[String]): Unit = solve()

  def solve(): (Individual, Int) = solveFor(
    (0 to 9).combinations(2).toList.map { l => (0 to 9).filterNot(l.toSet).toVector }
  )

  def solveFor(startingPopulation: Population): (Individual, Int) =
    Iterator.iterate((startingPopulation, 0))((p, c) => (createNextGeneration(p), c + 1))
      .dropWhile((p, c) => p.size > 1 && c < 20)
      .tapEach((p, c) => println(p.map(fitness)))
      .map((p, c) => (p.head, c))
      .next()

  def fitness(individual: Individual): Int =
    val chars = "sendmoremoney".toSeq.distinct.unwrap
    val values = chars.zip(individual).toMap
    val send = valueOfWord("send", values)
    val more = valueOfWord("more", values)
    val money = valueOfWord("money", values)
    (send + more - money).abs

  def valueOfWord(word: String, values: Map[Char, ValueOfChar]) =
      word.map(values).reduce(_ * 10 + _)

  def createNextGeneration(current: Population): Population =
      if (fitness(current.head) == 0)
        current.take(1)
      else current.take(generationRetainment)
        .flatMap(everyMutation(_).take(generationProficiency))
        .sortBy(fitness)

  def everyMutation(individual: Individual): Iterator[Individual] =
    val indicesSwapped = (0 to 7).combinations(2)
    util.Random.shuffle(indicesSwapped).map { case Seq(i, j) =>
        individual.updated(i, individual(j)).updated(j, individual(i))
    }

