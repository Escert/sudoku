package com.github.escert.sudoku.service;

import com.github.escert.sudoku.model.Cell;
import com.github.escert.sudoku.model.CellGroup;
import com.github.escert.sudoku.model.Sudoku;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class SudokuFactory {

	private static final int SIZE = 9;

	public Sudoku fromString(String input) {
		verifyInputSize(input);
		verifyInputValues(input);

		List<CellGroup> rows = createEmptyCellGroups(SIZE);
		List<CellGroup> columns = createEmptyCellGroups(SIZE);
		List<CellGroup> squares = createEmptyCellGroups(SIZE);
		int sizeOfSquare = Double.valueOf(Math.sqrt(SIZE)).intValue();

		for (int row = 0; row < SIZE; row++) {
			for (int column = 0; column < SIZE; column++) {
				new Cell(
						input.charAt(row * SIZE + column),
						rows.get(row),
						columns.get(column),
						squares.get(Math.floorDiv(row, sizeOfSquare) * sizeOfSquare + column / sizeOfSquare)
				);
			}
		}

		verifyCellGroupsIsValid(rows, "row");
		verifyCellGroupsIsValid(columns, "column");
		verifyCellGroupsIsValid(squares, "square");

		return new Sudoku(rows, columns, squares);
	}

	private void verifyInputSize(String input) {
		int requiredNumberOfCells = (int) Math.pow(SIZE, 2);
		if (input.length() != requiredNumberOfCells) {
			throw new SudokuValidationException("Sudoku has " + input.length() + " cells defined but needs to have exactly " + requiredNumberOfCells + " cells");
		}
	}

	private void verifyInputValues(String input) {
		if (!input.matches("[0-9]+")) {
			throw new SudokuValidationException("Sudoku contains invalid value");
		}
	}

	private List<CellGroup> createEmptyCellGroups(int count) {
		return IntStream.range(0, count)
				.mapToObj(index -> new CellGroup())
				.toList();
	}

	private void verifyCellGroupsIsValid(List<CellGroup> cellGroups, String cellGroupName) {
		for (int i = 0; i < cellGroups.size(); i++) {
			verifyCellGroupIsValid(cellGroups.get(i), i, cellGroupName);
		}
	}

	private void verifyCellGroupIsValid(CellGroup cellGroup, int cellGroupIndex, String cellGroupName) {
		if (cellGroup.isValid()) {
			return;
		}

		List<Character> characters = cellGroup.stream()
				.filter(cell -> !cell.isEmpty())
				.map(Cell::getValue)
				.distinct()
				.toList();
		for (Character character : characters) {
			long countOfThisCharacter = cellGroup.stream()
					.filter(cell -> cell.getValue() == character)
					.count();
			if (countOfThisCharacter > 1) {
				throw new SudokuValidationException("Duplicated value " + character + " in " + cellGroupName + " " + (cellGroupIndex + 1));
			}
		}
	}
}
