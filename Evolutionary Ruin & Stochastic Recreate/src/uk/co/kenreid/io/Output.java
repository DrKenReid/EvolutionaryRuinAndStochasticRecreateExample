/*
 * E: Ken@kenreid.co.uk
 * Written by Ken Reid
 */
package uk.co.kenreid.io;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import uk.co.kenreid.dataobjects.Context;
import uk.co.kenreid.dataobjects.RunStats;
import uk.co.kenreid.evolutionaryruinandstochasticrecreate.Phase1SolutionDecomposition;
import uk.co.kenreid.function.Function;

/**
 * The Class Output.
 */
public class Output {

	/** The accepted solutions. */
	private List<String> acceptedSolutions;

	/** The attempt. */
	private String attempt;

	/** The ctx. */
	private Context ctx;

	/** The function. */
	Function function = new Function();

	/** The original attempt. */
	private String originalAttempt;

	/** The overall fitness per iteration. */
	private Map<Integer, Double> overallFitnessPerIteration;

	/** The xls path. */
	String xlsPath = "data/output/";

	/**
	 * Instantiates a new output.
	 */
	public Output() {
		// ONLY for use for final output for OutputStats method.
	}

	/**
	 * Instantiates a new output.
	 *
	 * @param overallFitnessPerIteration
	 *            the overall fitness per iteration
	 * @param acceptedSolutions
	 *            the accepted solutions
	 * @param originalAttempt
	 *            the original attempt
	 * @param attempt
	 *            the attempt
	 * @param ctx
	 *            the ctx
	 */
	public Output(final Map<Integer, Double> overallFitnessPerIteration, final List<String> acceptedSolutions, final String originalAttempt,
			final String attempt, final Context ctx) {

		this.overallFitnessPerIteration = overallFitnessPerIteration;
		this.acceptedSolutions = acceptedSolutions;
		this.originalAttempt = originalAttempt;
		this.attempt = attempt;
		this.ctx = ctx;
		// if folders don't exist to hold excel data, make 'em.
		new File(this.xlsPath).mkdirs();
	}

	/**
	 * Output stats.
	 *
	 * @param runStats
	 *            the run stats
	 */
	public void outputStats(final List<RunStats> runStats) {
		String xlsPath = "data/ERSRoutput/XLSX/";
		new File(xlsPath).mkdirs();
		final long ms = Calendar.getInstance().getTimeInMillis();
		final SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-ss");
		final Date resultdate = new Date(ms);
		final String time = "" + sdf.format(resultdate);
		xlsPath = "data/output/";
		try {
			final String path = xlsPath + "aaaaRunstats" + time + ".xls";
			final FileOutputStream outputStream = new FileOutputStream(path);
			final HSSFWorkbook workbook = new HSSFWorkbook();
			final HSSFSheet worksheet = workbook.createSheet("Results");

			final HSSFRow row1 = worksheet.createRow(0);

			HSSFCell cell = row1.createCell(0);
			cell.setCellValue("Iteration");

			cell = row1.createCell(1);
			cell.setCellValue("Temperature");

			cell = row1.createCell(2);
			cell.setCellValue("Cooling Rate");

			cell = row1.createCell(3);
			cell.setCellValue("SC1");

			cell = row1.createCell(4);
			cell.setCellValue("SC2");

			cell = row1.createCell(5);
			cell.setCellValue("Selection Rate");

			cell = row1.createCell(6);
			cell.setCellValue("Mutation 1 Rate");

			cell = row1.createCell(7);
			cell.setCellValue("Mutation 2 Rate");

			cell = row1.createCell(8);
			cell.setCellValue("Best Fitness");

			cell = row1.createCell(9);
			cell.setCellValue("Runtime");

			this.function.makeRowBold(workbook, row1);

			int rowNumber = 1;
			for (final RunStats rs : runStats) {

				final HSSFRow currentRow = worksheet.createRow(rowNumber);
				int cellNumber = 0;

				cell = currentRow.createCell(cellNumber);
				cell.setCellValue(rs.getIteration());
				cellNumber++;

				cell = currentRow.createCell(cellNumber);
				cell.setCellValue(rs.getTemperature());
				cellNumber++;

				cell = currentRow.createCell(cellNumber);
				cell.setCellValue(rs.getCoolingRate());
				cellNumber++;

				cell = currentRow.createCell(cellNumber);
				cell.setCellValue(rs.getSc1());
				cellNumber++;

				cell = currentRow.createCell(cellNumber);
				cell.setCellValue(rs.getSc2());
				cellNumber++;

				cell = currentRow.createCell(cellNumber);
				cell.setCellValue(rs.getSelectionRate());
				cellNumber++;

				cell = currentRow.createCell(cellNumber);
				cell.setCellValue(rs.getMutationRate1());
				cellNumber++;

				cell = currentRow.createCell(cellNumber);
				cell.setCellValue(rs.getMutationRate2());
				cellNumber++;

				cell = currentRow.createCell(cellNumber);
				cell.setCellValue(rs.getBestSolution());
				cellNumber++;

				cell = currentRow.createCell(cellNumber);
				cell.setCellValue(rs.getEnd() - rs.getStart() + "s");

				rowNumber++;

			}
			rowNumber++;
			for (int i = 0; i < 15; i++) {
				worksheet.autoSizeColumn(i);
			}
			// Save the workbook in .xls file
			workbook.close();
			workbook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		}
		catch (

		final Exception e) {
			System.err.println(e);
			final StackTraceElement[] elements = e.getStackTrace();
			for (int iterator = 1; iterator <= elements.length; iterator++) {
				System.err.println("Class Name:" + elements[iterator - 1].getClassName() + " Method Name:" + elements[iterator - 1].getMethodName()
						+ " Line Number:" + elements[iterator - 1].getLineNumber());
			}
		}
	}

	/**
	 * Output XLS overall fitness of first X iterations.
	 *
	 * @param fileName
	 *            the file name
	 */
	public void outputXLSOverallFitnessOfFirstXIterations(final String fileName) {
		try {
			final String path = this.xlsPath + fileName + ".xls";
			// setup
			final FileOutputStream outputStream = new FileOutputStream(path);
			final HSSFWorkbook workbook = new HSSFWorkbook();
			final HSSFSheet worksheet = workbook.createSheet("Results");

			final HSSFRow row1 = worksheet.createRow(0);

			final HSSFCell cellA1 = row1.createCell(0);
			cellA1.setCellValue("Iteration");

			final HSSFCell cellB1 = row1.createCell(1);
			cellB1.setCellValue("Fitness");

			this.function.makeRowBold(workbook, row1);

			int counter = 0;
			int rowNumber = 1;
			for (final Entry<Integer, Double> e : this.overallFitnessPerIteration.entrySet()) {
				counter++;
				if (counter % 100 != 0) {
					continue;
				}
				int cellNumber = 0;

				final HSSFRow currentRow = worksheet.createRow(rowNumber);

				final HSSFCell iterationCell = currentRow.createCell(cellNumber);
				iterationCell.setCellValue(e.getKey());

				cellNumber++;

				final HSSFCell fitnessCell = currentRow.createCell(cellNumber);
				fitnessCell.setCellValue(e.getValue());

				rowNumber++;

			}
			for (int i = 0; i < 15; i++) {
				worksheet.autoSizeColumn(i);
			}

			// Save the workbook in .xls file
			workbook.write(outputStream);
			workbook.close();
			outputStream.flush();
			outputStream.close();
		}
		catch (final Exception e) {
			this.printErr(e);
		}

	}

	/**
	 * Output XLS xaccepted solutions.
	 *
	 * @param fileName
	 *            the file name
	 */
	public void outputXLSXacceptedSolutions(final String fileName) {
		try {
			final String path = this.xlsPath + fileName + ".xlsx";
			// setup
			final FileOutputStream outputStream = new FileOutputStream(path);
			final Workbook workbook = new XSSFWorkbook();
			final Sheet worksheet = workbook.createSheet("Results");

			final Row row1 = worksheet.createRow(0);

			final Cell cellA1 = row1.createCell(0);
			cellA1.setCellValue("Solutions");

			int rowNumber = 1;

			for (final String solution : this.acceptedSolutions) {
				final Row row = worksheet.createRow(rowNumber);

				final Cell cell1 = row.createCell(0);
				cell1.setCellValue(solution);

				rowNumber++;
			}

			this.function.makeRowBold(workbook, row1);

			for (int i = 0; i < 15; i++) {
				worksheet.autoSizeColumn(i);
			}
			workbook.write(outputStream);
			workbook.close();
			outputStream.flush();
			outputStream.close();
		}
		catch (final Exception e) {
			this.printErr(e);
		}

	}

	/**
	 * Output XLSX fitness of best solution compared to original.
	 *
	 * @param fileName
	 *            the file name
	 */
	public void outputXLSXFitnessOfBestSolutionComparedToOriginal(final String fileName) {
		try {
			final String path = this.xlsPath + fileName + ".xlsx";
			// setup
			final FileOutputStream outputStream = new FileOutputStream(path);
			final Workbook workbook = new XSSFWorkbook();
			final Sheet worksheet = workbook.createSheet("Results");

			final Row row1 = worksheet.createRow(0);

			final Cell cellA1 = row1.createCell(0);
			cellA1.setCellValue("Constraint");

			final Cell cellA2 = row1.createCell(1);
			cellA2.setCellValue("Weighted Final Fitness");

			final Cell cellA3 = row1.createCell(2);
			cellA3.setCellValue("Weighted Original value");

			final Cell cellA4 = row1.createCell(3);
			cellA4.setCellValue("Weighting");

			final Cell cellA5 = row1.createCell(4);
			cellA5.setCellValue("Unweighted Final Fitness");

			final Cell cellA6 = row1.createCell(5);
			cellA6.setCellValue("Unweighted Original value");

			int rowNumber = 1;

			final Phase1SolutionDecomposition fitness = new Phase1SolutionDecomposition(this.ctx, this.attempt, this.function);
			fitness.decompose();
			final Map<String, Double> bestSolutionWeightedFitness = fitness.getWeightedFitnessMapping();

			final Phase1SolutionDecomposition originalFitness = new Phase1SolutionDecomposition(this.ctx, this.originalAttempt, this.function);
			originalFitness.decompose();
			final Map<String, Double> originalSolutionFitnessAndConstraints = originalFitness.getWeightedFitnessMapping();

			for (final Entry<String, Double> weightedConstraintAndFitness : bestSolutionWeightedFitness.entrySet()) {
				final Row row = worksheet.createRow(rowNumber);

				final Cell cell1 = row.createCell(0);
				cell1.setCellValue(weightedConstraintAndFitness.getKey());

				final Cell cell2 = row.createCell(1);
				cell2.setCellValue(weightedConstraintAndFitness.getValue());

				final Cell cell3 = row.createCell(2);
				cell3.setCellValue(originalSolutionFitnessAndConstraints.get(weightedConstraintAndFitness.getKey()));

				final Cell cell4 = row.createCell(3);
				cell4.setCellValue(this.ctx.getWeightingsPerConstraint().get(weightedConstraintAndFitness.getKey()));

				final Cell cell5 = row.createCell(4);
				cell5.setCellValue(originalFitness.getUnweightedFitnessMapping().get(weightedConstraintAndFitness.getKey()));

				final Cell cell6 = row.createCell(5);
				cell6.setCellValue(fitness.getUnweightedFitnessMapping().get(weightedConstraintAndFitness.getKey()));

				rowNumber++;
			}

			final Row row = worksheet.createRow(rowNumber);

			final Cell cellB = row.createCell(1);
			cellB.setCellValue(fitness.getOverallWeightedFitness());

			final Cell cellC = row.createCell(2);
			cellC.setCellValue(originalFitness.getOverallWeightedFitness());

			final Cell cellD = row.createCell(3);
			cellD.setCellValue(1);

			final Cell cellE = row.createCell(4);
			double average = 0;
			for (final Map.Entry<String, Double> e : fitness.getUnweightedFitnessMapping().entrySet()) {
				average += e.getValue();
			}
			average = average / fitness.getUnweightedFitnessMapping().size();
			cellE.setCellValue(average);

			final Cell c = row.createCell(5);
			average = 0;
			for (final Map.Entry<String, Double> e : originalFitness.getUnweightedFitnessMapping().entrySet()) {
				average += e.getValue();
			}
			average = average / originalFitness.getUnweightedFitnessMapping().size();
			c.setCellValue(average);

			this.function.makeRowBold(workbook, row1);
			this.function.makeRowBold(workbook, row);

			for (int i = 0; i < 15; i++) {
				worksheet.autoSizeColumn(i);
			}
			// Save the workbook in .xls file
			workbook.write(outputStream);
			workbook.close();
			outputStream.flush();
			outputStream.close();
		}
		catch (final Exception e) {
			this.printErr(e);
		}
	}

	/**
	 * Prints the err.
	 *
	 * @param e
	 *            the e
	 */
	private void printErr(final Exception e) {
		System.err.println(e);
		final StackTraceElement[] elements = e.getStackTrace();
		for (int iterator = 1; iterator <= elements.length; iterator++) {
			System.err.println("Class Name:" + elements[iterator - 1].getClassName() + " Method Name:" + elements[iterator - 1].getMethodName()
					+ " Line Number:" + elements[iterator - 1].getLineNumber());
		}
	}

}
