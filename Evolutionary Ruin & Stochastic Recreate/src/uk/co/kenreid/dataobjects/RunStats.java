/*
 * E: Ken@kenreid.co.uk
 * Written by Ken Reid
 */
package uk.co.kenreid.dataobjects;

// TODO: Auto-generated Javadoc
/**
 * The Class RunStats.
 */
public class RunStats {

	/** The best solution. */
	private final double bestSolution;

	/** The cooling rate. */
	private final double coolingRate;

	/** The end. */
	private final long end;

	/** The iteration. */
	private final int iteration;

	/** The mutation rate 1. */
	private double mutationRate1;

	/** The mutation rate 2. */
	private double mutationRate2;

	/** The sc 1. */
	private double sc1;

	/** The sc 2. */
	private double sc2;

	/** The selection rate. */
	private double selectionRate;

	/** The start. */
	private final long start;

	/** The temperature. */
	private final double temperature;

	/**
	 * Instantiates a new run stats.
	 *
	 * @param iteration
	 *            the iteration
	 * @param temperature
	 *            the temperature
	 * @param coolingRate
	 *            the cooling rate
	 * @param fitness
	 *            the fitness
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 * @param softConstraintWeightings
	 *            the soft constraint weightings
	 * @param selectionAndMutationRates
	 *            the selection and mutation rates
	 */
	public RunStats(final int iteration, final double temperature, final double coolingRate, final double fitness, final long start, final long end,
			final double[] softConstraintWeightings, final double[] selectionAndMutationRates) {
		this.iteration = iteration;
		this.temperature = temperature;
		this.coolingRate = coolingRate;
		this.bestSolution = fitness;
		this.start = start / 1000000000;
		this.end = end / 1000000000;
		this.sc1 = softConstraintWeightings[0];
		this.sc2 = softConstraintWeightings[1];
		this.selectionRate = selectionAndMutationRates[0];
		this.mutationRate1 = selectionAndMutationRates[1];
		this.mutationRate2 = selectionAndMutationRates[2];
	}

	/**
	 * Gets the best solution.
	 *
	 * @return the best solution
	 */
	public double getBestSolution() {
		return this.bestSolution;
	}

	/**
	 * Gets the cooling rate.
	 *
	 * @return the cooling rate
	 */
	public double getCoolingRate() {
		return this.coolingRate;
	}

	/**
	 * Gets the end.
	 *
	 * @return the end
	 */
	public long getEnd() {
		return this.end;
	}

	/**
	 * Gets the iteration.
	 *
	 * @return the iteration
	 */
	public int getIteration() {
		return this.iteration;
	}

	/**
	 * Gets the mutation rate 1.
	 *
	 * @return the mutation rate 1
	 */
	public double getMutationRate1() {
		return this.mutationRate1;
	}

	/**
	 * Gets the mutation rate 2.
	 *
	 * @return the mutation rate 2
	 */
	public double getMutationRate2() {
		return this.mutationRate2;
	}

	/**
	 * Gets the sc 1.
	 *
	 * @return the sc 1
	 */
	public double getSc1() {
		return this.sc1;
	}

	/**
	 * Gets the sc 2.
	 *
	 * @return the sc 2
	 */
	public double getSc2() {
		return this.sc2;
	}

	/**
	 * Gets the selection rate.
	 *
	 * @return the selection rate
	 */
	public double getSelectionRate() {
		return this.selectionRate;
	}

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	public long getStart() {
		return this.start;
	}

	/**
	 * Gets the temperature.
	 *
	 * @return the temperature
	 */
	public double getTemperature() {
		return this.temperature;
	}

	/**
	 * Sets the mutation rate 1.
	 *
	 * @param mutationRate1
	 *            the new mutation rate 1
	 */
	public void setMutationRate1(final double mutationRate1) {
		this.mutationRate1 = mutationRate1;
	}

	/**
	 * Sets the mutation rate 2.
	 *
	 * @param mutationRate2
	 *            the new mutation rate 2
	 */
	public void setMutationRate2(final double mutationRate2) {
		this.mutationRate2 = mutationRate2;
	}

	/**
	 * Sets the sc 1.
	 *
	 * @param sc1
	 *            the new sc 1
	 */
	public void setSc1(final double sc1) {
		this.sc1 = sc1;
	}

	/**
	 * Sets the sc 2.
	 *
	 * @param sc2
	 *            the new sc 2
	 */
	public void setSc2(final double sc2) {
		this.sc2 = sc2;
	}

	/**
	 * Sets the selection rate.
	 *
	 * @param selectionRate
	 *            the new selection rate
	 */
	public void setSelectionRate(final double selectionRate) {
		this.selectionRate = selectionRate;
	}

}
