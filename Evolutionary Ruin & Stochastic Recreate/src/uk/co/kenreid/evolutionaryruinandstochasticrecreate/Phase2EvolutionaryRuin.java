/*
 * E: Ken@kenreid.co.uk
 * Written by Ken Reid
 */
package uk.co.kenreid.evolutionaryruinandstochasticrecreate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import uk.co.kenreid.dataobjects.Context;
import uk.co.kenreid.function.Function;

/**
 * The Class Phase2EvolutionaryRuin.
 */
public class Phase2EvolutionaryRuin {

	/**
	 * The Enum Operator.
	 */
	public enum Operator {

	/** The addition. */
	ADDITION("+") {
		@Override
		public double apply(final double x1, final double x2) {
			return x1 + x2;
		}
	},

	/** The divide. */
	DIVIDE("/") {
		@Override
		public double apply(final double x1, final double x2) {
			return x1 / x2;
		}
	},

	/** The multiply. */
	MULTIPLY("*") {
		@Override
		public double apply(final double x1, final double x2) {
			return x1 * x2;
		}
	},

	/** The subtraction. */
	SUBTRACTION("-") {
		@Override
		public double apply(final double x1, final double x2) {
			return x1 - x2;
		}
	};

		// You'd include other operators too...

		/** The text. */
		private final String text;

		/**
		 * Instantiates a new operator.
		 *
		 * @param text
		 *            the text
		 */
		private Operator(final String text) {
			this.text = text;
		}

		/**
		 * Apply.
		 *
		 * @param x1
		 *            the x 1
		 * @param x2
		 *            the x 2
		 * @return the double
		 */
		public abstract double apply(double x1, double x2);

		/*
		 * (non-Javadoc)
		 *
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return this.text;
		}
	}

	/** The attempt. */
	private String attempt;

	/** The ctx. */
	private final Context ctx;

	/** The function. */
	private final Function function;

	/** The removed indexes. */
	private ArrayList<Integer> removedIndexes;

	/** The s D. */
	private final Phase1SolutionDecomposition sD;

	/** The temperature. */
	final private double temperature;

	/**
	 * Instantiates a new phase 2 evolutionary ruin.
	 *
	 * @param sD
	 *            the s D
	 * @param temperature
	 *            the temperature
	 * @param ctx
	 *            the ctx
	 * @param attempt
	 *            the attempt
	 * @param function
	 *            the function
	 */
	public Phase2EvolutionaryRuin(final Phase1SolutionDecomposition sD, final double temperature, final Context ctx, final String attempt,
			final Function function) {
		this.sD = sD;
		this.temperature = temperature;
		this.ctx = ctx;
		this.setAttempt(attempt);
		this.function = function;
		this.selectionPhase(ctx.getSelectionRate());
		this.ruinPhase(ctx.getRuinp1Rate(), ctx.getStageOneOperator(), ctx.getRuinp2Rate(), ctx.getStageTwoOperator());
	}

	/**
	 * Calculate.
	 *
	 * @param op
	 *            the op
	 * @param x1
	 *            the x 1
	 * @param x2
	 *            the x 2
	 * @return the int
	 */
	public int calculate(final Operator op, final double x1, final double x2) {
		return (int) op.apply(x1, x2);
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
	 * Gets the temperature.
	 *
	 * @return the temperature
	 */
	public double getTemperature() {
		return this.temperature;
	}

	/**
	 * Ruin phase.
	 *
	 * @param stageOneRuinRate
	 *            the stage one ruin rate
	 * @param stageOneOperator
	 *            the stage one operator
	 * @param stageTwoRuinRate
	 *            the stage two ruin rate
	 * @param stageTwoOperator
	 *            the stage two operator
	 */
	public void ruinPhase(final double stageOneRuinRate, final Operator stageOneOperator, final double stageTwoRuinRate, final Operator stageTwoOperator) {
		if (stageOneRuinRate == 0 && stageTwoRuinRate == 0) {
			return;
		}
		// No point in ruining already selected bits. Grab all potential indexes.
		final List<Integer> potentialRuinIndexes = new ArrayList<>();
		for (int i = 0; i < this.getAttempt().length(); i++) {
			if (this.removedIndexes != null) {
				if (!this.removedIndexes.contains(i)) {
					potentialRuinIndexes.add(i);
				}
			}
			else {
				potentialRuinIndexes.add(i);
			}
		}
		final List<Integer> toBeRuined = new ArrayList<>();

		// Ruin phase 1
		if (stageOneRuinRate != 0) {
			for (final int potentialRuinIndex : potentialRuinIndexes) {
				final int randomNum = this.function.getRandom().nextInt(this.calculate(stageOneOperator, this.ctx.getOriginalTemperature(), stageOneRuinRate));
				if (randomNum < this.getTemperature()) {
					toBeRuined.add(potentialRuinIndex);
				}
			}
		}
		potentialRuinIndexes.removeAll(toBeRuined);

		// Ruin phase 2
		if (stageTwoRuinRate != 0) {
			final int randomNumber = this.function.getRandom().nextInt(this.calculate(stageTwoOperator, this.ctx.getOriginalTemperature(), stageTwoRuinRate));
			if (randomNumber < this.temperature) {
				toBeRuined.addAll(potentialRuinIndexes);
			}
		}

		// actually ruin. use the char '%' to ruin, since the original problem doesn't
		// use this char, nor does the initial string generator, nor the stochastic
		// recreate.
		for (final int ruinMe : toBeRuined) {
			final StringBuilder builder = new StringBuilder(this.getAttempt());
			builder.setCharAt(ruinMe, '%');
			this.setAttempt(builder.toString());
		}
	}

	/**
	 * Selection phase.
	 *
	 * @param selectionRate
	 *            the selection rate
	 */
	public void selectionPhase(double selectionRate) {
		if (selectionRate == 0) {
			return;
		}
		this.removedIndexes = new ArrayList<>();
		selectionRate = selectionRate * this.function.getRandom().nextDouble();
		for (final Entry<Integer, Double> e : this.sD.getSc1FitnessPerComponent().entrySet()) {

			final int index = e.getKey();
			final double fitness = e.getValue();
			if (fitness < selectionRate) {
				this.removedIndexes.add(index);
			}
		}
		for (final int removeMe : this.removedIndexes) {
			final StringBuilder builder = new StringBuilder(this.getAttempt());
			builder.setCharAt(removeMe, '%');
			this.setAttempt(builder.toString());
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
