package com.github.escert.sudoku;

public class SudokuTestData {

	public static final String VALID_SUDOKU = normalizeInput("""
			000000000
			000000000
			000000000
			000000000
			000000000
			000000000
			000000000
			000000000
			000000000
			""");

	public static String normalizeInput(String input) {
		return input.replaceAll("\r|\n", "");
	}
}
