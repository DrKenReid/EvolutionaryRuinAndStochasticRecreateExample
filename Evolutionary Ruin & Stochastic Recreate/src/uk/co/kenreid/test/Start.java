/*
 * E: Ken@kenreid.co.uk
 * Written by Ken Reid
 */
package uk.co.kenreid.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import uk.co.kenreid.dataobjects.RunStats;
import uk.co.kenreid.dataobjects.TestCombination;
import uk.co.kenreid.function.Function;
import uk.co.kenreid.io.Input;
import uk.co.kenreid.io.Output;

/**
 * The Class Start.
 */
public class Start {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(final String[] args) {
		final Start start = new Start();
		start.beginTests(args);
	}

	/** The cool rates and temperatures. */
	private List<double[]> coolRatesAndTemperatures;

	/** The function. */
	Function function = new Function();

	/** The mutation rates. */
	private List<double[]> mutationRates;

	/** The weightings. */
	private List<double[]> weightings;

	/**
	 * Begin tests.
	 *
	 * @param args
	 *            the args
	 */
	private void beginTests(final String[] args) {
		this.prepUser();
		System.out.println("\nTesting begun.\n");

		// Used for timing length of run.
		final long begin = System.currentTimeMillis();

		// RunStats stores run information per run. So if you have multiple tests
		// prepared with varying parameters, this is useful
		final List<RunStats> runStats = new ArrayList<>();

		System.out.println("Reading parameters...");
		final Input input = new Input();
		input.readParameters();

		this.weightings = input.getWeightings();
		this.mutationRates = input.getMutationRates();
		this.coolRatesAndTemperatures = input.getCoolRatesAndTemperatures();

		System.out.println("Success!");

		// prep tests
		final int i = 0;
		System.out.println("Preparing parameters for testing...");
		final List<TestCombination> allTestParameters = new ArrayList<>();
		for (final double[] coolRateAndTemperature : this.coolRatesAndTemperatures) {
			for (final double[] selectionAndMutationRates : this.mutationRates) {
				for (final double[] scWeightings : this.weightings) {
					final TestCombination tc = new TestCombination(coolRateAndTemperature, selectionAndMutationRates, scWeightings);
					allTestParameters.add(tc);
				}
			}
		}
		System.out.println("Beginning testing.");

		int counter = 0;
		for (final TestCombination testParameters : allTestParameters) {
			final long start = System.nanoTime();
			final double coolingRate = testParameters.getCoolRateAndTemperature()[0];
			final double temperature = testParameters.getCoolRateAndTemperature()[1];
			final Map<String, Double> formattedWeightings = this.formatWeightings(testParameters.getScWeightings());
			final Test test = new Test(temperature, coolingRate, formattedWeightings, testParameters.getSelectionAndMutationRates()[0],
					testParameters.getSelectionAndMutationRates()[1], testParameters.getSelectionAndMutationRates()[2], start);
			test.go();
			final long end = System.nanoTime();
			final RunStats rs = new RunStats(i, temperature, coolingRate, test.getBestSolution(), test.getStart(), end, testParameters.getScWeightings(),
					testParameters.getSelectionAndMutationRates());
			runStats.add(rs);
			counter++;

			final long e = System.nanoTime();

			System.out.println("Took: " + ((e - start) / 1000000000) + " seconds for test " + counter + "/" + allTestParameters.size() + " to complete.");
		}

		System.out.println("Saving output stats");
		final Output output = new Output();
		output.outputStats(runStats);
		System.out.println("Done!\n");

		// Print time taken.
		System.out.println("Finished after " + ((System.currentTimeMillis() - begin) / 1000) + " second(s)");
	}

	/**
	 * Format weightings.
	 *
	 * @param scWeightings
	 *            the sc weightings
	 * @return the map
	 */
	private Map<String, Double> formatWeightings(final double[] scWeightings) {
		final Map<String, Double> weightingsPerConstraint = new HashMap<>();

		weightingsPerConstraint.put("SC1", scWeightings[0]);
		weightingsPerConstraint.put("SC2", scWeightings[1]);
		return weightingsPerConstraint;
	}

	/**
	 * Prep user.
	 */
	private void prepUser() {
		/*
		 * Info message
		 */
		System.out.println("Evolutionary Ruin & Stochastic Recreate example running.");
		this.sleep(500);
		System.out.println("Attempting to work out the following passcode:");
		this.sleep(10);
		System.err.println("1-7-3-4-6-7-3-2-1-4-7-6-Charlie-3-2-7-8-9-7-7-7-6-4-3-Tango-7-3-2-Victor-7-3-1-1-7-8-8-8-7-3-2-4-7-6-7-8-9-7-6-4-3-7-6\n");
		this.sleep(5000);
		System.out.println("Using ER&SR, the processor generates a random String, then modifies it,\ntesting if it is closer or further each iteration");
		this.sleep(3000);
		System.out.println("Our \"Hard Constraint\" will be hard-coded, in the form that the \nString must be the exact length of the above solution.");
		this.sleep(3000);
		System.out.println("\nA single argument specifying the path for the params.xlsx file is expected. \nIf no path is provided, it's assumed here:\n");
		this.sleep(500);
		System.err.println(">" + System.getProperty("user.dir") + "\\data\\input\n");
		this.sleep(3000);
		System.out.println("\n\nReady to begin? y/n");
		final Scanner scanner = new Scanner(System.in);
		final String ready = scanner.nextLine();
		if (!ready.equals("y")) {
			System.exit(0);
		}
		scanner.close();

	}

	/**
	 * Sleep.
	 *
	 * @param ms
	 *            the ms
	 */
	public void sleep(final long ms) {
		try {
			Thread.sleep(ms);
		}
		catch (final InterruptedException e) {
			System.err.println(e);
		}
	}

}
