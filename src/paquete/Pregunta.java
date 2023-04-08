package paquete;

public class Pregunta {
	
	private String pregunta;
	private String[] opciones;
	private String respuestaCorrecta;
	
	public Pregunta(String pregunta, String[] opciones, String respuestaCorrecta) {
		this.pregunta = pregunta;
		this.opciones = opciones;
		this.respuestaCorrecta = respuestaCorrecta;
	}
	
	public String getPregunta() {
		return pregunta;
	}
	
	public String[] getOpciones() {
		return opciones;
	}

	public String getRespuestaCorrecta() {
		return respuestaCorrecta;
	}

	public boolean verificaRespuesta(String opcion) {
		return opcion.equalsIgnoreCase(this.respuestaCorrecta);
	}
	
}
