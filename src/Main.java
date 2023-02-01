import modulos.RegistroEstudiantes;
import modulos.UtilidadesGenerales;
import modulos.VentaProductos;
import modulos.restaurante.Restaurante;

public class Main {

    static UtilidadesGenerales teclado = new UtilidadesGenerales();

    private static void MensajeDespedida() {
        System.out.println("Nos vemos pronto!");
    }

    public static void Menu() {
        while(true) {
            System.out.println("\t\t### PROYECTO FINAL ###\n");
            System.out.println("1 -> Asistente de restaurante.");
            System.out.println("2 -> Aplicación para registrar la venta de productos a un cliente.");
            System.out.println("3 -> Programa que permita almacenar los registros de varios estudiantes en un archivo.");
            System.out.println("4 -> (Por definir)");
            System.out.println("5 -> Salir");
            System.out.print("Ingrese su opción >> ");
            int opc = teclado.LeerNumeroIntervalo(1, 5);
            switch (opc) {
                case 1:
                    Restaurante modulo1 = new Restaurante();
                    modulo1.Menu();
                    break;
                case 2:
                    VentaProductos modulo2 = new VentaProductos();
                    break;
                case 3:
                    RegistroEstudiantes modulo3 = new RegistroEstudiantes();
                    break;
                case 4:
                    break;
                default:
                    MensajeDespedida();
                    return;
            }
        }
    }

    public static void main(String[] args) {
        Menu();
    }
}