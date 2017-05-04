import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class nQueens {
	private final static int POPULATION_SIZE = 100;
	private final static int CHROMOSOME_SIZE = 50;
	// A chromosome contains a
	// queen's row for each
	// subsequent column
	private final static int FITNESS_GOAL = CHROMOSOME_SIZE * CHROMOSOME_SIZE;
	private final static int NUM_GENERATIONS = 100;

	private static Map<List<Integer>, Integer> populationFitnessMap;

	public static void main(String[] args) {
		for (int i = 0; i < NUM_GENERATIONS; i++) {
			initPopulation();
			// TODO
		}
	}

	private static void initPopulation() {
		populationFitnessMap = new HashMap<List<Integer>, Integer>(POPULATION_SIZE);
		for (int i = 0; i < POPULATION_SIZE; i++) {
			List<Integer> chromosome = new ArrayList<Integer>(CHROMOSOME_SIZE);
			for (int j = 0; j < CHROMOSOME_SIZE; j++) {
				chromosome.add(j);
			}
			fisherYatesShuffle(chromosome);

			if (!populationFitnessMap.containsKey(chromosome)) {
				populationFitnessMap.put(chromosome, getFitness(chromosome));
			} else {
				--i;
			}
		}
	}

	private static int getFitness(List<Integer> chromosome) {
		int errorSum = 0;
		for (int i = 0; i < CHROMOSOME_SIZE; i++) {
			errorSum += numOpposingQueens(i, chromosome);
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
			numbersList.add(randomIndex, numbersList.get(i));
			numbersList.add(i, temp);
		}
		return numbersList;
	}

	private static void printChromosome(List<Integer> chromosome) {
		String s = "";
		for (int i = 0; i < chromosome.size(); i++) {
			s += chromosome.get(i) + " ";
		}
		System.out.println(s);
	}
}
