package modulos;

import static modulos.Consola.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VentaProductos {

    double[] descuentos = new double[]{0.07, 0.05, 0.06};

    // datos cliente
    private String cedula;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;

    // datos productos
    private final List<String> codigo = new ArrayList<>();
    private final List<String> producto = new ArrayList<>();
    private final List<Integer> tipo = new ArrayList<>();
    private final List<Integer> stock = new ArrayList<>();
    private final List<Double> precioNormal = new ArrayList<>();
    private final List<Double> precioAlPorMayor = new ArrayList<>();

    // datos productos a comprar

    private final List<Integer> ids = new ArrayList<>();
    private final List<Integer> cantidades = new ArrayList<>();
    private final List<Double> subtotal = new ArrayList<>();
    private final List<Double> descuento = new ArrayList<>();

    double iva = 0.12;

    public void Menu() {
        CargarDatosProductos();
        SolicitarInformacion();
        MostrarProductos();
        SeleccionarProductos();
        SeleccionarFactura();
    }

    private int ObtenerProducto(String codigo) {
        int n = producto.size();
        for (int i = 0; i < n; i++) {
            String codigoProducto = this.codigo.get(i);
            if (codigo.equals(codigoProducto)) {
                return i;
            }
        }
        return -1;
    }

    private void SeleccionarProductos() {
        int opc = 1;
        while (true) {
            if (opc == 1) {
                String codigo;
                int id = Integer.MAX_VALUE;
                do {
                    if (id == -1) Imprimir("Codigo no válido, Inténtelo de nuevo");
                    else Imprimir("Ingrese código");
                    codigo = LeerCadena();
                    id = ObtenerProducto(codigo);
                } while (id == -1);
                Imprimir("Ingrese la cantidad");
                int max = stock.get(id);
                int cantidad = LeerNumeroEnIntervalo(0, max);
                ids.add(id);
                cantidades.add(cantidad);
            }
            else if (opc == 2) {
                MostrarProductos(ids, cantidades);
            }
            else {
                AplicarDescuento();
                return;
            }
            SaltarLinea();
            ImprimirLinea("Qué desea hacer?");
            ImprimirLinea("1 -> Añadir otro producto");
            ImprimirLinea("2 -> Revisar lista productos");
            ImprimirLinea("3 -> Regresar");
            Imprimir("Ingrese su opción");
            opc = LeerNumeroEnIntervalo(1, 3);
        }
    }

    private void SeleccionarFactura() {
        SaltarLinea();
        ImprimirLinea("Seleccione el tipo de factura");
        ImprimirLinea("1 -> Física");
        ImprimirLinea("2 -> Electrónica");
        Imprimir("Ingrese su opción");
        int opc = LeerNumeroEnIntervalo(1, 2);
        if (opc == 1) {
            ImprimirFactura();
        } else {
            EnviarFactura();
        }
    }

    private void ImprimirFactura () {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyyMMdd");
        String nombreArchivo = cedula + "-" + LocalDate.now().format(formatoFecha);
        try {
            PrintWriter escritor = new PrintWriter(nombreArchivo + ".txt");
            escritor.println("----------------------------DETALLE----------------------------");
            String formato = "| %-20s | %-10s | %-10s | %-10s |";
            escritor.printf(formato, "NOMBRE", "CANTIDAD", "PRECIO", "SUBTOTAL");
            double total = 0;
            double descuento = 0;
            for (int i = 0; i < ids.size(); i++) {
                int id = ids.get(i);
                String nombre = producto.get(id);
                int cantidad =  cantidades.get(i);
                double precio = cantidad >= 6 ?  precioAlPorMayor.get(id) :  precioNormal.get(id);
                double subtotal = cantidad * precio;
                total += subtotal;
                escritor.printf(formato, nombre, cantidad, precio, subtotal);
            }
             escritor.println("---------------------------------------------------------------");
             escritor.println("Total: " + total);
             escritor.println("Descuento: " + descuento);
             escritor.println("IVA: "  + (100*iva));
             escritor.println("---------------------------------------------------------------");
            escritor.close();
            ImprimirLinea("Factura '" + nombreArchivo + "' impreso con éxito.");
        } catch (Exception e) {
            ImprimirLinea("Error al imprimir factura '" + nombreArchivo + "'.");
        }
    }

    private void EnviarFactura() {
        SaltarLinea();
        ImprimirLinea("--------------------------------FACTURA-------------------------------");
        ImprimirLinea("Cédula:" + cedula);
        ImprimirLinea("Nombre y apellido: " + nombre + " " + apellido);
        ImprimirLinea("Numero teléfono: " + telefono);
        ImprimirLinea("Dirección de correo: " + correo);
        ImprimirLinea("Fecha: " + LocalDate.now());
        MostrarProductos(ids, cantidades);
    }

    private void AplicarDescuento() {
        int n = ids.size();
        for (int i = 0; i < n; i++) {
            int id = ids.get(i);
            double descuento;
            double subTotal;
            double precio = precioNormal.get(id);
            int cantidad = cantidades.get(id);
            if (cantidad < 6) {
                descuento = 0;
                subTotal = precio * cantidad;
            }
            else {
                descuento = descuentos[tipo.get(id)];
                subTotal = precio * cantidad * (1 - descuento);
            }
            this.descuento.add(descuento);
            this.subtotal.add(subTotal);
        }
    }

    private void MostrarProductos(List<Integer> ids, List<Integer> cantidades) {
        SaltarLinea();
        ImprimirLinea("----------------------------DETALLE----------------------------");
        String formato = "| %-20s | %-10s | %-10s | %-10s |";
        printf(formato, "NOMBRE", "CANTIDAD", "PRECIO", "SUBTOTAL");
        double total = 0;
        double descuento = 0;
        for (int i = 0; i < ids.size(); i++) {
            int id = ids.get(i);
            String nombre = producto.get(id);
            int cantidad =  cantidades.get(i);
            double precio = cantidad >= 6 ?  precioAlPorMayor.get(id) :  precioNormal.get(id);
            double subtotal = cantidad * precio;
            total += subtotal;
            printf(formato, nombre, cantidad, precio, subtotal);
        }
        ImprimirLinea("---------------------------------------------------------------");
        ImprimirLinea("Total: " + total);
        ImprimirLinea("Descuento: " + descuento);
        ImprimirLinea("IVA: "  + (100*iva));
        ImprimirLinea("---------------------------------------------------------------");
        SaltarLinea();
    }

    private void MostrarProductos() {
        SaltarLinea();
        ImprimirLinea("-----------------------------------------------INVENTARIO-----------------------------------------------");
        String formato = "| %-10s | %-20s | %-5s | %-10s | %-15s | %-25s |";
        printf(formato, "CODIGO", "NOMBRE", "TIPO", "STOCK", "PRECIO NORMAL", "PRECIO AL POR MAYOR");
        for (int i = 0; i < producto.size(); i++) {
            String codigoProducto = codigo.get(i);
            String nombreProducto = producto.get(i);
            int tipoProducto = tipo.get(i);
            int stockProducto = stock.get(i);
            double precioNormalProducto = precioNormal.get(i);
            double precioAlPorMayorProducto = precioAlPorMayor.get(i);
            printf(formato,
              codigoProducto,
              nombreProducto,
              tipoProducto,
              stockProducto,
              precioNormalProducto,
              precioAlPorMayorProducto
            );
        }
        ImprimirLinea("--------------------------------------------------------------------------------------------------------");
        SaltarLinea();
    }

    private void SolicitarInformacion() {
        SaltarLinea();
        ImprimirLinea("Ingrese la información del cliente");
        SaltarLinea();
        Imprimir("Cedula");
        cedula = LeerCadena();
        Imprimir("Nombre");
        nombre = LeerCadena();
        Imprimir("Apellido");
        apellido = LeerCadena();
        Imprimir("Teléfono");
        telefono = LeerCadena();
        Imprimir("Correo electrónico");
        correo = LeerCadena();
    }

    private void CargarDatosProductos() {
        String nombreArchivo = "src/modulos/productos.txt";
        try {
            Scanner sc = new Scanner(new File(nombreArchivo));
            while (sc.hasNextLine()) {
                String[] datos = sc.nextLine().split(";");
                codigo.add(datos[0]);
                producto.add(datos[1]);
                tipo.add(Integer.parseInt(datos[2]));
                stock.add(Integer.parseInt(datos[3]));
                precioNormal.add(Double.parseDouble(datos[4]));
                precioAlPorMayor.add(Double.parseDouble(datos[5]));
            }
            sc.reset();
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
