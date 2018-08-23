/*
 * E: Ken@kenreid.co.uk
 * Written by Ken Reid
 */
package uk.co.kenreid.dataobjects;

/**
 * The Class TestCombination.
 */
public class TestCombination {

	/** The cool rate and temperature. */
	private final double[] coolRateAndTemperature;

	/** The sc weightings. */
	private final double[] scWeightings;

	/** The selection and mutation rates. */
	private final double[] selectionAndMutationRates;

	/**
	 * Instantiates a new test combination.
	 *
	 * @param coolRateAndTemperature
	 *            the cool rate and temperature
	 * @param selectionAndMutationRates
	 *            the selection and mutation rates
	 * @param scWeightings
	 *            the sc weightings
	 */
	public TestCombination(final double[] coolRateAndTemperature, final double[] selectionAndMutationRates, final double[] scWeightings) {
		this.coolRateAndTemperature = coolRateAndTemperature;
		this.selectionAndMutationRates = selectionAndMutationRates;
		this.scWeightings = scWeightings;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gets the cool rate and temperature.
	 *
	 * @return the cool rate and temperature
	 */
	public double[] getCoolRateAndTemperature() {
		return this.coolRateAndTemperature;
	}

	/**
	 * Gets the sc weightings.
	 *
	 * @return the sc weightings
	 */
	public double[] getScWeightings() {
		return this.scWeightings;
	}

	/**
	 * Gets the selection and mutation rates.
	 *
	 * @return the selection and mutation rates
	 */
	public double[] getSelectionAndMutationRates() {
		return this.selectionAndMutationRates;
	}

}
