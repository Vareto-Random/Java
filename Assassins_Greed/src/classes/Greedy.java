/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe concreta com implementacao de algoritmo utilizando tecnica Gulosa
 * @author vareto
 */
public class Greedy extends Superclass {

	/**
	 * Construtor padrao do objeto
	 */
	public Greedy() {
		super();
	}

	/**
	 * Construtor de inicializacao do objeto
	 * @param _foes caminho para arquivo de texto contendo <i>foes</i>
	 * @param _levels caminho para arquivo de texto contendo <i>levels</i>
	 */
	public Greedy(String _foes, String _levels) {
		super(_foes, _levels);
	}

	@Override
	public boolean execute() {
		if (this.foesRead && this.levelsRead) {
			int newLevel;

			Collections.sort(this.foes, Collections.reverseOrder());

			for (Integer level : this.levels) {
				ArrayList<Integer> mafia = new ArrayList<>(this.foes.size());
				newLevel = level;

				for (Integer foe : this.foes) {
					while (foe <= newLevel) {
						mafia.add(foe);
						newLevel -= foe;
					}
				}
				
				if (newLevel != 0) {
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
