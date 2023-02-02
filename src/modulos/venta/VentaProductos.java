package modulos.venta;

import static modulos.Consola.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VentaProductos {

    Cliente cliente;
    List<Producto> productos = new ArrayList<>();
    List<ProductoAComprar> productosAComprar = new ArrayList<>();
    double[] descuentos = new double[]{0.25, 0.15, 0.10};

    public void Menu() {
        CargarDatosProductos();
        SolicitarInformacion();
        MostrarProductos();
        SeleccionarProductos();
        SeleccionarFactura();
    }

    private Producto ObtenerProducto(String codigo) {
        int n = productos.size();
        for (int i = 0; i < n; i++) {
            Producto producto = productos.get(i);
            if (producto.getCodigo().equals(codigo)) return producto;
        }
        return null;
    }

    private void SeleccionarProductos() {
        int opc = 1;
        int cantidadTotal = 0;
        while (true) {
            if (opc == 1) {
                String codigo;
                Producto producto = new Producto();
                do {
                    if (producto == null) Imprimir("Codigo no válido, Inténtelo de nuevo");
                    else Imprimir("Ingrese código");
                    codigo = LeerCadena();
                    producto = ObtenerProducto(codigo);
                } while (producto == null);
                Imprimir("Ingrese la cantidad");
                int max = producto.getStock();
                int cantidad = LeerNumeroIntervalo(0, max);
                cantidadTotal += cantidad;
                productosAComprar.add(new ProductoAComprar(producto, cantidad));
            }
            else if (opc == 2) {
                MostrarProductos(productosAComprar);
            }
            else {
                AplicarDescuento(cantidadTotal);
                return;
            }
            SaltarLinea();
            ImprimirLinea("Qué desea hacer?");
            ImprimirLinea("1 -> Añadir otro producto");
            ImprimirLinea("2 -> Revisar lista productos");
            ImprimirLinea("3 -> Regresar");
            Imprimir("Ingrese su opción");
            opc = LeerNumeroIntervalo(1, 3);
        }
    }

    private void SeleccionarFactura() {
        SaltarLinea();
        ImprimirLinea("Seleccione el tipo de factura");
        ImprimirLinea("1 -> Física");
        ImprimirLinea("2 -> Electrónica");
        Imprimir("Ingrese su opción");
        int opc = LeerNumeroIntervalo(1, 2);
        if (opc == 1) {
            ImprimirFactura();
        } else {
            EnviarFactura();
        }
    }

    private void ImprimirFactura () {

    }

    private void EnviarFactura() {
        SaltarLinea();
        ImprimirLinea("--------------------------------FACTURA-------------------------------");
        ImprimirLinea("Cédula:" + cliente.getCedula());
        ImprimirLinea("Nombre y apellido: " + cliente.getNombre() + " " + cliente.getApellido());
        ImprimirLinea("Fecha: " + LocalDate.now());
        MostrarProductos(productosAComprar);
    }

    private void AplicarDescuento(int cantidad) {
        int n = productosAComprar.size();
        for (int i = 0; i < n; i++) {
            ProductoAComprar productos = productosAComprar.get(i);
            double descuento;
            double subTotal;
            if (cantidad < 6) descuento = 0;
            else descuento = descuentos[productos.getProducto().getTipo()];
            if (cantidad < 6) subTotal = productos.getProducto().getPrecioNormal();
            else subTotal = productos.getProducto().getPrecioNormal() * (1 - descuento);
            productos.setDescuento(descuento);
            productos.setSubtotal(subTotal);
        }
    }

    private void MostrarProductos(List<ProductoAComprar> productosAComprar) {
        SaltarLinea();
        ImprimirLinea("--------------------------------DETALLE--------------------------------");
        String formato = "| %-20s | %-5s | %-10s | %-10s | %-10s |";
        printf(formato, "NOMBRE", "TIPO", "CANTIDAD", "DESCUENTO", "SUBTOTAL");
        for (int i = 0; i < productosAComprar.size(); i++) {
            ProductoAComprar producto = productosAComprar.get(i);
            printf(formato,
                    producto.getProducto().getProducto(),
                    producto.getProducto().getTipo(),
                    producto.getCantidad(),
                    producto.getDescuento(),
                    producto.getDescuento()
            );
        }
        ImprimirLinea("-----------------------------------------------------------------------");
        SaltarLinea();
    }

    private void MostrarProductos() {
        SaltarLinea();
        ImprimirLinea("-----------------------------------------------INVENTARIO-----------------------------------------------");
        String formato = "| %-10s | %-20s | %-5s | %-10s | %-15s | %-25s |";
        printf(formato, "CODIGO", "NOMBRE", "TIPO", "STOCK", "PRECIO NORMAL", "PRECIO AL POR MAYOR");
        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            printf(formato,
                    producto.getCodigo(),
                    producto.getProducto(),
                    producto.getTipo(),
                    producto.getStock(),
                    producto.getPrecioNormal(),
                    producto.getPrecioAlPorMayor()
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
        String cedula = LeerCadena();
        Imprimir("Nombre");
        String nombre = LeerCadena();
        Imprimir("Apellido");
        String apellido = LeerCadena();
        Imprimir("Teléfono");
        String telefono = LeerCadena();
        Imprimir("Correo electrónico");
        String correo = LeerCadena();
        cliente = new Cliente(cedula, nombre, apellido, telefono, correo);
    }

    private void CargarDatosProductos() {
        String nombreArchivo = "src/modulos/venta/productos.txt";
        try {
            Scanner sc = new Scanner(new File(nombreArchivo));
            while (sc.hasNextLine()) {
                String[] datos = sc.nextLine().split(";");
                productos.add(new Producto(datos[0], datos[1], Integer.parseInt(datos[2]), Integer.parseInt(datos[3]), Double.parseDouble(datos[4]), Double.parseDouble(datos[5])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
