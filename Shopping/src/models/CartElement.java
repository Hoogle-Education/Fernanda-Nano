package models;

import models.abstracts.Item;

import java.util.Objects;

public class CartElement implements Comparable<CartElement> {

    // atributos - tem
    private Item item; // tem um item
    private int quantity;
    private int priority;

    public CartElement(Item item, int priority, int quantity) {
        this.item = item;
        this.priority = priority;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        this.quantity++;
    }
    public void decrementQuantity() {
        this.quantity--;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public double getSubtotal() {
        return item.getPrice() * quantity;
    }

    @Override
    public int compareTo(CartElement other) {
        return Integer.compare(this.priority, other.priority);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartElement that = (CartElement) o;

        if (quantity != that.quantity) return false;
        if (priority != that.priority) return false;
        return Objects.equals(item, that.item);
    }

    @Override
    public int hashCode() {
        int result = item != null ? item.hashCode() : 0;
        result = 31 * result + quantity;
        result = 31 * result + priority;
        return result;
    }

    public String toCsv() {
        return item.toCsv() +
                "," + quantity +
                "," + priority;
    }

    @Override
    public String toString() {
        return item +
               ", Quantity: " + quantity +
                ", Priority: " + priority;
    }
}
