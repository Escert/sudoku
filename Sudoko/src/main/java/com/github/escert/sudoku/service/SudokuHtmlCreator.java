package com.github.escert.sudoku.service;

import com.github.escert.sudoku.model.CellGroup;
import com.github.escert.sudoku.model.Sudoku;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class SudokuHtmlCreator {

	public String createSimpleHtmlPage(Sudoku sudoku) {
		return sudoku.getRows()
				.stream()
				.map(this::createTableRow)
				.collect(Collectors.joining("", "<html><table>", "</table></html>"));
	}

	private String createTableRow(CellGroup row) {
		String rowAsString = row.stream()
				.map(cell -> "<td>" + cell.getValue() + "</td>")
				.collect(Collectors.joining());
		return "<tr>" + rowAsString + "</tr>";
	}
}
