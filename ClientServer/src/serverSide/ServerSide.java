/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Vareto
 */
public class ServerSide {

	public final static int PORT = 1234;

	@SuppressWarnings("CallToPrintStackTrace")
	public static void main(String[] args) throws IOException {
		// Declarando ServerSocket
		try (ServerSocket server = new ServerSocket(PORT)) {
			System.out.println("Server is Running...");

			//Declarando ArrayList para armazenar mensagens enviadas
			List<String> messages = new ArrayList<>();
			List<String> timeStamps = new ArrayList<>();

			String message = null;
			do {
				// Estabelecendo a conexao com o cliente
				Socket client = server.accept();
				System.out.println("Client " + client.getInetAddress().getHostAddress() + " logged in");

				// Lendo mensagem enviada pelo ClientSide
				try (Scanner reader = new Scanner(client.getInputStream())) {
					if (reader.hasNextLine()) {
						message = reader.nextLine();

						// Armazenando mensagens enviadas
						timeStamps.add(new java.util.Date().toString());
						messages.add(message);

						System.out.println(message);
					}

					// Fechando conexoes
					reader.close();
					client.close();
				}
				// Executar ServerSide ate que cliente envie a mensagem 'disconnect'
			} while (message.compareToIgnoreCase("disconnect") != 0);

			server.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
