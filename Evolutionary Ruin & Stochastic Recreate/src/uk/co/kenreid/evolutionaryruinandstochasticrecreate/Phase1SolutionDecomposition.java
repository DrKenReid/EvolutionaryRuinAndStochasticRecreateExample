/*
 * E: Ken@kenreid.co.uk
 * Written by Ken Reid
 */
package uk.co.kenreid.evolutionaryruinandstochasticrecreate;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import uk.co.kenreid.dataobjects.Context;
import uk.co.kenreid.function.Function;

/**
 * The Class Phase1SolutionDecomposition.
 */
public class Phase1SolutionDecomposition {

	/** The attempt. */
	private final String attempt;

	/** The ctx. */
	private final Context ctx;

	/** The function. */
	private final Function function;

	/** The sc 1 fitness per component. */
	private HashMap<Integer, Double> sc1FitnessPerComponent;

	/** The unweighted fitness mapping. */
	private final Map<String, Double> unweightedFitnessMapping = new HashMap<>();

	/** The weighted fitness mapping. */
	private Map<String, Double> weightedFitnessMapping = new HashMap<>();

	/**
	 * Instantiates a new phase 1 solution decomposition.
	 *
	 * @param ctx
	 *            the ctx
	 * @param attempt
	 *            the attempt
	 * @param function
	 *            the function
	 */
	public Phase1SolutionDecomposition(final Context ctx, final String attempt, final Function function) {
		this.ctx = ctx;
		this.attempt = attempt;
		this.function = function;
	}

	/**
	 * Decompose.
	 *
	 * @return the double
	 */
	public double decompose() {
		// Work out fitness for all components, store the averages, weighted by
		// parameterised soft constraint.
		final Map<String, Double> weightings = this.ctx.getWeightingsPerConstraint();
		final double sc1fitness = this.sc1();
		this.getWeightedFitnessMapping().put("SC1", sc1fitness * weightings.get("SC1"));
		this.getUnweightedFitnessMapping().put("SC1", sc1fitness);

		final double sc2fitness = this.sc2();
		this.getWeightedFitnessMapping().put("SC2", sc2fitness * weightings.get("SC2"));
		this.getUnweightedFitnessMapping().put("SC2", sc2fitness);

		// return a double which is the overall weighted fitness.
		return this.getOverallWeightedFitness();
	}

	/**
	 * Gets the attempt.
	 *
	 * @return the attempt
	 */
	public String getAttempt() {
		return this.attempt;
	}

	/**
	 * Gets the fitness of a single component.
	 *
	 * @param index
	 *            the index
	 * @return the componential fitness
	 */
	public double getComponentialFitness(final int index) {
		final double sc1componentFitness = this.sc1componentFitness(index);
		final double sc2componentFitness = this.sc2componentFitness(index);

		return sc1componentFitness * this.ctx.getWeightingsPerConstraint().get("SC1") + sc2componentFitness * this.ctx.getWeightingsPerConstraint().get("SC2");
	}

	/**
	 * Gets the function.
	 *
	 * @return the function
	 */
	public Function getFunction() {
		return this.function;
	}

	/**
	 * Gets the overall weighted fitness.
	 *
	 * @return the overall weighted fitness
	 */
	public double getOverallWeightedFitness() {
		double d = 0;
		for (final Entry<String, Double> e : this.weightedFitnessMapping.entrySet()) {
			d += e.getValue();
		}
		return d;
	}

	/**
	 * Gets the sc 1 fitness per component.
	 *
	 * @return the sc 1 fitness per component
	 */
	public HashMap<Integer, Double> getSc1FitnessPerComponent() {
		return this.sc1FitnessPerComponent;
	}

	/**
	 * Gets the unweighted fitness mapping.
	 *
	 * @return the unweighted fitness mapping
	 */
	public Map<String, Double> getUnweightedFitnessMapping() {
		return this.unweightedFitnessMapping;
	}

	/**
	 * Gets the weighted fitness mapping.
	 *
	 * @return the weighted fitness mapping
	 */
	public // Stores average fitness per SC
	Map<String, Double> getWeightedFitnessMapping() {
		return this.weightedFitnessMapping;
	}

	/**
	 * Sc 1 for all components.
	 *
	 * @return the double
	 */
	private double sc1() {
		this.setSc1FitnessPerComponent(new HashMap<Integer, Double>());
		double fitness = 0;
		for (int index = 0; index < this.attempt.length(); index++) {
			final double componentFitness = this.sc1componentFitness(index);
			this.getSc1FitnessPerComponent().put(index, componentFitness);
			fitness = fitness + componentFitness;

		}
		return fitness / this.attempt.length();
	}

	/**
	 * Sc 1 for a single component.
	 *
	 * @param index
	 *            the index
	 * @return the double
	 */
	public double sc1componentFitness(final int index) {

		final char correctChar = this.ctx.getProblem().charAt(index);
		final char attemptChar = this.attempt.charAt(index);

		if (correctChar == attemptChar) {
			return 1.0;
		}

		if (attemptChar == '\'') {
			return 0;
		}

		final double correctAsciiIndex = correctChar;
		final double attemptAsciiIndex = attemptChar;

		double difference;
		if (correctAsciiIndex > attemptAsciiIndex) {
			difference = correctAsciiIndex - attemptAsciiIndex;
			difference /= correctAsciiIndex;
		}
		else {
			difference = attemptAsciiIndex - correctAsciiIndex;
			difference /= attemptAsciiIndex;
		}
		return difference;

	}

	/**
	 * Sc 2.
	 *
	 * @return the double
	 */
	private double sc2() {
		return 1;
	}

	/**
	 * Sc 2 component fitness.
	 *
	 * @param index
	 *            the index
	 * @return the double
	 */
	private double sc2componentFitness(@SuppressWarnings("unused") final int index) {
		return 1;
	}

	/**
	 * Sets the sc 1 fitness per component.
	 *
	 * @param sc1FitnessPerComponent
	 *            the sc 1 fitness per component
	 */
	public void setSc1FitnessPerComponent(final HashMap<Integer, Double> sc1FitnessPerComponent) {
		this.sc1FitnessPerComponent = sc1FitnessPerComponent;
	}

	/**
	 * Sets the weighted fitness mapping.
	 *
	 * @param weightedFitnessMapping
	 *            the weighted fitness mapping
	 */
	public void setWeightedFitnessMapping(final Map<String, Double> weightedFitnessMapping) {
		this.weightedFitnessMapping = weightedFitnessMapping;
	}
}