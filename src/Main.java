import modulos.Banco;
import modulos.Registro;
import modulos.VentaProductos;
import modulos.Restaurante;

import static modulos.Consola.*;

public class Main {

    private static void MensajeDespedida() {
        System.out.println("Nos vemos pronto!");
    }

    public static void main(String[] args) {
        while(true) {
            ImprimirLinea("----------------------------------PROYECTO FINAL----------------------------------");
            SaltarLinea();
            ImprimirLinea("1 -> Asistente de restaurante.");
            ImprimirLinea("2 -> Sistema de ventas");
            ImprimirLinea("3 -> Registro de estudiantes.");
            ImprimirLinea("4 -> Sistema de banco");
            ImprimirLinea("5 -> Salir");
            Imprimir("Ingrese su opción");
            int opc = LeerNumeroEnIntervalo(1, 5);
            SaltarLinea();
            switch (opc) {
                case 1:
                    Restaurante modulo1 = new Restaurante();
                    modulo1.Menu();
                    break;
                case 2:
                    ImprimirLinea("--------------------------------SISTEMA DE VENTAS---------------------------------");
                    VentaProductos modulo2 = new VentaProductos();
                    modulo2.Menu();
                    break;
                case 3:
                    ImprimirLinea("-----------------------------REGISTRO DE ESTUDIANTES------------------------------");
                    Registro modulo3 = new Registro();
                    modulo3.principal();
                    break;
                case 4:
                    Banco modulo4 = new Banco();
                    modulo4.Menu();
                    break;
                default:
                    MensajeDespedida();
                    return;
            }
        }
    }
}