package modulos.restaurante;

public class Pedido {

    private int id;
    Plato plato = new Plato();

    public Pedido() {
    }

    public Pedido(int id, Plato plato) {
        this.id = id;
        this.plato = plato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Plato getPlato() {
        return plato;
    }

    public void setPlato(Plato plato) {
        this.plato = plato;
    }

}
