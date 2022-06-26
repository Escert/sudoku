package com.github.escert.sudoku.service;

import com.github.escert.sudoku.model.Cell;
import com.github.escert.sudoku.model.Sudoku;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Service;

import java.util.ListIterator;
import java.util.Optional;

@Service
public class SudokuSolver {

	private static final String VALUES_IN_ORDER = "0123456789";

	public Sudoku solve(Sudoku sudoku) {
		ListIterator<Cell> fillableCellsIter = sudoku.getCells().stream()
				.filter(Cell::isEmpty)
				.toList()
				.listIterator();

		boolean wasLastFillSuccessful = true;
		while (fillableCellsIter.hasNext()) {
			Cell cell = wasLastFillSuccessful ? fillableCellsIter.next() : fillableCellsIter.previous();
			fillWithNextValidValueOrMakeEmpty(cell);
			wasLastFillSuccessful = !cell.isEmpty();
		}
		return sudoku;
	}

	@VisibleForTesting
	Cell fillWithNextValidValueOrMakeEmpty(Cell cell) {
		char nextValue = getNextValue(cell)
				.orElseGet(() -> VALUES_IN_ORDER.charAt(0));

		cell.setValue(nextValue);
		if (!cell.isValid()) {
			fillWithNextValidValueOrMakeEmpty(cell);
		}
		return cell;
	}

	@VisibleForTesting
	Optional<Character> getNextValue(Cell cell) {
		int index = VALUES_IN_ORDER.indexOf(cell.getValue());
		if (index == -1) {
			throw new IllegalStateException("Cell has value " + cell.getValue() + " which is not allowed");
		}
		int nextValueIndex = index + 1;
		if (nextValueIndex >= VALUES_IN_ORDER.length()) {
			return Optional.empty();
		}

		return Optional.of(VALUES_IN_ORDER.charAt(nextValueIndex));
	}
}
