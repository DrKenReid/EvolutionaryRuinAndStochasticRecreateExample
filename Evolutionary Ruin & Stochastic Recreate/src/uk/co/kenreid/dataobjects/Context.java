/*
 * E: Ken@kenreid.co.uk
 * Written by Ken Reid
 */
package uk.co.kenreid.dataobjects;

import java.util.HashMap;
import java.util.Map;

import uk.co.kenreid.evolutionaryruinandstochasticrecreate.Phase2EvolutionaryRuin.Operator;

/**
 * The Class Context.
 */
public class Context {

	/** The cooling rate. */
	private double coolingRate;

	/** The original temperature. */
	private int originalTemperature;

	/** The problem. */
	private final String problem = "1-7-3-4-6-7-3-2-1-4-7-6-Charlie-3-2-7-8-9-7-7-7-6-4-3-Tango-7-3-2-Victor-7-3-1-1-7-8-8-8-7-3-2-4-7-6-7-8-9-7-6-4-3-7-6";

	/** The ruinp 1 rate. */
	private double ruinp1Rate;

	/** The ruinp 2 rate. */
	private double ruinp2Rate;

	/** The selection operator. */
	private Operator selectionOperator;

	/** The selection rate. */
	private double selectionRate;

	/** The stage one operator. */
	private Operator stageOneOperator;

	/** The stage two operator. */
	private Operator stageTwoOperator;

	/** The temperature. */
	private double temperature;

	/** The weightings per constraint. */
	private Map<String, Double> weightingsPerConstraint = new HashMap<>();

	/**
	 * Gets the cooling rate.
	 *
	 * @return the cooling rate
	 */
	public double getCoolingRate() {
		// TODO Auto-generated method stub
		return this.coolingRate;
	}

	/**
	 * Gets the original temperature.
	 *
	 * @return the original temperature
	 */
	public int getOriginalTemperature() {
		return this.originalTemperature;
	}

	/**
	 * Gets the problem.
	 *
	 * @return the problem
	 */
	public String getProblem() {
		return this.problem;
	}

	/**
	 * Gets the ruinp 1 rate.
	 *
	 * @return the ruinp 1 rate
	 */
	public double getRuinp1Rate() {
		return this.ruinp1Rate;
	}

	/**
	 * Gets the ruinp 2 rate.
	 *
	 * @return the ruinp 2 rate
	 */
	public double getRuinp2Rate() {
		return this.ruinp2Rate;
	}

	/**
	 * Gets the selection operator.
	 *
	 * @return the selection operator
	 */
	public Operator getSelectionOperator() {
		return this.selectionOperator;
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
	 * Gets the stage one operator.
	 *
	 * @return the stage one operator
	 */
	public Operator getStageOneOperator() {
		return this.stageOneOperator;
	}

	/**
	 * Gets the stage two operator.
	 *
	 * @return the stage two operator
	 */
	public Operator getStageTwoOperator() {
		return this.stageTwoOperator;
	}

	/**
	 * Gets the temperature.
	 *
	 * @return the temperature
	 */
	public double getTemperature() {
		// TODO Auto-generated method stub
		return this.temperature;
	}

	/**
	 * Gets the weightings per constraint.
	 *
	 * @return the weightings per constraint
	 */
	public Map<String, Double> getWeightingsPerConstraint() {
		return this.weightingsPerConstraint;
	}

	/**
	 * Sets the cooling rate.
	 *
	 * @param coolingRate
	 *            the new cooling rate
	 */
	public void setCoolingRate(final double coolingRate) {
		this.coolingRate = coolingRate;
	}

	/**
	 * Sets the original temperature.
	 *
	 * @param i
	 *            the new original temperature
	 */
	public void setOriginalTemperature(final int i) {
		this.setTemperature(i);
		this.originalTemperature = i;

	}

	/**
	 * Sets the ruinp 1 rate.
	 *
	 * @param ruinp1Rate
	 *            the new ruinp 1 rate
	 */
	public void setRuinp1Rate(final double ruinp1Rate) {
		this.ruinp1Rate = ruinp1Rate;
	}

	/**
	 * Sets the ruinp 2 rate.
	 *
	 * @param ruinp2Rate
	 *            the new ruinp 2 rate
	 */
	public void setRuinp2Rate(final double ruinp2Rate) {
		this.ruinp2Rate = ruinp2Rate;
	}

	/**
	 * Sets the selection operator.
	 *
	 * @param selectionOperator
	 *            the new selection operator
	 */
	public void setSelectionOperator(final Operator selectionOperator) {
		this.selectionOperator = selectionOperator;
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

	/**
	 * Sets the stage one operator.
	 *
	 * @param stageOneOperator
	 *            the new stage one operator
	 */
	public void setStageOneOperator(final Operator stageOneOperator) {
		this.stageOneOperator = stageOneOperator;
	}

	/**
	 * Sets the stage two operator.
	 *
	 * @param stageTwoOperator
	 *            the new stage two operator
	 */
	public void setStageTwoOperator(final Operator stageTwoOperator) {
		this.stageTwoOperator = stageTwoOperator;
	}

	/**
	 * Sets the temperature.
	 *
	 * @param temperature
	 *            the new temperature
	 */
	public void setTemperature(final double temperature) {
		this.temperature = temperature;
	}

	/**
	 * Sets the weightings per constraint.
	 *
	 * @param weightingsPerConstraint
	 *            the weightings per constraint
	 */
	public void setWeightingsPerConstraint(final Map<String, Double> weightingsPerConstraint) {
		this.weightingsPerConstraint = weightingsPerConstraint;
	}

}
