package com.github.escert.sudoku.service;

import com.github.escert.sudoku.SudokuTestData;
import com.github.escert.sudoku.model.Sudoku;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class SudokuFactoryTest {

	@InjectMocks
	private SudokuFactory testee;


	@Nested
	class FromString {

		@Test
		void shouldCreateSudoku_whenInputIsValid() {
			//given
			String input = SudokuTestData.VALID_SUDOKU;

			//when
			Sudoku sudoku = testee.fromString(input);

			//then
			assertThat(sudoku).isNotNull();
		}

		@Test
		void shouldThrow_whenInputIsTooLong() {
			//given
			String input = SudokuTestData.VALID_SUDOKU + "0";

			//when / then
			assertThatThrownBy(() -> testee.fromString(input))
					.isInstanceOf(SudokuValidationException.class)
					.hasMessage("Sudoku has 82 cells defined but needs to have exactly 81 cells");
		}

		@Test
		void shouldThrow_whenInputIsTooShort() {
			//given
			String input = SudokuTestData.VALID_SUDOKU.substring(0, SudokuTestData.VALID_SUDOKU.length() - 1);

			//when / then
			assertThatThrownBy(() -> testee.fromString(input))
					.isInstanceOf(SudokuValidationException.class)
					.hasMessage("Sudoku has 80 cells defined but needs to have exactly 81 cells");
		}

		@Test
		void shouldThrow_whenInputContainsIllegalValue() {
			//given
			String input = SudokuTestData.VALID_SUDOKU.substring(0, SudokuTestData.VALID_SUDOKU.length() - 1) + "A";

			//when / then
			assertThatThrownBy(() -> testee.fromString(input))
					.isInstanceOf(SudokuValidationException.class)
					.hasMessage("Sudoku contains invalid value");
		}

		@Test
		void shouldThrow_whenRowContainsSameValueMultipleTimes() {
			//given
			String input = SudokuTestData.normalizeInput("""
					000000000
					000000000
					100000100
					000000000
					000000000
					000000000
					000000000
					000000000
					000000000
					""");

			//when / then
			assertThatThrownBy(() -> testee.fromString(input))
					.isInstanceOf(SudokuValidationException.class)
					.hasMessage("Duplicated value 1 in row 3");
		}

		@Test
		void shouldThrow_whenColumnContainsSameValueMultipleTimes() {
			//given
			String input = SudokuTestData.normalizeInput("""
					000020000
					000000000
					000000000
					000020000
					000000000
					000000000
					000000000
					000000000
					000000000
					""");

			//when / then
			assertThatThrownBy(() -> testee.fromString(input))
					.isInstanceOf(SudokuValidationException.class)
					.hasMessage("Duplicated value 2 in column 5");
		}

		@Test
		void shouldThrow_whenSquareContainsSameValueMultipleTimes() {
			//given
			String input = SudokuTestData.normalizeInput("""
					000000000
					000000000
					000000000
					000000000
					000000003
					000000030
					000000000
					000000000
					000000000
					""");

			//when / then
			assertThatThrownBy(() -> testee.fromString(input))
					.isInstanceOf(SudokuValidationException.class)
					.hasMessage("Duplicated value 3 in square 6");
		}
	}
}