package modulos;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static modulos.Consola.*;

public class Registro {

	private final List<String> cedula = new ArrayList<>();
	private final List<String> nombre = new ArrayList<>();
	private final List<String> apellido = new ArrayList<>();
	private final List<String> fechaNacimiento = new ArrayList<>();
	private final List<Character> genero = new ArrayList<>();
	private final List<String> estadoCivil = new ArrayList<>();

	private final List<String> generos = new ArrayList<>(Arrays.asList("M", "F"));
	private final List<String> estados = new ArrayList<>(Arrays.asList("SOLTERO", "CASADO", "DIVORCIADO", "VIUDO"));

	public void principal() {
		while (true) {
			leerEstudiante();
			ImprimirLinea("Qué desea hacer?");
			ImprimirLinea("1 -> Ingresar otro estudiante");
			ImprimirLinea("2 -> Guardar y salir");
			int opc = LeerNumeroEnIntervalo(1, 2);
			if (opc == 2) {
				crearRegistros();
				return;
			}
		}
	}

	private void crearRegistros() {
		registroMayores18();
		registroSoloMujeres();
		registroSoloSoleros();
		registroApellidosEmpiezaVocal();
	}

	private void registroApellidosEmpiezaVocal() {
		List<Integer> idApellidosVocal = new ArrayList<>();
		for (int i = 0; i < nombre.size(); i++) {
			String apellido = this.apellido.get(i);
			char letra = apellido.toUpperCase().charAt(0);
			if (letra == 'A' || letra == 'E' || letra == 'I' || letra == 'O' || letra == 'U') {
				idApellidosVocal.add(i);
			}
		}
		guardarRegistro(idApellidosVocal, "que su apellido comience con vocal");
	}

	private void registroSoloSoleros() {
		List<Integer> idSolteros = new ArrayList<>();
		for (int i = 0; i < nombre.size(); i++) {
			String estado = this.estadoCivil.get(i);
			if (estado.equalsIgnoreCase("SOLTERO")) {
				idSolteros.add(i);
			}
		}
		guardarRegistro(idSolteros, "Estudiantes de estado civil soltero");
	}

	private void registroSoloMujeres() {
		List<Integer> idMujeres = new ArrayList<>();
		for (int i = 0; i < nombre.size(); i++) {
			char genero = this.genero.get(i);
			if (genero == 'F') {
				idMujeres.add(i);
			}
		}
		guardarRegistro(idMujeres, "Estudiantes de genero femenino");
	}

	private void registroMayores18() {
		List<Integer> IdMayores18 = new ArrayList<>();
		for (int i = 0; i < nombre.size(); i++) {
			try {
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
				Date fechaNacimiento = formato.parse(this.fechaNacimiento.get(i));
				Date fechaActual = new Date();
				long diferencia = fechaActual.getTime() - fechaNacimiento.getTime();
				int edad = (int) (diferencia / (1000 * 60 * 60 * 24)) / 365;
				if (edad >= 18) {
					IdMayores18.add(i);
				}
			} catch (Exception e) {
				//
			}
		}
		guardarRegistro(IdMayores18, "Estudiantes mayores a 18 años");
	}

	private void guardarRegistro(List<Integer> ids, String nombreArchivo) {
		if (ids.size() > 0) {
			try {
				PrintWriter escritor = new PrintWriter(nombreArchivo + ".txt");
				for (int i = 0; i < nombre.size(); i++) {
					int id = ids.get(i);
					escritor.println(this.cedula.get(id) + ";"
						+ this.nombre.get(id) + ";"
						+ this.apellido.get(id) + ";"
						+ this.fechaNacimiento.get(id) + ";"
						+ this.genero.get(id) + ";"
						+ this.estadoCivil.get(id)
					);
				}
				escritor.close();
				ImprimirLinea("Registro '" + nombreArchivo + "' creado con éxito.");
			} catch (Exception e) {
				ImprimirLinea("Error al crear el registro '" + nombreArchivo + "'.");
			}
		}
		else {
			ImprimirLinea("Registro '" + nombreArchivo + "' no creado debido a que no hay estudiantes que cumplan con los requisitos.");
		}
	}

	private void leerEstudiante() {
		System.out.println("Ingreso de datos del estudiante");
		SaltarLinea();
		Imprimir("Cedula");
		String cedula = LeerCadena();
		Imprimir("Nombre");
		String nombre = LeerCadena().toUpperCase();
		Imprimir("Apellido");
		String apellido = LeerCadena().toUpperCase();
		Imprimir("Fecha nacimiento");
		String fechaNacimiento = LeerFecha();
		Imprimir("Genero (M/F)");
		char genero = LeerCadenaConOpcion(generos).charAt(0);
		Imprimir("Estado civil (Soltero/Casado/Divorciado/Viudo)");
		String estado = LeerCadenaConOpcion(estados);
		this.cedula.add(cedula);
		this.nombre.add(nombre);
		this.apellido.add(apellido);
		this.fechaNacimiento.add(fechaNacimiento);
		this.genero.add(genero);
		this.estadoCivil.add(estado);
		SaltarLinea();
	}

}
