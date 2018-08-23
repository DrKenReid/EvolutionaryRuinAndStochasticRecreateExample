/*
 * E: Ken@kenreid.co.uk
 * Written by Ken Reid
 */
package uk.co.kenreid.evolutionaryruinandstochasticrecreate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.kenreid.dataobjects.Context;
import uk.co.kenreid.function.Function;
import uk.co.kenreid.io.Output;

/**
 * The Class Phase4SolutionAcceptance.
 */
public class Phase4SolutionAcceptance {

	/** The accepted solutions. */
	List<String> acceptedSolutions = new ArrayList<>();

	/** The actually best fitness. */
	private double actuallyBestFitness;

	/** The attempt. */
	String attempt = "";

	/** The ctx. */
	private final Context ctx;

	/** The function. */
	Function function = new Function();

	/** The original attempt. */
	String originalAttempt = "";

	/** The temperature. */
	private double temperature;

	/**
	 * Instantiates a new phase 4 solution acceptance.
	 *
	 * @param ctx
	 *            the ctx
	 */
	public Phase4SolutionAcceptance(final Context ctx) {
		this.ctx = ctx;
	}

	/**
	 * Acceptance probability.
	 *
	 * @param originalSolutionFitness
	 *            the original solution fitness
	 * @param mutatedSolutionFitness
	 *            the mutated solution fitness
	 * @return the double
	 */
	private double acceptanceProbability(final double originalSolutionFitness, final double mutatedSolutionFitness) {
		// if fitness has improved, keep it!
		final double originalEnergyCost = (1 - originalSolutionFitness) * 1000;
		final double newEnergyCost = (1 - mutatedSolutionFitness) * 1000;

		if (newEnergyCost < originalEnergyCost) {
			return 1.0;
		}
		// otherwise, MAYBE keep it
		final double chance = Math.exp((originalEnergyCost - newEnergyCost) / this.temperature);
		return chance;
	}

	/**
	 * Gets the best solution.
	 *
	 * @return the best solution
	 */
	public double getBestSolution() {
		return this.actuallyBestFitness;
	}

	/**
	 * Go.
	 */
	public void go() {

		// ERSR is vague on how to set up an initial solution. Here it's entirely random
		// from allowed components.
		final Phase0InitialSolution initialize = new Phase0InitialSolution(this.ctx, this.function);
		initialize.go();
		this.attempt = initialize.getAttempt();

		// originalAttempt is for comparison between best solution and original.
		this.originalAttempt = this.attempt;

		// previousAttempt is for reverting if a change is worse than the new state.
		String previousAttempt = this.attempt;

		// store all fitnesses for optional outputs
		final List<Phase1SolutionDecomposition> p1s = new ArrayList<>();
		final Map<Integer, Double> overallFitnessPerIteration = new HashMap<>();

		// analyse initial solution
		Phase1SolutionDecomposition p1 = new Phase1SolutionDecomposition(this.ctx, this.attempt, this.function);
		p1.decompose();
		p1s.add(p1);

		this.temperature = this.ctx.getTemperature();
		final double coolingRate = this.ctx.getCoolingRate();
		int counter = 0;
		double bestFitness = p1.getOverallWeightedFitness();

		// We don't actually use this, but can be useful for outputs
		this.actuallyBestFitness = p1.getOverallWeightedFitness();

		// Stop if we find the best possible solution (in realistic scenarios that will
		// rarely happen)
		// Otherwise stop when monte-carlo acceptance criterion is met
		while (this.temperature > 0.00001 && this.actuallyBestFitness != 1) {
			// Ruin phase
			final Phase2EvolutionaryRuin ruin = new Phase2EvolutionaryRuin(p1, this.temperature, this.ctx, this.attempt, this.function);
			this.attempt = ruin.getAttempt();

			// Recreate phase
			final Phase3StochasticRecreate p3 = new Phase3StochasticRecreate(this.ctx, this.attempt, this.function);
			p3.rebuild();
			this.attempt = p3.getAttempt();

			// Analyze the changed phase
			final Phase1SolutionDecomposition transitionedp1 = new Phase1SolutionDecomposition(this.ctx, this.attempt, this.function);
			final double currentFitness = transitionedp1.decompose();

			// if best yet, save as such
			if (currentFitness > this.actuallyBestFitness) {
				this.actuallyBestFitness = currentFitness;
			}

			// Save new fitness value
			overallFitnessPerIteration.put(counter, transitionedp1.getOverallWeightedFitness());
			p1s.add(transitionedp1);

			// calculate if keep or revert.
			final double acceptanceProbability = this.acceptanceProbability(bestFitness, currentFitness);
			final double chance = this.function.getRandom().nextDouble();

			// if good or lucky
			if (acceptanceProbability > chance) {
				bestFitness = currentFitness;
				p1 = transitionedp1;
				previousAttempt = this.attempt;
				this.acceptedSolutions.add(this.attempt);
			}
			// if worse or unlucky
			else {
				// undo
				this.attempt = previousAttempt;
			}

			// Cool system
			this.temperature *= 1 - coolingRate;
			counter++;
		}
		// When finished, output XLS(X) files for some information on run.
		this.output(p1, overallFitnessPerIteration, p1s);
	}

	/**
	 * Output.
	 *
	 * @param p1
	 *            the p 1
	 * @param overallFitnessPerIteration
	 *            the overall fitness per iteration
	 * @param p1s
	 *            the p 1 s
	 */
	private void output(final Phase1SolutionDecomposition p1, final Map<Integer, Double> overallFitnessPerIteration,
			final List<Phase1SolutionDecomposition> p1s) {
		final long begin = System.currentTimeMillis();
		// filenames have hours / min / seconds info at the beginning. useful when
		// running multiple times.
		String time = "";
		final long ms = Calendar.getInstance().getTimeInMillis();
		final SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-ss");
		final Date resultdate = new Date(ms);
		time = "" + sdf.format(resultdate);

		// Takes some time. Comment out as preferred.
		final Output out = new Output(overallFitnessPerIteration, this.acceptedSolutions, this.originalAttempt, this.attempt, this.ctx);
		out.outputXLSOverallFitnessOfFirstXIterations(time + " iterationsFitness");
		out.outputXLSXacceptedSolutions(time + " accepted solutions");
		out.outputXLSXFitnessOfBestSolutionComparedToOriginal(time + " best solution compared to initial");

		System.out.println("Saving files took " + (System.currentTimeMillis() - begin) / 1000 + " second(s)");
	}

}
