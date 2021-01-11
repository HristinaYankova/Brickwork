package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import exceptions.InvalidLengthLineException;

public class Main {

	public static void main(String[] args) {
		// read and validate matrix dimensions
		String[] matrixDimensions = new String[] {};
		boolean isValidDimensions = false;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		while (!isValidDimensions) {
			matrixDimensions = readMatrixDimensions(input);
			isValidDimensions = validateDimensions(matrixDimensions);
		}
		// Populate and validate matrix values.
		int[][] layerOne = new int[][] {};
		boolean isValidValues = false;

		while (!isValidValues) {
			try {
				layerOne = populateLayerOne(matrixDimensions[0], matrixDimensions[1], input);
				isValidValues = validatePopulation(layerOne);
			} catch (InvalidLengthLineException e) {
				System.out.println(e.getMessage());
			} catch (NumberFormatException e) {
				System.out.println("Please enter valid values: must be valid number");
			}
		}

		// Create and print second layer of bricks
		int[][] layerTwo = creatingLayerTwo(layerOne);
		printLayerTwo(layerTwo);

	}

	/**
	 * Return the input values from the console for dimension of layer one.
	 * 
	 * @return Array with matrix dimensions.
	 */
	private static String[] readMatrixDimensions(BufferedReader input) {
		String[] dimension = new String[] {};
		// Scanner for console input stream.
		try {
			System.out.println("Enter rows and colums of the layer: \n");
			String inputDimension = input.readLine(); // Read line from console in String.
			// Split the value by space and remove space from begin and end of the line.
			dimension = inputDimension.trim().split("\\s+");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dimension;

	}

	/**
	 * Validate the input values for layer one's dimension.
	 * 
	 * @param rowsStr    The rows in String format.
	 * @param columnsStr The columns in String format.
	 * @return If the values are valid or not.
	 */
	private static boolean validateDimensions(String[] matrixDimensions) {
		if (matrixDimensions.length != 2) {
			throw new InvalidLengthLineException("Invalid array dimension length: must be 2 numbers.");
		}
		try {
			int rows = Integer.parseInt(matrixDimensions[0]);
			int columns = Integer.parseInt(matrixDimensions[1]);

			if (rows == 0 && columns == 0) {
				System.out.println("Please enter valid values: must be grater than 0");
				return false;
			}
			if (rows % 2 != 0 || columns % 2 != 0) {
				System.out.println("Please enter valid values: must be even numbers");
				return false;
			}
			if (rows >= 100 && columns >= 100) {
				System.out.println("Please enter valid values: must be less than 100");
				return false;
			}
		} catch (NumberFormatException e) {
			System.out.println("Please enter valid values: must be valid number");
			return false;
		}
		return true;
	}

	/**
	 * Return matrix that describes first layer of the bricks.
	 * 
	 * @param rowsStr    The rows in String format.
	 * @param columnsStr The columns in String format.
	 * @return Bricks layout in the first layer.
	 */

	private static int[][] populateLayerOne(String rowsStr, String columnsStr, BufferedReader input) {
		int rows = Integer.parseInt(rowsStr);
		int columns = Integer.parseInt(columnsStr);
		int[][] bricksArray = new int[rows][columns];
		System.out.println("Please enter the first layer: \n");
		try {
			for (int i = 0; i < bricksArray.length; i++) {
				String[] line = input.readLine().trim().split("\\s+");
				if (line.length > bricksArray[1].length || line.length == 1) {
					throw new InvalidLengthLineException("Invalid column's number");
				}
				for (int j = 0; j < bricksArray[0].length; j++) {
					bricksArray[i][j] = Integer.parseInt(line[j]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bricksArray;
	}

	/**
	 * Validate input values for layer one.
	 * 
	 * @param layerOne Bricks layout in the first layer.
	 * @return If the matrix meets the requirements or not.
	 */
	private static boolean validatePopulation(int[][] layerOne) {
		// Create a list of every rows from the matrix.
		List<Integer> listOfValues = new ArrayList<Integer>();
		for (int[] line : layerOne) {
			for (int number : line) {
				listOfValues.add(number);
			}
		}
		// Validate if the valid numbers exist and the it's frequency.
		for (int i = 1; i <= ((layerOne.length * layerOne[0].length) / 2); i++) {
			if (!listOfValues.contains(i) || (Collections.frequency(listOfValues, i) != 2)) {
				System.out.println(
						"Please enter valid values: " + "the nubmers must be from 1 to the total number of the bricks. "
								+ "Each brick must be mark with two equal numbers");
				return false;
			}
		}

		// Validate number's position.
		if (!(layerOne.length == 2 && layerOne[1].length == 2)) {

			for (int i = 0; i < layerOne.length; i++) {
				for (int j = 0; j < layerOne[0].length; j++) {
					if (i == 0 && j == 0) {
						if (!(layerOne[i][j] == layerOne[i][j + 1] || layerOne[i][j] == layerOne[i + 1][j])) {
							System.out.println("Please enter valid values: "
									+ "the nubmers must be from 1 to the total number of the bricks. "
									+ "Each brick must be mark with two equal numbers "
									+ "and the bricks cannot span 3 rows/collumns");
							return false;
						}
					} else if (i == layerOne.length - 1 && j == 0) {
						if (!(layerOne[i][j] == layerOne[i][j + 1] || layerOne[i][j] == layerOne[i - 1][j])) {
							System.out.println("Please enter valid values: "
									+ "the nubmers must be from 1 to the total number of the bricks. "
									+ "Each brick must be mark with two equal numbers "
									+ "and the bricks cannot span 3 rows/collumns");
							return false;
						}
					} else if (i == 0 && j == layerOne[0].length - 1) {
						if (!(layerOne[i][j] == layerOne[i][j - 1] || layerOne[i][j] == layerOne[i + 1][j])) {
							System.out.println("Please enter valid values: "
									+ "the nubmers must be from 1 to the total number of the bricks. "
									+ "Each brick must be mark with two equal numbers "
									+ "and the bricks cannot span 3 rows/collumns");
							return false;
						}
					} else if (i == layerOne.length - 1 && j == layerOne[0].length - 1) {
						if (!(layerOne[i][j] == layerOne[i][j - 1] || layerOne[i][j] == layerOne[i - 1][j])) {

							System.out.println("Please enter valid values: "
									+ "the nubmers must be from 1 to the total number of the bricks. "
									+ "Each brick must be mark with two equal numbers "
									+ "and the bricks cannot span 3 rows/collumns");
							return false;
						}
					} else if (i == 0 && j != 0 && j != layerOne[0].length - 1) {
						if (!(layerOne[i][j] == layerOne[i + 1][j] || layerOne[i][j] == layerOne[i][j - 1]
								|| layerOne[i][j] == layerOne[i][j + 1])) {
							System.out.println("Please enter valid values: "
									+ "the nubmers must be from 1 to the total number of the bricks. "
									+ "Each brick must be mark with two equal numbers "
									+ "and the bricks cannot span 3 rows/collumns");
							return false;
						}
					} else if (i != 0 && i != layerOne.length - 1 && j == 0) {
						if (!(layerOne[i][j] == layerOne[i - 1][j] || layerOne[i][j] == layerOne[i + 1][j]
								|| layerOne[i][j] == layerOne[i][j + 1])) {
							System.out.println("Please enter valid values: "
									+ "the nubmers must be from 1 to the total number of the bricks. "
									+ "Each brick must be mark with two equal numbers "
									+ "and the bricks cannot span 3 rows/collumns");
							return false;
						}
					} else if (i == layerOne.length - 1 && j != 0 && j != layerOne[0].length - 1) {
						if (!(layerOne[i][j] == layerOne[i - 1][j] || layerOne[i][j] == layerOne[i][j + 1]
								|| layerOne[i][j] == layerOne[i][j - 1])) {
							System.out.println("Please enter valid values: "
									+ "the nubmers must be from 1 to the total number of the bricks. "
									+ "Each brick must be mark with two equal numbers "
									+ "and the bricks cannot span 3 rows/collumns");
							return false;
						}
					} else if (i != layerOne.length - 1 && i != 0 && j == layerOne[0].length - 1) {
						if (!(layerOne[i][j] == layerOne[i - 1][j] || layerOne[i][j] == layerOne[i + 1][j]
								|| layerOne[i][j] == layerOne[i][j - 1])) {
							System.out.println("Please enter valid values: "
									+ "the nubmers must be from 1 to the total number of the bricks. "
									+ "Each brick must be mark with two equal numbers "
									+ "and the bricks cannot span 3 rows/collumns");
							return false;
						}
					} else {
						if (!(layerOne[i][j] == layerOne[i - 1][j] || layerOne[i][j] == layerOne[i + 1][j]
								|| layerOne[i][j] == layerOne[i][j - 1] || layerOne[i][j] == layerOne[i][j + 1])) {
							System.out.println("Please enter valid values: "
									+ "the nubmers must be from 1 to the total number of the bricks. "
									+ "Each brick must be mark with two equal numbers "
									+ "and the bricks cannot span 3 rows/collumns");
							return false;
						}
					}
				}

			}
			return true;
		}
		return true;
	}

	/**
	 * Return second layer of the bricks.
	 * 
	 * @param bricksArray Bricks layout in the first layer.
	 * @return Bricks layout in the second layer.
	 */
	public static int[][] creatingLayerTwo(int[][] bricksArray) {

		// Create transposed matrix.
		int[][] bricksArrayTransp = new int[bricksArray[0].length][bricksArray.length];
		for (int i = 0; i < bricksArrayTransp.length; i++) {
			for (int j = 0; j < bricksArrayTransp[0].length; j++) {
				bricksArrayTransp[i][j] = bricksArray[j][i];
			}
		}
		// Create transposed matrix in reverse order.
		int[][] bricksArrayTwo = new int[bricksArray.length][bricksArray[0].length];
		for (int i = 0; i < bricksArrayTwo.length; i++) {
			for (int j = 0; j < bricksArrayTwo[0].length; j++) {
				bricksArrayTwo[i][j] = bricksArrayTransp[(bricksArrayTransp.length - 1)
						- j][(bricksArrayTransp[0].length - 1) - i];
			}
		}
		return bricksArrayTwo;
	}

	/**
	 * Printing second layer.
	 * 
	 * @param layerTwo Bricks layout in the second layer.
	 */
	private static void printLayerTwo(int[][] layerTwo) {
		int rows = layerTwo.length;
		int col = layerTwo[0].length;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < col; j++) {
				if (i == 0) {
					System.out.print("--");
				} else {
					if (layerTwo[i][j] == layerTwo[i - 1][j]) {
						System.out.print("- ");
					} else {
						System.out.print("--");
					}
				}
			}
			System.out.println();
			for (int j = 0; j < col; j++) {
				if (j < col - 1) {
					if (layerTwo[i][j] == layerTwo[i][j + 1]) {
						if (j == 0) {
							System.out.print("-" + layerTwo[i][j] + " ");
						} else
							System.out.print(layerTwo[i][j] + " ");
					} else {
						if (j == 0) {
							System.out.print("-" + layerTwo[i][j] + "-");
						} else
							System.out.print(layerTwo[i][j] + "-");
					}
				} else {
					System.out.print(layerTwo[i][j] + "-");
				}
			}
			System.out.println();
		}
		System.out.println("--".repeat(col));
	}
}
