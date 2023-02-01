package modulos.restaurante;

import java.util.ArrayList;
import java.util.List;

public class Plato {

    private int id;
    private List<Ingrediente> ingredientes = new ArrayList<>();
    private String nombre;

    public Plato() {
    }

    public Plato(int id, String nombre, List<Ingrediente> ingredientes) {
        this.id = id;
        this.nombre = nombre;
        this.ingredientes = ingredientes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public void addIngrediente(Ingrediente ingrediente) {
        this.ingredientes.add(ingrediente);
    }

}
