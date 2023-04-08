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
			
			String acceso = flujoEntrada.readUTF();
			
			if (acceso.equalsIgnoreCase("ok")) {
				System.out.println("Bienvenido, " + nombreUsuario + "!");
				while (true) {
					String pregunta = flujoEntrada.readUTF();
					System.out.println("Q: " + pregunta);
					
					for (int i = 0; i < 4; i++) {
						String opciones = flujoEntrada.readUTF();
						System.out.println((i + 1) + ".- " + opciones);
					}
					
					System.out.print("R = ");
					
					String respuesta = scanner.nextLine();
					flujoSalida.writeUTF(respuesta);
					
					String acerto = flujoEntrada.readUTF();
					System.out.println(acerto);

				}
			} else {
				System.out.println("[ + ] Error: Nombre de usuario ya existe");
				iniciarCliente();
			}

			
			socket.close();
		} catch (Exception e) {
			System.out.println("[ - ] Error: No se puede conectar con el host. " + e.getMessage());

		}
	}
}
