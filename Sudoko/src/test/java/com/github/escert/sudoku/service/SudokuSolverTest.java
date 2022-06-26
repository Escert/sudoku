package com.github.escert.sudoku.service;

import com.github.escert.sudoku.model.Cell;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class SudokuSolverTest {

	@InjectMocks
	SudokuSolver testee;

	@Nested
	class GetNextValue {

		@ParameterizedTest
		@CsvSource({
				"0,1",
				"1,2",
				"2,3",
				"3,4",
				"4,5",
				"5,6",
				"6,7",
				"7,8",
				"8,9",
				"9,",
		})
		void shouldReturnNextValueDependingOnInput(char givenValue, Character expectedValue) {
			//given
			Cell cellMock = mock(Cell.class);
			given(cellMock.getValue()).willReturn(givenValue);

			//when
			Optional<Character> result = testee.getNextValue(cellMock);

			//then
			if (expectedValue != null) {
				assertThat(result).hasValue(expectedValue);
			} else {
				assertThat(result).isEmpty();
			}
		}

		@Test
		void shouldThrow_whenInputIsInvalid() {
			//given
			Cell cellMock = mock(Cell.class);
			given(cellMock.getValue()).willReturn('A');

			//when / then
			assertThatThrownBy(() -> testee.getNextValue(cellMock))
					.isInstanceOf(IllegalStateException.class);
		}
	}

	@Nested
	class FillWithNextValidValueOrMakeEmpty {

		@Test
		void shouldSetNextValue_whenValid() {
			//given
			Cell cellMock = mock(Cell.class);
			given(cellMock.getValue()).willReturn('0');
			given(cellMock.isValid()).willReturn(true);

			//when
			testee.fillWithNextValidValueOrMakeEmpty(cellMock);

			//then
			then(cellMock).should().setValue('1');
		}

		@Test
		void shouldSetNextValidValue_whenFirstIsInvalid() {
			//given
			Cell cellMock = mock(Cell.class);
			given(cellMock.getValue()).willReturn('0', '1');
			given(cellMock.isValid()).willReturn(false, true);

			//when
			testee.fillWithNextValidValueOrMakeEmpty(cellMock);

			//then
			InOrder inOrder = inOrder(cellMock);
			inOrder.verify(cellMock).setValue('1');
			inOrder.verify(cellMock).setValue('2');
		}
	}
}