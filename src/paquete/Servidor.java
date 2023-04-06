package paquete;

import java.io.*;
import java.net.*;

public class Servidor {

	private static final int PUERTO = 1090;
	
	public Servidor() {
				
	}

	public static void main(String[] args) {
		Servidor s = new Servidor();
		s.iniciarServidor();
	}
	
	public void iniciarServidor() {
		try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("[ + ] Servidor iniciado. Esperando conexiones . . .\n");

            while (true) {
                Socket clienteSocket = servidor.accept();
                System.out.println("[ * ] Nuevo cliente conectado: " + clienteSocket.getRemoteSocketAddress());

                ManejadorDeCliente manejadorDeCliente = new ManejadorDeCliente(clienteSocket);
                new Thread(manejadorDeCliente).start();
            }

        } catch (IOException e) {
            System.err.println("[ - ] Error al iniciar el servidor: " + e.getMessage());
        }
	}

}

class ManejadorDeCliente implements Runnable {
	
	private final Socket clienteSocket;
	DataInputStream entrada;
	DataOutputStream salida;
	
	public ManejadorDeCliente(Socket clienteSocket) throws IOException {
		this.clienteSocket = clienteSocket;
		this.entrada = new DataInputStream(clienteSocket.getInputStream());
		this.salida = new DataOutputStream(clienteSocket.getOutputStream());
	}

	@Override
	public void run() {
		try {
            while (true) {
            	String mensaje = entrada.readUTF();
                System.out.println("[ + ] Mensaje recibido desde " + clienteSocket.getInetAddress() + ": " + mensaje);
                
                salida.writeUTF("Servidor: " + mensaje);
            }
		} catch (IOException e) {
			System.out.println(
					"[ * ] El cliente " + clienteSocket.getRemoteSocketAddress() + " ha cerrado la conexion.\n");
		}
	}
}