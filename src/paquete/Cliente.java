package paquete;

import java.io.*;
import java.net.*;
import java.util.*;

public class Cliente {

	private String HOST = "192.168.0.6";
	private static final int PORT = 1090;

	public Cliente() {
	}

	public static void main(String[] args) {
		Cliente c = new Cliente();
		c.iniciarCliente();
	}

	public void iniciarCliente() {
		try {
			//getHost();
			Socket socket = new Socket(HOST, 1090);

			DataOutputStream flujoSalida = new DataOutputStream(socket.getOutputStream());
			DataInputStream flujoEntrada = new DataInputStream(socket.getInputStream());
			
			Scanner scanner = new Scanner(System.in);
			
			while (true) {
				System.out.print("$user > ");
				String msg = scanner.nextLine();
				
				flujoSalida.writeUTF(msg);
				
				String respuesta = flujoEntrada.readUTF();
				System.out.println(respuesta);
			}

		} catch (Exception e) {
			System.out.println("[ - ] Error: No se puede conectar con el host. " + e.getMessage());

		}
	}
	
	/*public void getHost() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Ingrese el nombre del host al que desea conectarse: ");
		HOST = scanner.nextLine();
	}*/
}
