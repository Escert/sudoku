package com.github.escert.sudoku.model;

import java.util.List;

public class Sudoku {

	private final List<CellGroup> rows;
	private final List<CellGroup> columns;
	private final List<CellGroup> squares;

	public Sudoku(List<CellGroup> rows, List<CellGroup> columns, List<CellGroup> squares) {
		this.rows = rows;
		this.columns = columns;
		this.squares = squares;
	}
}
