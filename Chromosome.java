import java.util.ArrayList;
import java.util.List;

public class Chromosome {
	private List<Integer> solution;
	private int fitness;

	public Chromosome() {
		solution = new ArrayList<Integer>(nQueens.CHROMOSOME_SIZE);
		fitness = 0;
	}

	public Chromosome(List<Integer> solution, int fitness) {
		this.setSolution(solution);
		this.setFitness(fitness);
	}

	public int getFitness() {
		return fitness;
	}

	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

	public List<Integer> getSolution() {
		return solution;
	}

	public void setSolution(List<Integer> solution) {
		this.solution = solution;
	}
}
