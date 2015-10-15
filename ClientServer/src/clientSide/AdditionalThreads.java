/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientSide;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author Vareto
 */
public class AdditionalThreads {

	private static final int CONNECTION_ERROR_MESSAGE = 0;
	private static final int CONNECTION_ESTABLISHED = 1;
	private static final int MESSAGE_SENT = 2;
	private static final int TRANSMISSION_TIMEOUT = 3;

	/**
	 * Construtor que prepara os componentes da classe para se conectar com
	 * ServerSide
	 *
	 * @param _ipAddress endereco de ip
	 * @param _port endereco da porta
	 * @param _message mensagem a ser enviada
	 */
	public AdditionalThreads(String _ipAddress, int _port, String _message) {
		this.ipAddress = _ipAddress;
		this.message = _message;
		this.port = _port;
		this.statusMessage = null;
		this.timeout = -1;
		this.timeStamp = new java.util.Date().toString();
		this.execution();
	}

	/**
	 * Construtor que prepara os componentes da classe para se conectar com
	 * ServerSide
	 *
	 * @param _ipAddress endereco de ip
	 * @param _port endereco da porta
	 * @param _message mensagem a ser enviada
	 * @param _timeout tempo de espera da resposta em milisegundos
	 */
	public AdditionalThreads(String _ipAddress, int _port, String _message, int _timeout) {
		this.ipAddress = _ipAddress;
		this.message = _message;
		this.port = _port;
		this.statusMessage = null;
		this.timeout = _timeout;
		this.timeStamp = new java.util.Date().toString();
		this.execution();
	}

	/**
	 * Metodo responsavel em invocar novas thread
	 */
	private void execution() {
		Broadcast communication = new Broadcast();
		Thread transmission = new Thread(communication);
		transmission.start();
	}

	/**
	 * Metodo responsavel em gerar o feedback para o usuario tanto pela
	 * interface (GUI) quando pelo console
	 *
	 * @param id identificador da mensagem
	 */
	private void showMessage(int id) {
		switch (id) {
			case 0:
				this.statusMessage
						= this.timeStamp
						+ " - Connection could not be established with ServerSide";
				break;
			case 1:
				this.statusMessage
						= this.timeStamp
						+ " - ClientSide has successfully connected to ServerSide";
				break;
			case 2:
				this.statusMessage 
						= this.timeStamp 
						+ " - Message has succesfully been sent to ServerSide";
				break;
			case 3:
				this.statusMessage 
						= this.timeStamp 
						+ " - Connection has exceeded the configured timeout value";
				break;
		}
	}

	/**
	 * Classe que sera executada em outra thread. Esta classe e responsavel pela
	 * comunicacao entre ServerSide e ClientSide
	 */
	private class Broadcast implements Runnable {

		/**
		 * Este metodo garante que uma outra thread fique responsavel em enviar
		 * a mensagem para ServerSide
		 */
		@Override
		public void run() {
			try {
				Socket client = new Socket(ipAddress, port);
				try {
					PrintStream output = new PrintStream(client.getOutputStream());
					showMessage(MESSAGE_SENT);

					output.println(timeStamp + " " + message);
					output.flush();
					output.close();
				} catch (IOException ex) {
					showMessage(CONNECTION_ERROR_MESSAGE);
				}
				client.close();
			} catch (IOException ex) {
				showMessage(CONNECTION_ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Classe que sera executada em outra thread. Esta classe e responsavel em
	 * calcular o tempo de envio da mensagem (timeout)
	 */
	private class Timeout implements Runnable {

		/**
		 * Este metodo garante que uma outra thread fique responsavel em
		 * calcular o tempo de envio da mensagem (timeout)
		 */
		@Override
		public void run() {

		}
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	private final String ipAddress;
	private final String message;
	private final int port;
	private String statusMessage;
	private final String timeStamp;
	private final int timeout;
	private Boolean timeoutStatus;
}
