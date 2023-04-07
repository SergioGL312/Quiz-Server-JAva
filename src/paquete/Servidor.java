package paquete;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Servidor {

	private static final int PUERTO = 1090;

	private List<Jugador> listaJugadores;

	public Servidor() {
		this.listaJugadores = new ArrayList<Jugador>();
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
				
				ManejadorDeCliente manejadorDeCliente = new ManejadorDeCliente(clienteSocket, this);
				new Thread(manejadorDeCliente).start();
			}

		} catch (IOException e) {
			System.err.println("[ - ] Error: " + e.getMessage());
		}
	}
	
	public boolean agregarJugador(Jugador jugador) {
		boolean encontrado = false;
		for (Jugador j : listaJugadores) {
			if (j.getNombreJugador().equals(jugador.getNombreJugador())) {
				j.agregarPuntos(jugador.getPuntos());
	            j.agregarTiempo(jugador.getTiempo());
				encontrado = true;
				break;
			}
		}
		if (!encontrado) {
			System.out.println("[ + ] Jugador agregado a la lista de jugadores: " + jugador.getNombreJugador());
			listaJugadores.add(jugador);
		}
		return encontrado;
	}

	public void getNombreJugadores() {
		System.out.println("Jugadores conectados: ");
	    for (Jugador jugador : listaJugadores) {
			System.out.println(jugador.getNombreJugador());
		}
	    System.out.println("\n");
	}

	public void eliminarJugador(String nombreJugador) {
        for (Iterator<Jugador> iter = listaJugadores.iterator(); iter.hasNext();) {
            Jugador jugador = iter.next();
            if (jugador.getNombreJugador().equals(nombreJugador)) {
                iter.remove();
                System.out.println("[ - ] Jugador eliminado de la lista de jugadores: " + nombreJugador);
                break;
            }
        }
    }
}

class ManejadorDeCliente implements Runnable {

	private final Socket clienteSocket;
	private final Servidor servidor;
	DataInputStream entrada;
	DataOutputStream salida;
	private String nombreUsuario;

	public ManejadorDeCliente(Socket clienteSocket, Servidor servidor) throws IOException {
		this.clienteSocket = clienteSocket;
		this.servidor = servidor;
		this.entrada = new DataInputStream(clienteSocket.getInputStream());
		this.salida = new DataOutputStream(clienteSocket.getOutputStream());
	}

	@Override
	public void run() {
		
		try {
			nombreUsuario = entrada.readUTF();
			System.out.println(
					"[ + ] Nombre de usuario recibido desde " + clienteSocket.getInetAddress() + ": " + nombreUsuario);

			Jugador jugador = new Jugador(nombreUsuario, 0, 0);
			
			
			if (servidor.agregarJugador(jugador)) {
				salida.writeUTF("no");
				servidor.getNombreJugadores();
			} else {
				servidor.getNombreJugadores();
				salida.writeUTF("ok");
				while (true) {
					
					String mensaje = entrada.readUTF();

					System.out.println("[ + ] Mensaje recibido desde " + clienteSocket.getInetAddress() + ": " + mensaje);

					salida.writeUTF(mensaje);
				}
			}
			
			
		} catch (IOException e) {
			System.out.println(
					"[ * ] El cliente " + clienteSocket.getRemoteSocketAddress() + " ha cerrado la conexion.\n");
			servidor.eliminarJugador(nombreUsuario);
			servidor.getNombreJugadores();
		}
	}
}