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
			Socket socket = new Socket(HOST, 1090);

			DataOutputStream flujoSalida = new DataOutputStream(socket.getOutputStream());
			DataInputStream flujoEntrada = new DataInputStream(socket.getInputStream());

			Scanner scanner = new Scanner(System.in);

			System.out.print("Ingrese su nombre de usuario: ");
			String nombreUsuario = scanner.nextLine();

			flujoSalida.writeUTF(nombreUsuario);
			
			String respuesta = flujoEntrada.readUTF();
			
			if (respuesta.equalsIgnoreCase("ok")) {
				System.out.println("Bienvenido, " + nombreUsuario + "!");
				while (true) {
					System.out.print("$" + nombreUsuario + " > ");
					String msg = scanner.nextLine();
					flujoSalida.writeUTF(msg);
					respuesta = flujoEntrada.readUTF();
				}
			} else {
				System.out.println("[ + ] Error: Nombre de usuario ya existe");
				iniciarCliente();
			}

			

		} catch (Exception e) {
			System.out.println("[ - ] Error: No se puede conectar con el host. " + e.getMessage());

		}
	}
}
