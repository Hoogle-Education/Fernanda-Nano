package models;

public class StoreElement {

    private Item item;
    private int quantity;

    public StoreElement(Item item, int quantity) {
        this.item = item;
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

    public void incrementQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void decrementQuantity(int quantity) {
        this.quantity -= quantity;
    }

    @Override
    public String toString() {
        return item + ", Quantity: " + quantity;
    }
}
