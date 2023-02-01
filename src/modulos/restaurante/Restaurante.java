package modulos.restaurante;

import modulos.UtilidadesGenerales;

import java.util.ArrayList;
import java.util.List;

public class Restaurante {

    List<Ingrediente> ingredientes = new ArrayList<>();
    List<Plato> platos = new ArrayList<>();
    List<Pedido> pedidos = new ArrayList<>();
    UtilidadesGenerales teclado = new UtilidadesGenerales();

    private void AnadirIngrediente() {
        System.out.println("\n\t\t\t### REGISTRO DE INGREDIENTES ###");
        int opc = 1;
        while (true) {
            if (opc == 1) {
                int id = ingredientes.size() + 1;
                System.out.print("Nombre >> ");
                String nombre = teclado.LeerCadena();
                System.out.print("Cantidad >> ");
                int cantidad = teclado.LeerNumeroMayorQue(0);
                ingredientes.add(new Ingrediente(id, nombre, cantidad));
            }
            if (opc == 2) {
                MostrarIngredientes();
            }
            if (opc == 3)
                return;
            System.out.println("Qué desea hacer?");
            System.out.println("1 -> Registrar otro ingrediente");
            System.out.println("2 -> Revisar lista de ingredientes");
            System.out.println("3 -> Regresar");
            System.out.print("Ingrese su opción >> ");
            opc = teclado.LeerNumeroIntervalo(1, 3);
        }
    }

    private void MostrarIngredientes() {
        System.out.println("\n\t\t\t### REGISTRO DE INGREDIENTES ###");
        int n = ingredientes.size();
        if (n==0) {
            System.out.println("No hay ingredientes registrados");
            return;
        }
        System.out.println("ID \t | NOMBRE \t| CANTIDAD |");
        for (int i = 0; i < n; i++) {
            Ingrediente ingrediente = ingredientes.get(i);
            System.out.println((i + 1) + " \t | " + ingrediente.getNombre() + " \t | " + ingrediente.getCantidad());
        }
        System.out.println("\t\t\t| FIN LISTA |\n");
    }

    private void MostrarIngredientes(Plato plato) {
        System.out.println("\n\t\t\t### REGISTRO DE INGREDIENTES DEL PLATO " + plato.getNombre() +" ###");
        List<Ingrediente> ingredientes = plato.getIngredientes();
        int n = ingredientes.size();
        System.out.println("\t| NOMBRE -> CANTIDAD |");
        for (int i = 0; i < n; i++) {
            Ingrediente ingrediente = ingredientes.get(i);
            System.out.println((i + 1) + " " + ingrediente.getNombre() + " -> " + ingrediente.getCantidad());
        }
        System.out.println("\t| FIN LISTA |");
    }

    private void AnadirPlato(){
        if (ingredientes.size()==0) {
            System.out.println("\nRegistre ingredientes antes de registrar un plato");
            return;
        }
        System.out.println("\n\t\t\t### REGISTRO DE PLATO ###\n");
        int opc = 1;
        Plato plato = new Plato();
        while (true) {
            if (opc == 1) {
                System.out.print("Nombre del platillo >> ");
                String nombre = teclado.LeerCadena();
                plato.setNombre(nombre);
                MostrarIngredientes();
                System.out.print("Ingrese el id del ingrediente >> ");
                int id = teclado.LeerNumeroIntervalo(0, ingredientes.size()) - 1;
                Ingrediente ingrediente = ingredientes.get(id);
                System.out.print("Ingrese la cantidad >> ");
                int cantidad = teclado.LeerNumeroMayorQue(0);
                ingrediente.setCantidad(cantidad);
                plato.addIngrediente(ingrediente);
                platos.add(plato);
            }
            System.out.println("Qué desea hacer?");
            System.out.println("1 -> Registrar otro ingrediente al plato");
            System.out.println("2 -> Revisar lista de ingredientes del plato");
            System.out.println("3 -> Regresar");
            System.out.print("Ingrese su opción >> ");
            opc = teclado.LeerNumeroIntervalo(1, 3);
            if (opc == 2)
                MostrarIngredientes(plato);
            if (opc == 3)
                return;
        }
    }

    private void MostarPlatos() {
        System.out.println("\n\t\t\t### REGISTRO DE PLATOS ###");
        int n = platos.size();
        if (n==0) {
            System.out.println("No hay platos registrados");
            return;
        }
        System.out.println("\t| ID -> NOMBRE |");
        for (int i = 0; i < n; i++) {
            Plato plato = platos.get(i);
            System.out.println((i + 1) + " -> " + plato.getNombre());
        }
        System.out.println("\t| FIN LISTA |");
    }

    private boolean VerificarIngredientes(Plato plato) {
       List<Ingrediente> ingredientes = plato.getIngredientes();
       for (int i = 0; i < ingredientes.size(); i++) {
           int id = ingredientes.get(i).getId() - 1;
           if (ingredientes.get(i).getCantidad() > this.ingredientes.get(id).getCantidad())
               return false;
       }
       return true;
    }

    private void SeleccionarPlatillos() {
        int max = this.platos.size();
       if (max == 1) {
           int id = max + 1;
           System.out.print("Ingrese su opción >> ");
           int opc = teclado.LeerNumeroIntervalo(1, max);
           Plato plato = this.platos.get(opc - 1);
           if (VerificarIngredientes(plato)) {
               Pedido pedido = new Pedido(id, plato);
               pedidos.add(pedido);
           } else {
               System.out.println("No hay suficientes ingredientes para este plato.");
           }
           System.out.println("Qué desea hacer?");
           System.out.println("1 -> Registrar otro pedido");
           System.out.println("2 -> Regresar");
           System.out.print("Ingrese su opción >> ");
           opc = teclado.LeerNumeroIntervalo(1, 2);
       } else {
           System.out.print("Registre un plato antes.");
       }
    }

    private void AnadirPedido() {
        MostarPlatos();
        SeleccionarPlatillos();
    }

    private void MostarPedidos() {
        System.out.println("\n\t\t\t### REGISTRO DE PEDIDOS ###");
        int n = pedidos.size();
        System.out.println("ID PEDIDO \t | ID PLATO \t | NOMBRE PLATO |");
        for (int i = 0; i < n; i++) {
            Plato plato = pedidos.get(i).getPlato();
            System.out.println((i + 1) + " \t | " + (plato.getId()+1) + " \t | " + plato.getNombre());
        }
        System.out.println("\t\t\t| FIN LISTA |\n");
    }

    public void Menu() {
        while (true) {
            System.out.println("\n\t\t### ASISTENTE DE RESTAURANTE ###\n");
            System.out.println("1 -> Registrar ingrediente.");
            System.out.println("2 -> Registrar plato.");
            System.out.println("3 -> Realizar pedido.");
            System.out.println("4 -> Revisar registro de pedidos.");
            System.out.println("5 -> Regresar");
            System.out.print("Ingrese su opción >> ");
            int opc = teclado.LeerNumeroIntervalo(1, 5);
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