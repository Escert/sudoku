package com.github.escert.sudoku.service;

import com.github.escert.sudoku.model.Cell;
import com.github.escert.sudoku.model.CellGroup;
import com.github.escert.sudoku.model.Sudoku;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class SudokuHtmlCreatorTest {

	@InjectMocks
	private SudokuHtmlCreator testee;

	@Nested
	class CreateSimpleHtmlPage {

		@Test
		void shouldReturnProperHtml() {
			//given
			Sudoku sudokuMock = mock(Sudoku.class);
			CellGroup firstRow = mockRow('1', '2', '3');
			CellGroup secondRow = mockRow('4', '5', '6');
			given(sudokuMock.getRows()).willReturn(List.of(firstRow, secondRow));

			//when
			String html = testee.createSimpleHtmlPage(sudokuMock);

			//then
			assertThat(html).isEqualToIgnoringNewLines(
					"""
							<html><table>
							<tr><td>1</td><td>2</td><td>3</td></tr>
							<tr><td>4</td><td>5</td><td>6</td></tr>
							</table></html>
							"""
			);
		}

		private CellGroup mockRow(Character... values) {
			CellGroup rowMock = mock(CellGroup.class);
			given(rowMock.stream()).willReturn(Arrays.stream(values).map(this::mockCell));
			return rowMock;
		}

		private Cell mockCell(Character value) {
			Cell cellMock = mock(Cell.class);
			given(cellMock.getValue()).willReturn(value);
			return cellMock;
		}
	}

}