package modulos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static modulos.Consola.*;

public class Banco {

	// Listas para usuario
	private List<String> cedula = new ArrayList<>();
	private List<String> nombre = new ArrayList<>();
	private List<String> apellido = new ArrayList<>();
	private List<String> telefono = new ArrayList<>();
	private List<String> correo = new ArrayList<>();

	// Listas cuentas
	private List<String> numerosCuenta = new ArrayList<>();
	private List<String> cedulasCuenta = new ArrayList<>();
	private List<Double> saldosCuenta = new ArrayList<>();

	// Listas para transferencias
	private List<String> tipoTransaccion = Arrays.asList("Deposito", "Retiro", "Transferencia");
	private List<List<String>> transacciones = new ArrayList<>();

	public void Menu() {
		while (true) {
			ImprimirLinea("--------------------------------ASISTENTE DE BANCO---------------------------------");
			ImprimirLinea("1 -> Registrar cliente.");
			ImprimirLinea("2 -> Registrar cuenta para cliente.");
			ImprimirLinea("3 -> Realizar una transacción.");
			ImprimirLinea("4 -> Regresar");
			Imprimir("Ingrese su opción");
			int opc = LeerNumeroEnIntervalo(1, 5);
			switch (opc) {
				case 1:
					RegistrarCliente();
					break;
				case 2:
					CrearCuenta();
					break;
				case 3:
					RealizarTransaccion();
					break;
				default:
					return;
			}
		}
	}

	private void RealizarTransferencia() {
		String numeroTransaccion = String.valueOf(transacciones.size() + 1);
		numeroTransaccion = String.format("%0" + 5 + "d", Integer.valueOf(numeroTransaccion));
		MostrarCuentas();
		Imprimir("Número de cuenta origen");
		String cuentaOrigen = LeerCadenaConOpcion(this.numerosCuenta);
		Imprimir("Número de cuenta destino");
		String cuentaDestino;
		while (true) {
			cuentaDestino = LeerCadenaConOpcion(this.numerosCuenta);
			if (!cuentaOrigen.equals(cuentaDestino)) {
				break;
			}
			ImprimirLinea("La cuenta de destino debe ser diferente de la cuenta de origen");
		}
		int indexOrigen = this.numerosCuenta.indexOf(cuentaOrigen);
		int indexDestino = this.numerosCuenta.indexOf(cuentaDestino);
		double saldoOrigen = this.saldosCuenta.get(indexOrigen);
		double saldoDestino = this.saldosCuenta.get(indexDestino);
		Imprimir("Monto retiro (máximo " + saldoOrigen + ")");
		double monto = LeerNumeroEnIntervalo(1, saldoOrigen);
		saldosCuenta.set(indexOrigen, saldoOrigen - monto);
		saldosCuenta.set(indexDestino, saldoDestino + monto);
		transacciones.add(Arrays.asList(numeroTransaccion, cuentaOrigen, tipoTransaccion.get(2) + "-" + String.valueOf(monto), cuentaDestino));
	}

	private void RealizarTransaccion() {
		ImprimirLinea("--------------------------------TRANSACCIONES---------------------------------");
		if (numerosCuenta.size() == 0) {
			ImprimirLinea("No hay cuentas registrados.");
			EsperarTecla();
			SaltarLinea();
			return;
		}
		int opc = 0;
		while (true) {
			switch (opc) {
				case 1:
					RealizarDeposito();
					break;
				case 2:
					RealizarRetiro();
					break;
				case 3:
					RealizarTransferencia();
					break;
				case 4:
					MostrarTransacciones();
					break;
				case 5:
					SaltarLinea();
					return;
			}
			ImprimirLinea("1 -> Realizar deposito.");
			ImprimirLinea("2 -> Realizar retiro");
			ImprimirLinea("3 -> Transferir dinero.");
			ImprimirLinea("4 -> Ver transacciones");
			ImprimirLinea("5 -> Regresar.");
			Imprimir("Ingrese su opción");
			opc = LeerNumeroEnIntervalo(1, 5);
		}
	}

	private void MostrarTransacciones() {
		ImprimirLinea("-----------------------------REGISTRO DE TRANSACCIONES-----------------------------");
		Imprimir("Numero de cedula");
		String cedula = LeerCadenaConOpcion(this.cedula);
		int n = this.cedulasCuenta.size();
		ImprimirLinea("----------------------------TRANSACCIONES----------------------------");
		String formato = "| %-5s | %-5s | %-10s | %-10s | %-10s |";
		printf(formato, "ID", "ORIGEN", "TIPO", "VALOR", "DESTINO");
		ImprimirLinea("---------------------------------------------------------------------");
		for (int i = 0; i < n; i ++) {
			if (cedula.equals(this.cedulasCuenta.get(i))) {
				String cuenta = numerosCuenta.get(i);
				for (int j = 0; j < this.transacciones.size(); j++) {
					List<String> transaccion = this.transacciones.get(j);
					if (transaccion.get(1).equals(cuenta)) {
						String id = transaccion.get(0);
						String origen = transaccion.get(1);
						String tipo = transaccion.get(2);
						String valor = transaccion.get(3);
						String destino = transaccion.get(4);
						printf(formato, id, origen, tipo, valor, destino);
					}
				}
			}
		}
		ImprimirLinea("---------------------------------------------------------------------");
	}

	private void RealizarDeposito() {
		String numeroTransaccion = String.valueOf(transacciones.size() + 1);
		numeroTransaccion = String.format("%0" + 5 + "d", Integer.valueOf(numeroTransaccion));
		MostrarCuentas();
		Imprimir("Número de cuenta");
		String cuenta = LeerCadenaConOpcion(this.numerosCuenta);
		int index = this.numerosCuenta.indexOf(cuenta);
		double saldo = this.saldosCuenta.get(index);
		Imprimir("Monto deposito");
		double deposito = LeerNumeroMayorQue(0);
		saldosCuenta.set(index, saldo + deposito);
		transacciones.add(Arrays.asList(numeroTransaccion, cuenta, tipoTransaccion.get(0), "+" + String.valueOf(deposito), "-"));
	}

	private void RealizarRetiro() {
		String numeroTransaccion = String.valueOf(transacciones.size() + 1);
		numeroTransaccion = String.format("%0" + 5 + "d", Integer.valueOf(numeroTransaccion));
		MostrarCuentas();
		Imprimir("Número de cuenta");
		String cuenta = LeerCadenaConOpcion(this.numerosCuenta);
		int index = this.numerosCuenta.indexOf(cuenta);
		double saldo = this.saldosCuenta.get(index);
		Imprimir("Monto retiro (máximo " + saldo + ")");
		double retiro = LeerNumeroEnIntervalo(1, saldo);
		saldosCuenta.set(index, saldo - retiro);
		transacciones.add(Arrays.asList(numeroTransaccion, cuenta, tipoTransaccion.get(1), "-" + String.valueOf(retiro), "-"));
	}

	private void MostrarCuentas() {
		int n = cedulasCuenta.size();
		SaltarLinea();
		ImprimirLinea("------------------------------------CUENTAS-------------------------------------");
		String formato = "| %-10s | %-40s | %-10s | %-10s |";
		printf(formato, "CUENTA", "PROPIETARIO", "CEDULA", "SALDO");
		ImprimirLinea("--------------------------------------------------------------------------------");
		for (int i = 0; i < n; i++) {
			String numeroCuenta = numerosCuenta.get(i);
			String cedula = cedulasCuenta.get(i);
			int aux = cedulasCuenta.indexOf(cedula);
			String nombre = this.nombre.get(aux);
			String apellido = this.apellido.get(aux);
			double saldo = this.saldosCuenta.get(aux);
			printf(formato, numeroCuenta, nombre + " " + apellido, cedula, saldo);
		}
		ImprimirLinea("--------------------------------------------------------------------------------");
	}

	private void CrearCuenta() {
		SaltarLinea();
		ImprimirLinea("---------------------------------CREAR DE CLIENTES---------------------------------");
		int opc = 1;
		while(true) {
			switch (opc) {
				case 1:
					if (cedula.size() == 0) {
						ImprimirLinea("No hay clientes registrados.");
						EsperarTecla();
						SaltarLinea();
						return;
					}
					String numeroCuenta = String.valueOf(numerosCuenta.size() + 1);
					numeroCuenta = String.format("%0" + 5 + "d", Integer.valueOf(numeroCuenta));
					ImprimirLinea("Numero de cuenta que se va asignar: " + numeroCuenta);
					Imprimir("Número de cedula del cliente");
					String cedula = LeerCadena();
					Imprimir("Monto inicial (cantidades mayores a 0)");
					double saldo = LeerNumeroMayorQue(0);
					numerosCuenta.add(numeroCuenta);
					cedulasCuenta.add(cedula);
					saldosCuenta.add(saldo);
					break;
				case 2:
					MostrarCuentas();
					break;
				case 3:
					SaltarLinea();
					return;
			}
			ImprimirLinea("Qué desea hacer?");
			ImprimirLinea("1 -> Crear otra cuenta.");
			ImprimirLinea("2 -> Ver todas las cuentas registradas.");
			ImprimirLinea("3 -> Regresar.");
			Imprimir("Ingrese su opción");
			opc = LeerNumeroEnIntervalo(1, 3);
		}
	}

	private void RegistrarCliente() {
		SaltarLinea();
		ImprimirLinea("--------------------------------INGRESO DE CLIENTES--------------------------------");
		int opc = 1;
		while(true) {
			switch (opc) {
				case 1:
					ImprimirLinea("Ingreso de datos del cliente");
					Imprimir("Cédula");
					String cedula = LeerCadena();
					Imprimir("Nombre");
					String nombre = LeerCadena();
					Imprimir("Apellido");
					String apellido = LeerCadena();
					Imprimir("Telefono");
					String telefono = LeerCadena();
					Imprimir("Correo");
					String correo = LeerCadena();
					SaltarLinea();
					this.cedula.add(cedula);
					this.nombre.add(nombre);
					this.apellido.add(apellido);
					this.telefono.add(telefono);
					this.correo.add(correo);
					break;
				case 2:
					MostrarClientes();
					break;
				case 3:
					SaltarLinea();
					return;
			}
			ImprimirLinea("Qué desea hacer?");
			ImprimirLinea("1 -> Registrar otro cliente.");
			ImprimirLinea("2 -> Ver todos los clientes registrados.");
			ImprimirLinea("3 -> Regresar.");
			Imprimir("Ingrese su opción");
			opc = LeerNumeroEnIntervalo(1, 3);
		}
	}

	private void MostrarClientes() {
		int n = this.cedula.size();
		SaltarLinea();
		ImprimirLinea("----------------------------------------------------CLIENTES-----------------------------------------------------");
		String formato = "| %-10s | %-40s | %-10s | %-40s |";
		printf(formato, "CEDULA", "NOMBRE Y APELLIDO", "TELEFONO", "CORREO");
		ImprimirLinea("-----------------------------------------------------------------------------------------------------------------");
		for (int i = 0; i < n; i++) {
			String cedula = this.cedula.get(i);
			String nombre = this.nombre.get(i);
			String apellido = this.apellido.get(i);
			String telefono = this.telefono.get(i);
			String correo = this.correo.get(i);
			printf(formato, cedula, nombre + " " + apellido, telefono, correo);
		}
		ImprimirLinea("-----------------------------------------------------------------------------------------------------------------");
	}

}
