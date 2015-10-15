/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe concreta com implementacao de algoritmo utilizando tecnica de Programacao Dinamica
 * @author vareto
 */
public class Dynamic extends Superclass {

	/**
	 * Construtor padrao do objeto
	 */
	public Dynamic() {
		super();
	}

	/**
	 * Construtor de inicializacao do objeto
	 * @param _foes caminho para arquivo de texto contendo <i>foes</i>
	 * @param _levels caminho para arquivo de texto contendo <i>levels</i>
	 */
	public Dynamic(String _foes, String _levels) {
		super(_foes, _levels);
	}

	private Integer[][] initializeSolution(Integer[][] matrix) {
		for (int column = 0; column < matrix[0].length; column++) {
			matrix[0][column] = Integer.MAX_VALUE - 1;
		}

		for (int row = 1; row < matrix.length; row++) {
			matrix[row][0] = 0;
		}

		matrix[1][0] = 0;

		return matrix;
	}

	@Override
	public boolean execute() {
		if (this.foesRead && this.levelsRead) {

			for (Integer level : this.levels) {
				Integer[][] matrix = new Integer[this.foes.size() + 1][level + 1];
				ArrayList<Integer> mafia = new ArrayList<>(this.foes.size());

				matrix = this.initializeSolution(matrix);

				for (int row = 1; row < matrix.length; row++) {
					for (int column = 1; column < matrix[row].length; column++) {
						if (column >= this.foes.get(row - 1)) {
							matrix[row][column] = Math.min(matrix[row - 1][column],
									matrix[row][column - this.foes.get(row - 1)] + 1);
						} else {
							matrix[row][column] = matrix[row - 1][column];
						}
					}
				}

				int row = matrix.length - 1;
				int column = matrix[0].length - 1;
				int foe = row - 1;
				try {
					while (matrix[row][column] != 0) {
						int enemy = this.foes.get(foe);
						if (column >= enemy) {
							if (matrix[row][column] == (matrix[row][column - enemy] + 1)) {
								column -= enemy;
								mafia.add(enemy);
							} else if (matrix[row][column].equals(matrix[row - 1][column])) {
								row--;
								foe--;
							}
						} else {
							foe--;
						}
					}
				} catch (ArrayIndexOutOfBoundsException ex) {
					// System.err.println("It is impossible to generate an
					// answer to LEVEL "
					// + column + " when your smallest FOE is " +
					// this.foes.get(0));
					mafia.clear();
				}

				mafia.add(mafia.size());
				Collections.reverse(mafia);
				this.solution.add(mafia);
			}

			return true;
		}
		return false;
	}

}
