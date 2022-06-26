package com.github.escert.sudoku.api;

import com.github.escert.sudoku.SudokuTestData;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SudokuControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;


	@Nested
	class Validate {

		@Test
		void shouldReturnInput_whenValid() throws Exception {
			//given
			String input = SudokuTestData.VALID_SUDOKU;

			//when
			ResultActions result = mockMvc.perform(get("/validate")
					.queryParam("sudoku", input));

			//then
			result.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.size").value(9))
					.andExpect(jsonPath("$.values").value(input));
		}

		@Test
		void shouldReturnError_whenInputIsInvalid() throws Exception {
			//given
			String tooLongInput = SudokuTestData.VALID_SUDOKU + "0";

			//when
			ResultActions result = mockMvc.perform(get("/validate")
					.queryParam("sudoku", tooLongInput));

			//then
			result.andExpect(status().isBadRequest());
		}
	}

	@Nested
	class Display {

		@Test
		void shouldReturnHtmlTable_whenValid() throws Exception {
			//given
			String input = SudokuTestData.normalizeInput("""
					123456789
					000000000
					000000000
					000000000
					000000000
					000000000
					000000000
					000000000
					987564321
					""");

			//when
			ResultActions result = mockMvc.perform(get("/display")
					.queryParam("sudoku", input));

			//then
			result.andExpect(status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML_VALUE))
					.andExpect(content().string(matchesPattern("<html><table>(<tr>(<td>[0-9]</td>){9}</tr>){9}</table></html>")))
					.andExpect(content().string(startsWith("<html><table><tr><td>1</td><td>2</td>")))
					.andExpect(content().string(endsWith("<td>2</td><td>1</td></tr></table></html>")));
		}

		@Test
		void shouldReturnError_whenInputIsInvalid() throws Exception {
			//given
			String tooLongInput = SudokuTestData.VALID_SUDOKU + "0";

			//when
			ResultActions result = mockMvc.perform(get("/display")
					.queryParam("sudoku", tooLongInput));

			//then
			result.andExpect(status().isBadRequest());
		}
	}

	@Nested
	class Solve {

		@Test
		void shouldReturnSolvedSudoku_whenValid() throws Exception {
			//given
			String input = SudokuTestData.VALID_SUDOKU;

			//when
			ResultActions result = mockMvc.perform(get("/solve")
					.queryParam("sudoku", input));

			//then
			result.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.size").value(9))
					.andExpect(jsonPath("$.values").value("123456789456897123789123456214365897365978214897214365531642978642789531978531642"));
		}

		@Test
		void shouldReturnError_whenInputIsInvalid() throws Exception {
			//given
			String tooLongInput = SudokuTestData.VALID_SUDOKU + "0";

			//when
			ResultActions result = mockMvc.perform(get("/solve")
					.queryParam("sudoku", tooLongInput));

			//then
			result.andExpect(status().isBadRequest());
		}
	}

}