package models;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String name;
    private String role;
    private double bankAccount;
    private double initialBankAccount;
    private double missingAmount;
    private List<CartElement> cartItems;
    private List<CartElement> acquiredItems;
    private List<CartElement> remainingItems;

    public User(String name, double bankAccount) {
        this.name = name;
        this.initialBankAccount = bankAccount;
        this.bankAccount = bankAccount;
        this.missingAmount = 0;
        cartItems = new ArrayList<>();
        acquiredItems = new ArrayList<>();
        remainingItems = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getBankAccount() {
        return bankAccount;
    }
    public double getInitialBankAccount() {
        return initialBankAccount;
    }
    public double getMissingAmount() {
        return remainingItems
                .stream()
                .mapToDouble(CartElement::getSubtotal)
                .sum();
    }
    public double getBoughtAmount() {
        return (this.initialBankAccount - this.bankAccount);
    }
    public double getTotalFromCart() {
        return cartItems.stream().mapToDouble(CartElement::getSubtotal).sum();
    }
    public List<CartElement> getCartItems() {
        return cartItems;
    }
    public List<CartElement> getAcquiredItems() {
        return acquiredItems;
    }
    public List<CartElement> getRemainingItems() {
        return remainingItems;
    }
    public void payWithBankAccount(double amount) {
        this.bankAccount -= amount;
    }

}
