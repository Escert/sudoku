package com.github.escert.sudoku.api;

import com.github.escert.sudoku.model.Cell;
import com.github.escert.sudoku.model.Sudoku;

import java.util.stream.Collector;

public class SudokuDto {

	private final String values;
	private final int size;

	private SudokuDto(String values, int size) {
		this.values = values;
		this.size = size;
	}

	public static SudokuDto from(Sudoku sudoku) {
		String values = sudoku.getCells()
				.stream()
				.map(Cell::getValue)
				.collect(Collector.of(StringBuilder::new, StringBuilder::append, StringBuilder::append, StringBuilder::toString));

		return new SudokuDto(values, sudoku.getSize());
	}

	public String getValues() {
		return values;
	}

	public int getSize() {
		return size;
	}
}
