package paquete;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Quiz {

    private List<Pregunta> preguntas;
    private Scanner scanner;
    private int tiempoLimite = 5;
    private Timer temporizador = new Timer();

    public Quiz() {
        this.preguntas = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void agregarPregunta(Pregunta pregunta) {
        preguntas.add(pregunta);
    }

    private void iniciarTemporizador() {
        temporizador.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("\n�Tiempo agotado!");
            }
        }, tiempoLimite * 1000);
    }

    public void mostrarPreguntas() {
        System.out.println("Bienvenido al quiz!");
        System.out.println("Presiona Enter para comenzar...");
        scanner.nextLine();

        for (Pregunta pregunta : preguntas) {
            System.out.println(pregunta.getPregunta());
            String[] opciones = pregunta.getOpciones();
            for (int i = 0; i < opciones.length; i++) {
                System.out.println((i + 1) + ". " + opciones[i]);
            }

            // iniciarTemporizador();

            System.out.print("R= ");
            String respuesta = scanner.nextLine();   

            boolean respuestaCorrecta = pregunta.verificaRespuesta(respuesta);
            System.out.println("Respuesta: " + (respuestaCorrecta ? "Correcta" : "Incorrecta"));
        }
    }

    //Modificar las preguntas existentes
	//Reiniciar Quiz
}
