/*
 * E: Ken@kenreid.co.uk
 * Written by Ken Reid
 */
package uk.co.kenreid.io;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * The Class Input.
 */
public class Input {

	/** The cool rates and temperatures. */
	private List<double[]> coolRatesAndTemperatures;

	/** The input path. */
	String inputPath = "data/input/";

	/** The mutation rates. */
	private List<double[]> mutationRates;

	/** The weightings. */
	private List<double[]> weightings;

	/**
	 * Gets the cool rates and temperatures.
	 *
	 * @return the cool rates and temperatures
	 */
	public List<double[]> getCoolRatesAndTemperatures() {
		return this.coolRatesAndTemperatures;
	}

	/**
	 * Gets the input path.
	 *
	 * @return the input path
	 */
	public String getInputPath() {
		return this.inputPath;
	}

	/**
	 * Gets the mutation rates.
	 *
	 * @return the mutation rates
	 */
	public List<double[]> getMutationRates() {
		return this.mutationRates;
	}

	/**
	 * Gets the weightings.
	 *
	 * @return the weightings
	 */
	public List<double[]> getWeightings() {
		return this.weightings;
	}

	/**
	 * Read parameters.
	 */
	public void readParameters() {
		try {
			final File file = new File("data/input/params.xlsx");
			final FileInputStream fis = new FileInputStream(file);
			final XSSFWorkbook wb = new XSSFWorkbook(fis);

			/*
			 * Read in constraint weightings
			 */
			XSSFSheet sheet = wb.getSheetAt(0);

			int numberOfRows = sheet.getPhysicalNumberOfRows();
			final List<double[]> weightings = new ArrayList<>();

			for (int i = 1; i < numberOfRows; i++) {
				final double[] weightingRow = new double[4];
				final XSSFRow row = sheet.getRow(i);
				XSSFCell cell = row.getCell(0);
				weightingRow[0] = cell.getNumericCellValue();
				cell = row.getCell(1);
				weightingRow[1] = cell.getNumericCellValue();
				weightings.add(weightingRow);
			}
			this.weightings = weightings;

			/*
			 * Read in selection and mutation rates
			 */
			sheet = wb.getSheetAt(1);

			numberOfRows = sheet.getPhysicalNumberOfRows();
			final List<double[]> mutationRates = new ArrayList<>();

			for (int i = 1; i < numberOfRows; i++) {
				final double[] mutationRow = new double[3];
				final XSSFRow row = sheet.getRow(i);
				XSSFCell cell = row.getCell(0);
				mutationRow[0] = cell.getNumericCellValue();
				cell = row.getCell(1);
				mutationRow[1] = cell.getNumericCellValue();
				cell = row.getCell(2);
				mutationRow[2] = cell.getNumericCellValue();
				mutationRates.add(mutationRow);
			}
			this.mutationRates = mutationRates;

			/*
			 * Read in cooling rates and temperatures
			 */
			sheet = wb.getSheetAt(2);

			numberOfRows = sheet.getPhysicalNumberOfRows();
			final List<double[]> coolRatesAndTemperatures = new ArrayList<>();

			for (int i = 1; i < numberOfRows; i++) {
				final double[] crAndTemp = new double[2];
				final XSSFRow row = sheet.getRow(i);
				XSSFCell cell = row.getCell(0);
				crAndTemp[0] = cell.getNumericCellValue();
				cell = row.getCell(1);
				crAndTemp[1] = cell.getNumericCellValue();
				coolRatesAndTemperatures.add(crAndTemp);
			}
			this.coolRatesAndTemperatures = coolRatesAndTemperatures;
			wb.close();
			fis.close();
		}
		catch (final Exception e) {
			System.err.println(e);
		}
	}

	/**
	 * Sets the cool rates and temperatures.
	 *
	 * @param coolRatesAndTemperatures
	 *            the new cool rates and temperatures
	 */
	public void setCoolRatesAndTemperatures(final List<double[]> coolRatesAndTemperatures) {
		this.coolRatesAndTemperatures = coolRatesAndTemperatures;
	}

	/**
	 * Sets the input path.
	 *
	 * @param inputPath
	 *            the new input path
	 */
	public void setInputPath(final String inputPath) {
		this.inputPath = inputPath;
	}

	/**
	 * Sets the mutation rates.
	 *
	 * @param mutationRates
	 *            the new mutation rates
	 */
	public void setMutationRates(final List<double[]> mutationRates) {
		this.mutationRates = mutationRates;
	}

	/**
	 * Sets the weightings.
	 *
	 * @param weightings
	 *            the new weightings
	 */
	public void setWeightings(final List<double[]> weightings) {
		this.weightings = weightings;
	}

}
