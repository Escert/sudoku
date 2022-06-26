package com.github.escert.sudoku.api;

import com.github.escert.sudoku.model.Sudoku;
import com.github.escert.sudoku.service.SudokuFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class SudokuController {

	@Autowired
	private SudokuFactory sudokuFactory;

	/**
	 * Mit diesem Endpunkt kann der Eingabewert validiert werden, der ein Sudoku repräsentiert. Überprüft wird dabei, ob <br>
	 * <ul>
	 * <li>die Länge der Eingabe korrekt ist, 81 Zeichen für ein 9x9 Sudoku</li>
	 * <li>ob die Zeichen der Eingabe erlaubt sind, bei einem 9x9 Feld sollten 0-9 erlaubt sein, wobei 0 für ein leeres Feld steht.</li>
	 * <li>ob keine Sudoku-Regeln verletzt werden, beispielsweise darf bei einem 9x9 Feld die Zahl 1 nur einmal pro Spalte und Reihe gesetzt sein</li>
	 * </ul>
	 * <p>
	 * Returns:
	 * <ul>
	 * <li>400 - Bad Request - Der Eingabewert ist nicht valide.</li>
	 * <li>200 - Ok - Der Eingabewert ist valide.</li>
	 * </ul>
	 */
	@GetMapping("/validate")
	public ResponseEntity<SudokuDto> validate(@RequestParam(name = "sudoku") String sudokuInput) {
		Sudoku sudoku = sudokuFactory.fromString(sudokuInput);
		return ResponseEntity.ok(SudokuDto.from(sudoku));
	}
}
