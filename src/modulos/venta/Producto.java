package modulos.venta;

public class Producto {

    private String codigo;
    private String producto;
    private int tipo;
    private int stock;
    private double precioNormal;
    private double precioAlPorMayor;

    public Producto() {
    }

    public Producto(String codigo, String producto, int tipo, int stock, double precioNormal, double precioAlPorMayor) {
        this.codigo = codigo;
        this.producto = producto;
        this.tipo = tipo;
        this.stock = stock;
        this.precioNormal = precioNormal;
        this.precioAlPorMayor = precioAlPorMayor;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecioNormal() {
        return precioNormal;
    }

    public void setPrecioNormal(double precioNormal) {
        this.precioNormal = precioNormal;
    }

    public double getPrecioAlPorMayor() {
        return precioAlPorMayor;
    }

    public void setPrecioAlPorMayor(double precioAlPorMayor) {
        this.precioAlPorMayor = precioAlPorMayor;
    }

}
