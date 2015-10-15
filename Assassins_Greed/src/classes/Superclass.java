/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Classe abstrata com metodos de manipulacao de aquivos de texto e de inicializacao
 * @author vareto
 */
public abstract class Superclass implements Sketch {

	/**
	 * Construtor padrao do objeto
	 */
	public Superclass() {
		this.foesRead = false;
		this.levelsRead = false;
		this.solution = new ArrayList<>();
	}

	/**
	 * Construtor de inicializacao do objeto
	 * @param _foes caminho para arquivo de texto contendo <i>foes</i>
	 * @param _levels caminho para arquivo de texto contendo <i>levels</i>
	 */
	public Superclass(String _foes, String _levels) {
		this.readFoes(_foes);
		this.readLevels(_levels);
		this.solution = new ArrayList<>();
	}

	private static final ArrayList<Integer> readFile(ArrayList<Integer> array, String fileName)
			throws FileNotFoundException {
		if (array == null) {
			array = new ArrayList<>();
		}

		File infile = new File(fileName);
		Scanner reader = new Scanner(infile);
		while (reader.hasNextInt()) {
			array.add(reader.nextInt());
		}
		reader.close();

		return array;
	}

	@Override
	public final boolean readFoes(String fileName) {
		try {
			this.foes = Superclass.readFile(this.foes, fileName);
			Collections.sort(this.foes);
			return (this.foesRead = true);
		} catch (FileNotFoundException ex) {
			System.err.println(ex.getClass() + ": " + ex.getMessage());
			return (this.foesRead = false);
		}
	}

	@Override
	public final boolean readLevels(String fileName) {
		try {
			this.levels = Superclass.readFile(this.levels, fileName);
			Collections.sort(this.levels);
			return (this.levelsRead = true);
		} catch (FileNotFoundException ex) {
			System.err.println(ex.getClass() + ": " + ex.getMessage());
			return (this.foesRead = false);
		}
	}

	private static final boolean writeFile(ArrayList<ArrayList<Integer>> matrix, String fileName, boolean write)
			throws IOException {
		File outfile = new File(fileName);
		if (!outfile.exists()) {
			outfile.createNewFile();
		}

		FileWriter outWriter = new FileWriter(outfile);
		try (PrintWriter outPrinter = new PrintWriter(outWriter)) {
			for (ArrayList<Integer> row : matrix) {
				for (Integer cell : row) {
					outPrinter.write(cell.toString() + " ");
					if (write) {
						System.out.print(cell + " ");
					}
				}
				outPrinter.write("\n");
				if (write) {
					System.out.print("\n");
				}
			}
		}

		return true;
	}

	@Override
	public boolean writeSolution(String fileName) {
		try {
			return Superclass.writeFile(this.solution, fileName, true);
		} catch (IOException ex) {
			System.err.println(ex.getClass() + ": " + ex.getMessage());
			return false;
		}
	}

	@Override
	public abstract boolean execute();

	protected boolean foesRead;
	protected boolean levelsRead;
	protected ArrayList<Integer> foes;
	protected ArrayList<Integer> levels;
	protected ArrayList<ArrayList<Integer>> solution;

}
