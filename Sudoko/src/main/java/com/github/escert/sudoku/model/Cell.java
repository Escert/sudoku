package com.github.escert.sudoku.model;

public class Cell {

	private final boolean isModifiable;
	private final CellGroup row;
	private final CellGroup column;
	private final CellGroup square;
	private char value;

	public Cell(char value, CellGroup row, CellGroup column, CellGroup square) {
		this.value = value;
		this.row = row;
		this.column = column;
		this.square = square;
		this.isModifiable = isEmpty();

		row.addCell(this);
		column.addCell(this);
		square.addCell(this);
	}

	public boolean isEmpty() {
		return value == '0';
	}

	public char getValue() {
		return value;
	}

	public void setValue(char value) {
		if (!isModifiable) {
			throw new UnsupportedOperationException("Cell is not modifiable");
		}
		this.value = value;
	}

	public boolean isValid() {
		return row.isValid() && column.isValid() && square.isValid();
	}
}
