import java.util.ArrayList;
import java.util.List;

public class nQueens {
	private final static int POPULATION_SIZE = 100;
	final static int CHROMOSOME_SIZE = 50;
	// A chromosome contains a queen's row for each subsequent column
	private final static int FITNESS_GOAL = (CHROMOSOME_SIZE - 1) * CHROMOSOME_SIZE;
	private final static int NUM_GENERATIONS = 1000;
	private final static double MUTATION_PROB = 0.01;

	private static List<Chromosome> population;
	private static int totalFitness = 0;
	private static boolean foundSolution = false;
	private static Chromosome maxChromosome = new Chromosome();

	public static void main(String[] args) {
		while (!foundSolution) {
			initPopulation();
			int i = 0;

			while (i < NUM_GENERATIONS) {
				updatePopulation(crossover(selection()));
				if (foundSolution) {
					return;
				}
				i++;
			}
		}
	}

	private static void initPopulation() {
		totalFitness = 0;
		population = new ArrayList<Chromosome>(POPULATION_SIZE);
		for (int i = 0; i < POPULATION_SIZE; i++) {
			List<Integer> solution = new ArrayList<Integer>(CHROMOSOME_SIZE);
			for (int j = 0; j < CHROMOSOME_SIZE; j++) {
				solution.add(j);
			}
			fisherYatesShuffle(solution);

			int fitness = getFitness(solution);
			if (fitness > maxChromosome.getFitness()) {
				maxChromosome.setFitness(fitness);
				maxChromosome.setSolution(solution);
				printMaxChromosome();
				if (fitness == FITNESS_GOAL) {
					foundSolution = true;
					return;
				}
			}

			population.add(new Chromosome(solution, fitness));
			totalFitness += fitness;
		}
	}

	private static int getFitness(List<Integer> solution) {
		int errorSum = 0;
		for (int i = 0; i < CHROMOSOME_SIZE; i++) {
			errorSum += numOpposingQueens(i, solution);
		}

		return FITNESS_GOAL - errorSum;
	}

	private static int numOpposingQueens(int currentQueen, List<Integer> chromosome) {
		int sum = 0;

		int row = chromosome.get(currentQueen);
		int col = currentQueen;

		for (int i = 0; i < CHROMOSOME_SIZE; i++) {
			if (i != currentQueen) {
				int opposingRow = chromosome.get(i);
				int opposingCol = i;
				if (row - opposingRow == 0 || col - opposingCol == 0
						|| Math.abs(row - opposingRow) == Math.abs(col - opposingCol)) {
					// checking rows, cols, and diagonals
					++sum;
				}
			}
		}
		return sum;
	}

	/* Used to create permutations from 0 to CHROMOSOME_SIZE */
	private static List<Integer> fisherYatesShuffle(List<Integer> numbersList) {
		for (int i = 0; i < numbersList.size(); i++) {
			int randomIndex = (int) (Math.random() * (numbersList.size() - i));
			int temp = numbersList.get(randomIndex);
			numbersList.set(randomIndex, numbersList.get(i));
			numbersList.set(i, temp);
		}
		return numbersList;
	}

	private static void printMaxChromosome() {
		String s = "Fitness: " + maxChromosome.getFitness() + "\nSolution: ";
		for (int i = 0; i < maxChromosome.getSolution().size(); i++) {
			s += maxChromosome.getSolution().get(i) + " ";
		}
		System.out.println(s);
	}

	private static List<Chromosome> selection() {
		List<Chromosome> selectedChromosomes = new ArrayList<Chromosome>(POPULATION_SIZE);
		for (int i = 0; i < POPULATION_SIZE; i++) {
			int sum = 0;
			int r = (int) (Math.random() * totalFitness) + 1;
			for (Chromosome c : population) {
				sum += c.getFitness();
				if (sum >= r) {
					selectedChromosomes.add(c);
					break;
				}
			}
			if (sum < r) {
				selectedChromosomes.add(population.get(POPULATION_SIZE - 1));
			}
		}
		if (selectedChromosomes.size() < POPULATION_SIZE) {
			System.out.println(selectedChromosomes.size());
		}
		return selectedChromosomes;
	}

	private static List<Chromosome> crossover(List<Chromosome> selectedChromosomes) {
		List<Chromosome> children = new ArrayList<Chromosome>(POPULATION_SIZE);
		for (int i = 0; i < POPULATION_SIZE; i++) {
			Chromosome parent = selectedChromosomes.get(i);
			Chromosome matingPartner = selectedChromosomes.get((int) (Math.random() * POPULATION_SIZE));

			// 1 to 49
			int crossoverPoint = (int) (Math.random() * CHROMOSOME_SIZE - 1) + 1;

			Chromosome child = new Chromosome();
			child.getSolution().addAll(parent.getSolution().subList(0, crossoverPoint));
			child.getSolution().addAll(matingPartner.getSolution().subList(crossoverPoint, CHROMOSOME_SIZE));
			mutate(child);

			children.add(child);
		}

		return children;
	}

	private static void mutate(Chromosome child) {
		for (int i = 0; i < CHROMOSOME_SIZE; i++) {
			if (Math.random() <= MUTATION_PROB) {
				child.getSolution().set(i, (int) (Math.random() * CHROMOSOME_SIZE));
			}
		}
	}

	private static void updatePopulation(List<Chromosome> newPopulation) {
		totalFitness = 0;
		population = new ArrayList<Chromosome>(POPULATION_SIZE);
		for (int i = 0; i < POPULATION_SIZE; i++) {
			Chromosome chromosome = newPopulation.get(i);
			List<Integer> solution = chromosome.getSolution();
			int fitness = getFitness(solution);

			if (fitness > maxChromosome.getFitness()) {
				maxChromosome.setFitness(fitness);
				maxChromosome.setSolution(solution);
				printMaxChromosome();
				if (fitness == FITNESS_GOAL) {
					foundSolution = true;
					return;
				}
			}

			chromosome.setFitness(fitness);
			population.add(chromosome);
			totalFitness += fitness;
		}
	}
}
