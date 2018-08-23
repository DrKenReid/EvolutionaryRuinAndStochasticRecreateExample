/*
 * E: Ken@kenreid.co.uk
 * Written by Ken Reid
 */
package uk.co.kenreid.test;
/*
 *
 */

import java.util.Map;
import java.util.Random;

import uk.co.kenreid.dataobjects.Context;
import uk.co.kenreid.evolutionaryruinandstochasticrecreate.Phase2EvolutionaryRuin.Operator;
import uk.co.kenreid.evolutionaryruinandstochasticrecreate.Phase4SolutionAcceptance;

// TODO: Auto-generated Javadoc
/**
 * The Class Test.
 */
public class Test {

	/** The best solution. */
	private double bestSolution;

	/** The cooling rate. */
	private final double coolingRate;

	/** The mutationp 1 rate. */
	private final double mutationp1Rate;

	/** The mutationp 2 rate. */
	private final double mutationp2Rate;

	/** The original temperature. */
	private final double originalTemperature;

	/** The random. */
	Random random = new Random();

	/** The selection rate. */
	private final double selectionRate;

	/** The start. */
	private final long start;

	/** The temperature. */
	private final double temperature;

	/** The weightings. */
	private final Map<String, Double> weightings;

	/** The xls path. */
	String xlsPath = "C:\\ESRSoutput\\XLS\\";

	/**
	 * Instantiates a new test.
	 *
	 * @param temperature
	 *            the temperature
	 * @param coolingRate
	 *            the cooling rate
	 * @param weightings
	 *            the weightings
	 * @param selectionRate
	 *            the selection rate
	 * @param mutationp1Rate
	 *            the mutationp 1 rate
	 * @param mutationp2Rate
	 *            the mutationp 2 rate
	 * @param start
	 *            the start
	 */
	Test(final double temperature, final double coolingRate, final Map<String, Double> weightings, final double selectionRate, final double mutationp1Rate,
			final double mutationp2Rate, final long start) {
		this.temperature = temperature;
		this.originalTemperature = temperature;
		this.coolingRate = coolingRate;
		this.weightings = weightings;
		this.selectionRate = selectionRate;
		this.mutationp1Rate = mutationp1Rate;
		this.mutationp2Rate = mutationp2Rate;
		this.start = start;

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
	 * Gets the original temperature.
	 *
	 * @return the original temperature
	 */
	public double getOriginalTemperature() {
		return this.originalTemperature;
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
	 * Go.
	 */
	void go() {

		// Context class contains run-time parameters.
		final Context ctx = new Context();

		// can be parameterised for further customising selection & ruin capabilities.
		ctx.setStageOneOperator(Operator.MULTIPLY);
		ctx.setStageTwoOperator(Operator.MULTIPLY);
		ctx.setSelectionOperator(Operator.MULTIPLY);

		ctx.setSelectionRate(this.selectionRate);
		ctx.setRuinp1Rate(this.mutationp1Rate);
		ctx.setRuinp2Rate(this.mutationp2Rate);

		// "original" temperature is stored for use in probabilities within the Ruin
		// phase.
		ctx.setOriginalTemperature((int) this.temperature);
		ctx.setCoolingRate(this.getCoolingRate());

		// Set up weightings for each hard and soft constraint.
		// Stored as a map with the name of the constraint as a key
		// and the weighting as a double.

		ctx.setWeightingsPerConstraint(this.weightings);

		// Seems odd to call phase4 first, reasons for it is phase 4 controls
		// the acceptance of the solution, so controls the simulated annealing
		// aspect calling the main 3 phases.

		// Kinda like Test(Phase4(Phase1, Phase2, Phase3))
		final Phase4SolutionAcceptance solver = new Phase4SolutionAcceptance(ctx);
		solver.go();
		this.setBestSolution(solver.getBestSolution());
	}

	/**
	 * Sets the best solution.
	 *
	 * @param bestSolution
	 *            the new best solution
	 */
	public void setBestSolution(final double bestSolution) {
		this.bestSolution = bestSolution;
	}
}
