package com.github.escert.sudoku.model;

import java.util.List;

public class Sudoku {

	private final List<Cell> cells;
	private final List<CellGroup> rows;
	private final List<CellGroup> columns;
	private final List<CellGroup> squares;

	public Sudoku(List<Cell> cells, List<CellGroup> rows, List<CellGroup> columns, List<CellGroup> squares) {
		this.cells = cells;
		this.rows = rows;
		this.columns = columns;
		this.squares = squares;
	}

	public List<Cell> getCells() {
		return cells;
	}

	public int getSize() {
		return rows.size();
	}
}
