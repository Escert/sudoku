package com.github.escert.sudoku.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class SudokuValidationException extends RuntimeException {

	public SudokuValidationException(String message) {
		super(message);
	}
}
