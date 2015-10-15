/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 * Classe principal de execucao
 * @author vareto
 */
public class AssassinsGreed {

	/**
	 * Metodo principal de execucao
	 * @param args argumentos da linha de comando
	 */
	public static void main(String[] args) {
		try {
			if (args.length == 3) {
				switch (args[2]) {
				case "b":
					Superclass backtrack = new BackTrack(args[0], args[1]);
					backtrack.execute();
					backtrack.writeSolution("output.txt");
					break;
				case "d":
					Superclass dynamic = new Dynamic(args[0], args[1]);
					dynamic.execute();
					dynamic.writeSolution("output.txt");
					break;
				case "g":
					Superclass greedy = new Greedy(args[0], args[1]);
					greedy.execute();
					greedy.writeSolution("output.txt");
					break;
				default:
					throw new ArrayIndexOutOfBoundsException();
				}
			} else if (args.length == 2) {
				Superclass backtrack = new BackTrack(args[0], args[1]);
				backtrack.execute();
				backtrack.writeSolution("output_fb.txt");

				Superclass dynamic = new Dynamic(args[0], args[1]);
				dynamic.execute();
				dynamic.writeSolution("output_pd.txt");

				Superclass greedy = new Greedy(args[0], args[1]);
				greedy.execute();
				greedy.writeSolution("output_ag.txt");
			} else {
				throw new ArrayIndexOutOfBoundsException();
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
			System.err.println("You have inserted wrong arguments:\n\t"
					+ "java -jar AssassinsGreed_java.jar [foes] [levels] [b|d|g]\n\t"
					+ "java -jar AssassinsGreed_java.jar [foes] [levels]");

		}
	}

}
