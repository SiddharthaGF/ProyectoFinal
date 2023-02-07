package modulos;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class Consola {

	public static void Imprimir(Object argumento) {
		System.out.print(argumento);
	}

	public static void ImprimirLinea(Object argumento) {
		System.out.println(argumento);
	}

	public static void SaltarLinea() {
		System.out.println();
	}

  public static String LeerCadena() {
		String string;
		Scanner teclado = new Scanner(System.in);
		while (true) {
			Imprimir(" >> ");
			string = teclado.nextLine().trim();
			if (string.length() > 0) {
				teclado.reset();
				return string;
			}
			Imprimir("Entrada no valida, intente de nuevo");
		}
	}

	public static String LeerCadenaConOpcion(List<String> opciones) {
		String string;
		Scanner teclado = new Scanner(System.in);
		while (true) {
			Imprimir(" >> ");
			string = teclado.nextLine().trim();
			if (string.length() > 0 && opciones.contains(string.toUpperCase())) {
				teclado.reset();
				return string;
			}
			Imprimir("Entrada no valida, intente de nuevo");
		}
	}

	public static int LeerNumeroEnIntervalo(double min, double max) {
		int num;
		Scanner teclado = new Scanner(System.in);
		while (true) {
			Imprimir(" >> ");
			num = teclado.nextInt();
			if (num >= min && num <= max) {
				teclado.reset();
				return num;
			}
			Imprimir("Entrada no valida, intente de nuevo");
		}
	}

	public static int LeerNumeroMayorQue(int valor) {
		return LeerNumeroEnIntervalo(valor, Integer.MAX_VALUE);
	}

	public static int LeerNumeroMenorQue(int valor) {
		return LeerNumeroEnIntervalo(-Integer.MAX_VALUE, valor);
	}

	public static int LeerNumero() {
		return LeerNumeroEnIntervalo(0, 0);
	}

	public static void printf(String formato, Object... datos) {
		System.out.printf(formato, datos);
		Imprimir("\n");
	}

	public static String LeerFecha() {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		formato.setLenient(false);
		while (true) {
			String fecha = LeerCadena().trim();
			try {
				formato.parse(fecha);
				return fecha;
			} catch (Exception e) {
				Imprimir("Fecha no válida, intente de nuevo");
			}
		}
	}

	public static void EsperarTecla() {
		Imprimir("Presione ENTER para continuar...");
		Scanner teclado = new Scanner(System.in);
		teclado.nextLine();
		teclado.reset();
	}

}
