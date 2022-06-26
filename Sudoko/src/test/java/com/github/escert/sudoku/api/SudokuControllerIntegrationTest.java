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

}