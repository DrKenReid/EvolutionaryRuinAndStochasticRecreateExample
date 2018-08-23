/*
 * E: Ken@kenreid.co.uk
 * Written by Ken Reid
 */
package uk.co.kenreid.evolutionaryruinandstochasticrecreate;

import uk.co.kenreid.dataobjects.Context;
import uk.co.kenreid.function.Function;

// TODO: Auto-generated Javadoc
/**
 * The Class Phase0InitialSolution.
 */
public class Phase0InitialSolution {

	/** The attempt. */
	private String attempt;

	/** The ctx. */
	private final Context ctx;

	/** The function. */
	Function function;

	/**
	 * Instantiates a new phase 0 initial solution.
	 *
	 * @param ctx
	 *            the ctx
	 * @param function
	 *            the function
	 */
	public Phase0InitialSolution(final Context ctx, final Function function) {
		this.ctx = ctx;
		this.function = function;
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
	 * Go.
	 */
	void go() {

		final String potentialCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-";
		final int solutionLength = this.ctx.getProblem().length();
		String initialAttempt = "";
		while (initialAttempt.length() != solutionLength) {
			initialAttempt = initialAttempt + potentialCharacters.charAt(this.function.getRandom().nextInt(potentialCharacters.length()));
		}
		this.setAttempt(initialAttempt);
	}

	/**
	 * Sets the attempt.
	 *
	 * @param attempt
	 *            the new attempt
	 */
	public void setAttempt(final String attempt) {
		this.attempt = attempt;
	}

}
