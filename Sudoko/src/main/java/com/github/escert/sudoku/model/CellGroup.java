package com.github.escert.sudoku.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class CellGroup implements Iterable<Cell> {

	private final List<Cell> cells = new ArrayList<>();

	public void addCell(Cell cell) {
		cells.add(cell);
	}

	public boolean isValid() {
		List<Cell> noneEmptyCells = cells.stream()
				.filter(cell -> !cell.isEmpty())
				.toList();

		long distinctCellValues = noneEmptyCells.stream()
				.map(Cell::getValue)
				.distinct()
				.count();

		return distinctCellValues == noneEmptyCells.size();
	}

	@Override
	public Iterator<Cell> iterator() {
		return Collections.unmodifiableList(cells).iterator();
	}

	public Stream<Cell> stream() {
		return cells.stream();
	}
}
