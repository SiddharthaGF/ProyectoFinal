package modulos;

import java.util.ArrayList;
import java.util.List;

import static modulos.Consola.*;

public class Restaurante {


	// Listas para ingredientes
	List<String> nombresIngrediente = new ArrayList<>();
	List<Integer> cantidadIngrediente = new ArrayList<>();

	// Listas para platos
	List<String> nombresPlatos = new ArrayList<>();
	List<List<Integer>> idIngredientes = new ArrayList<>();
	List<List<Integer>> cantidadIngredientes = new ArrayList<>();

	//Listas para pedidos
	List<String> pedidos = new ArrayList<>();

	private void AnadirIngrediente() {
		SaltarLinea();
		ImprimirLinea("--------------------------------REGISTRO DE PLATO---------------------------------");
		int opc = 1;
		while (true) {
			if (opc == 1) {
				ImprimirLinea("Ingreso de datos del ingrediente");
				Imprimir("Nombre");
				String nombre = LeerCadena();
				Imprimir("Cantidad");
				int cantidad = LeerNumeroMayorQue(0);
				nombresIngrediente.add(nombre);
				cantidadIngrediente.add(cantidad);
			}
			if (opc == 2) {
				MostrarIngredientes();
			}
			if (opc == 3) {
				SaltarLinea();
				return;
			}
			SaltarLinea();
			ImprimirLinea("Qué desea hacer?");
			ImprimirLinea("1 -> Registrar otro ingrediente");
			ImprimirLinea("2 -> Revisar lista de ingredientes");
			ImprimirLinea("3 -> Regresar");
			Imprimir("Ingrese su opción");
			opc = LeerNumeroEnIntervalo(1, 3);
		}
	}

	private void MostrarIngredientes() {
		SaltarLinea();
		ImprimirLinea("-----------------------------INGREDIENTES REGISTRADOS-----------------------------");
		SaltarLinea();
		ImprimirLinea("---------------LISTA DE INGREDIENTES--------------");
		int n = nombresIngrediente.size();
		if (n == 0) {
			SaltarLinea();
			ImprimirLinea("No hay ingredientes registrados");
			SaltarLinea();
			return;
		}
		String formato = "| %-5s | %-25s | %-10s |";
		printf(formato, "ID", "NOMBRE", "CANTIDAD");
		ImprimirLinea("--------------------------------------------------");
		for (int i = 0; i < n; i++) {
			int id = i + 1;
			String nombre = nombresIngrediente.get(i);
			int cantidad = cantidadIngrediente.get(i);
			printf(formato, id, nombre, cantidad);
		}
		ImprimirLinea("--------------------------------------------------");
		SaltarLinea();
	}

	private void MostrarIngredientes(String nombre, List<Integer> ids, List<Integer> cantidades) {
		ImprimirLinea("------------------------REGISTRO DE INGREDIENTES DEL PLATO------------------------");
		ImprimirLinea("Nombre del plato: " + nombre);
		SaltarLinea();
		ImprimirLinea("------------------------------------------");
		String formato = "| %-25s | %-10s |";
		printf(formato, "NOMBRE", "CANTIDAD");
		ImprimirLinea("------------------------------------------");
		for (int i = 0; i < ids.size(); i++) {
			String nombreIngrediente = nombresIngrediente.get(ids.get(i));
			printf(formato, nombreIngrediente, cantidades.get(i));
		}
		ImprimirLinea("------------------------------------------");
	}

	private void AnadirPlato() {
		SaltarLinea();
		ImprimirLinea("--------------------------------REGISTRO DE PLATO---------------------------------");
		int numeroIngredientes = nombresIngrediente.size();
		if (numeroIngredientes == 0) {
			SaltarLinea();
			ImprimirLinea("Registre ingredientes antes de registrar un plato");
			EsperarTecla();
			SaltarLinea();
			return;
		}
		ImprimirLinea("Ingreso de datos del plato");
		Imprimir("Nombre del platillo");
		String nombre = LeerCadena();
		List<Integer> ids = new ArrayList<>();
		List<Integer> cantidades = new ArrayList<>();
		MostrarIngredientes();
		int opc = 1;
		while (true) {
			if (opc == 1) {
				Imprimir("Ingrese el ID del ingrediente");
				int idIngrediente = LeerNumeroEnIntervalo(0, numeroIngredientes) - 1;
				Imprimir("Ingrese la cantidad");
				int cantidad = LeerNumeroMayorQue(0);
				ids.add(idIngrediente);
				cantidades.add(cantidad);
			}
			SaltarLinea();
			ImprimirLinea("Qué desea hacer?");
			ImprimirLinea("1 -> Registrar otro ingrediente al plato");
			ImprimirLinea("2 -> Revisar lista de ingredientes del plato");
			ImprimirLinea("3 -> Guardar y regresar");
			Imprimir("Ingrese su opción");
			opc = LeerNumeroEnIntervalo(1, 3);
			SaltarLinea();
			if (opc == 2)
				MostrarIngredientes(nombre, ids, cantidades);
			if (opc == 3) {
				nombresPlatos.add(nombre);
				idIngredientes.add(ids);
				cantidadIngredientes.add(cantidades);
				return;
			}
		}
	}

	private void MostarPlatos() {
		ImprimirLinea("-------------------------------REGISTRO DE PLATOS---------------------------------");
		int n = nombresPlatos.size();
		if (n == 0) {
			SaltarLinea();
			ImprimirLinea("No hay platos registrados");
			EsperarTecla();
			SaltarLinea();
			return;
		}
		SaltarLinea();
		ImprimirLinea("----------------PLATOS---------------");
		String formato = "| %-5s | %-25s |";
		printf(formato, "ID", "NOMBRE");
		ImprimirLinea("-------------------------------------");
		for (int i = 0; i < n; i++) {
			int id = i + 1;
			String nombre = nombresPlatos.get(i);
			printf(formato, id, nombre);
		}
		ImprimirLinea("-------------------------------------");
		SaltarLinea();
	}

	private boolean VerificarIngredientes(List<Integer> ids, List<Integer> cantidades) {
		for (int i = 0; i < ids.size(); i++) {
			int cantidad = cantidadIngrediente.get(ids.get(i));
			if (cantidades.get(i) > cantidad)
				return false;
		}
		return true;
	}

	private void SeleccionarPlatillos() {
		while (true) {
			int cantidadPlatos = nombresPlatos.size();
			if (cantidadPlatos == 0) {
				return;
			}
			Imprimir("Ingrese su opción");
			int idPlato = LeerNumeroEnIntervalo(1, cantidadPlatos) - 1;
			List<Integer> ids = idIngredientes.get(idPlato);
			List<Integer> cantidad = cantidadIngredientes.get(idPlato);
			String nombre = nombresPlatos.get(idPlato);
			if (VerificarIngredientes(ids, cantidad)) {
				RestarIngredientes(ids, cantidad);
				pedidos.add(nombre);
			} else {
				ImprimirLinea("No hay suficientes ingredientes para este plato.");
			}
			ImprimirLinea("Qué desea hacer?");
			ImprimirLinea("1 -> Registrar otro pedido");
			ImprimirLinea("2 -> Regresar");
			Imprimir("Ingrese su opción");
			int opc = LeerNumeroEnIntervalo(1, 2);
			if (opc == 2) {
				return;
			}
		}
	}

	private void RestarIngredientes(List<Integer> ids, List<Integer> cantidades) {
		for (int i = 0; i < ids.size(); i++) {
			int id = ids.get(i);
			int cantidad = cantidadIngrediente.get(id);
			cantidadIngrediente.set(id, cantidad - cantidades.get(i));
		}
	}

	private void AnadirPedido() {
		MostarPlatos();
		SeleccionarPlatillos();
	}

	private void MostarPedidos() {
		ImprimirLinea("-------------------------------REGISTRO DE PEDIDOS--------------------------------");
		int n = pedidos.size();
		SaltarLinea();
		ImprimirLinea("----------------PLATOS---------------");
		String formato = "| %-5s | %-25s |";
		printf(formato, "ID", "NOMBRE PLATILLO");
		ImprimirLinea("-------------------------------------");
		for (int i = 0; i < n; i++) {
			int id = (i + 1);
			String nombre = pedidos.get(i);
			printf(formato, id, nombre);
		}
		ImprimirLinea("-------------------------------------");
	}

	public void Menu() {
		while (true) {
			ImprimirLinea("-----------------------------ASISTENTE DE RESTAURANTE------------------------------");
			ImprimirLinea("1 -> Registrar ingrediente.");
			ImprimirLinea("2 -> Registrar plato.");
			ImprimirLinea("3 -> Realizar pedido.");
			ImprimirLinea("4 -> Revisar registro de pedidos.");
			ImprimirLinea("5 -> Regresar");
			Imprimir("Ingrese su opción");
			int opc = LeerNumeroEnIntervalo(1, 5);
			switch (opc) {
				case 1:
					AnadirIngrediente();
					break;
				case 2:
					AnadirPlato();
					break;
				case 3:
					AnadirPedido();
					break;
				case 4:
					MostarPedidos();
					break;
				default:
					return;
			}
		}
	}
}