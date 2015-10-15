/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 * Interface com metodos de acesso publico
 * @author vareto
 */
public interface Sketch {
	/**
	 * Metodo para ler um arquivo de texto contendo <i>foes</i> e armazena-lo internamente
	 * @param fileName caminho para arquivo de texto
	 * @return True se nao houver excessoes 
	 */
	boolean readFoes(String fileName);

	/**
	 * Metodo para ler um arquivo de texto contendo <i>levels</i> e armazena-lo internamente
	 * @param fileName caminho para arquivo de texto
	 * @return True se nao houver excessoes 
	 */
	boolean readLevels(String fileName);

	/**
	 * Metodo para salvar solucao armazenada em matrix em um arquivo de texto
	 * @param fileName caminho para novo arquivo de texto
	 * @return True se nao houver excessoes
	 */
	boolean writeSolution(String fileName);

	/**
	 * Metodo que executa a solucao do problema de acordo com a tecnica proposta 
	 * @return True se algoritmo foi executado
	 */
	boolean execute();
}
