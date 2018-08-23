/*
 * E: Ken@kenreid.co.uk
 * Written by Ken Reid
 */
package uk.co.kenreid.function;

import java.util.List;
import java.util.Random;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * The Class Function.
 */
public class Function {

	/** The random. */
	private Random random = new Random();

	/**
	 * Gets the random.
	 *
	 * @return the random
	 */
	public Random getRandom() {
		return this.random;
	}

	/**
	 * Make row bold.
	 *
	 * @param wb
	 *            the wb
	 * @param row
	 *            the row
	 */
	public void makeRowBold(final Workbook wb, final Row row) {
		final CellStyle style = wb.createCellStyle();// Create style
		final Font font = wb.createFont();// Create font
		font.setBold(true);// Make font bold
		style.setFont(font);// set it to bold

		for (int i = 0; i < row.getLastCellNum(); i++) {// For each cell in the
			// row
			if (!(row.getCell(i) == null)) {
				row.getCell(i).setCellStyle(style);// Set the style
			}
		}
	}

	/**
	 * Sets the random.
	 *
	 * @param random
	 *            the new random
	 */
	public void setRandom(final Random random) {
		this.random = random;
	}

	/**
	 * Sum array of ints.
	 *
	 * @param ints
	 *            the ints
	 * @return the int
	 */
	public int sumArrayOfInts(final int[] ints) {
		int ans = 0;
		for (final int i : ints) {
			ans += i;
		}
		return ans;
	}

	/**
	 * Sum array of ints to integer.
	 *
	 * @param ints
	 *            the ints
	 * @return the integer
	 */
	public Integer sumArrayOfIntsToInteger(final int[] ints) {
		int ans = 0;
		for (final int i : ints) {
			ans += i;
		}
		return ans;
	}

	/**
	 * Sum list of doubles.
	 *
	 * @param doubles
	 *            the doubles
	 * @return the double
	 */
	public Double sumListOfDoubles(final List<Double> doubles) {
		Double ans = 0.0;
		for (final Double d : doubles) {
			ans += d;
		}
		return ans;
	}

}
