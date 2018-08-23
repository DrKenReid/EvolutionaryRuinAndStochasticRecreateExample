/*
 * E: Ken@kenreid.co.uk
 * Written by Ken Reid
 */
package uk.co.kenreid.evolutionaryruinandstochasticrecreate;

import uk.co.kenreid.dataobjects.Context;
import uk.co.kenreid.function.Function;

/**
 * The Class Phase3StochasticRecreate.
 */
public class Phase3StochasticRecreate {

	/** The attempt. */
	private String attempt;

	/** The ctx. */
	private final Context ctx;

	/** The function. */
	Function function;

	/**
	 * Instantiates a new phase 3 stochastic recreate.
	 *
	 * @param ctx
	 *            the ctx
	 * @param attempt
	 *            the attempt
	 * @param function
	 *            the function
	 */
	public Phase3StochasticRecreate(final Context ctx, final String attempt, final Function function) {
		this.ctx = ctx;
		this.function = function;
		this.setAttempt(attempt);

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
	 * Rebuild. This functions by finding all '%' chars in the String, then
	 * replacing each with a new character. First it only accepts 100% fitness, then
	 * 100% * 0.99 fitness, and so on. This isn't exhaustive so eventually a "good
	 * enough" solution is acceptable. Of course since this is an example, we could
	 * easily make this exhaustive but that wouldn't show the workings of ERSR.
	 */
	public void rebuild() {
		for (int index = 0; index < this.getAttempt().length(); index++) {
			if (this.getAttempt().charAt(index) == '%') {
				boolean barLoweredSufficiently = false;
				double fitnessAllowed = 1.0;
				final String potentialCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-";
				while (!barLoweredSufficiently) {
					final char newComponent = potentialCharacters.charAt(this.function.getRandom().nextInt(potentialCharacters.length()));
					final StringBuilder builder = new StringBuilder(this.getAttempt());
					builder.setCharAt(index, newComponent);
					final String newAttempt = builder.toString();
					final Phase1SolutionDecomposition p1 = new Phase1SolutionDecomposition(this.ctx, newAttempt, this.function);
					final double componentFitness = p1.getComponentialFitness(index);

					if (componentFitness >= fitnessAllowed) {

						barLoweredSufficiently = true;
						this.setAttempt(newAttempt);
					}
					fitnessAllowed *= 0.99;
				}
			}
		}
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
