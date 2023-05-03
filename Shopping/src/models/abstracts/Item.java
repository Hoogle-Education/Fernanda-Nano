package models.abstracts;

import models.enums.ItemCategory;

import java.util.Objects;

public abstract class Item {

    // atributos - tem, caracteristicas
    protected String name;
    protected double price;

    protected ItemCategory category;

    // construtor
    // O constructor possui o mesmo nome da classe
    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Item(Item source) {
        this.name = source.name;
        this.price = source.price;
    }

    // getter and setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ItemCategory getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Name: " + name  +
                ", Price: $" + price;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;

        Item item = (Item) other;

        if (Double.compare(item.price, price) != 0) return false;
        return Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    // método personalido
    // método: faz, verbo
    public String toCsv() { 
        // CSV = Comma Separated Values
        return name + "," + price; 
    }

}
