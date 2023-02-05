package modulos.registro;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static modulos.Consola.*;

public class Registro {
	private final List<Estudiante> estudiantes = new ArrayList<>();
	private final List<String> generos = new ArrayList<>(Arrays.asList("M", "F"));
	private final List<String> estados = new ArrayList<>(Arrays.asList("SOLTERO", "CASADO", "DIVORCIADO", "VIUDO"));

	public void principal() {
		while (true) {
			leerEstudiante();
			ImprimirLinea("Qué desea hacer?");
			ImprimirLinea("1 -> Ingresar otro estudiante");
			ImprimirLinea("2 -> Guardar y salir");
			int opc = LeerNumeroIntervalo(1, 2);
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
		List<Estudiante> apellidosVocal = new ArrayList<>();
		for (int i = 0; i < estudiantes.size(); i++) {
			Estudiante estudiante = estudiantes.get(i);
			char letra = estudiante.getApellido().toUpperCase().charAt(0);
			if (letra == 'A' || letra == 'E' || letra == 'I' || letra == 'O' || letra == 'U') {
				apellidosVocal.add(estudiante);
			}
		}
		guardarRegistro(apellidosVocal, "que su apellido comience con vocal");
	}

	private void registroSoloSoleros() {
		List<Estudiante> solteros = new ArrayList<>();
		for (int i = 0; i < estudiantes.size(); i++) {
			Estudiante estudiante = estudiantes.get(i);
			if (estudiante.getEstadoCivil().equalsIgnoreCase("SOLTERO")) {
				solteros.add(estudiante);
			}
		}
		guardarRegistro(solteros, "Estudiantes de estado civil soltero");
	}

	private void registroSoloMujeres() {
		List<Estudiante> mujeres = new ArrayList<>();
		for (int i = 0; i < estudiantes.size(); i++) {
			Estudiante estudiante = estudiantes.get(i);
			if (estudiante.getGenero() == 'F') {
				mujeres.add(estudiante);
			}
		}
		guardarRegistro(mujeres, "Estudiantes de genero femenino");
	}

	private void registroMayores18() {
		List<Estudiante> mayores18 = new ArrayList<>();
		for (int i = 0; i < estudiantes.size(); i++) {
			Estudiante estudiante = estudiantes.get(i);
			try {
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
				Date fechaActual = new Date();
				Date fechaNacimiento = formato.parse(estudiante.getFechaNacimiento());
				long diferencia = fechaActual.getTime() - fechaNacimiento.getTime();
				int edad = (int) (diferencia / (1000 * 60 * 60 * 24)) / 365;
				if (edad >= 18) {
					mayores18.add(estudiante);
				}
			} catch (Exception e) {
				//
			}
		}
		guardarRegistro(mayores18, "Estudiantes mayores a 18 años");
	}

	private void guardarRegistro(List<Estudiante> estudiantes, String nombreArchivo) {
		if (estudiantes.size() >= 0) {
			try {
				PrintWriter escritor = new PrintWriter(nombreArchivo + ".txt");
				for (int i = 0; i < estudiantes.size(); i++) {
					Estudiante estudiante = estudiantes.get(i);
					escritor.println(estudiante.getCedula() + ";"
						+ estudiante.getNombre() + ";"
						+ estudiante.getApellido() + ";"
						+ estudiante.getFechaNacimiento() + ";"
						+ estudiante.getGenero() + ";"
						+ estudiante.getEstadoCivil()
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
		estudiantes.add(new Estudiante(cedula, nombre, apellido, fechaNacimiento, genero, estado));
		SaltarLinea();
	}

}
