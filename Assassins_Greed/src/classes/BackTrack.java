/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe concreta com implementacao de algoritmo utilizando tecnica de Forca
 * Bruta
 * 
 * @author vareto
 */
public class BackTrack extends Superclass {

	/**
	 * Construtor padrao do objeto
	 */
	public BackTrack() {
		super();
		this.nodes = 0L;
	}

	/**
	 * Construtor de inicializacao do objeto
	 * 
	 * @param _foes
	 *            caminho para arquivo de texto contendo <i>foes</i>
	 * @param _levels
	 *            caminho para arquivo de texto contendo <i>levels</i>
	 */
	public BackTrack(String _foes, String _levels) {
		super(_foes, _levels);
		this.nodes = 0L;
	}

	@Override
	public boolean execute() {
		if (this.foesRead && this.levelsRead) {
			for (Integer level : this.levels) {
				this.bestSize = Integer.MAX_VALUE;
				this.bestArray = new ArrayList<>();

				ArrayList<Integer> mafia = new ArrayList<>();
				this.bruteRecursion(mafia, level, 0);

				Collections.sort(this.bestArray);
				this.bestArray.add(0, this.bestArray.size());
				this.solution.add(this.bestArray);
			}
			System.out.println("Nodes: " + this.nodes);
			return true;
		}
		return false;
	}

	private void bruteRecursion(ArrayList<Integer> array, int remaining, int descendant) {
		this.nodes++;
		if (remaining == 0) {
			if (this.bestArray.isEmpty() || (array.size() < this.bestArray.size())) {
				this.bestArray = new ArrayList<>(array);
				this.bestSize = this.bestArray.size();
			}
		} else if (array.size() <= this.bestSize) {
			for (int index = descendant; index < this.foes.size(); index++) {
				Integer foe = this.foes.get(index);
				if (remaining >= foe) {
					array.add(foe);
					this.bruteRecursion(array, remaining - foe, index);
					array.remove(array.size() - 1);
				}
			}
		}
	}

	private ArrayList<Integer> bestArray;
	private Integer bestSize;
	private Long nodes;

}
