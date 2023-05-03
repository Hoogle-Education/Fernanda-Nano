package models;

import exceptions.BuyingEmptyCartException;
import exceptions.InvalidItemIndexException;
import exceptions.NegativeOrZeroException;
import exceptions.OutOfStockException;
import utils.BubbleSort;

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
    public void addItemToCart(int itemId, int priority, int quantity)
            throws InvalidItemIndexException, NegativeOrZeroException, OutOfStockException {

        if (itemId > Store.getItems().size())
            throw new InvalidItemIndexException("The index must be in the items available items");

        if (itemId < 0)
            throw new NegativeOrZeroException("The value must be greater or equal than zero!");

        if (priority <= 0 || quantity <= 0)
            throw new NegativeOrZeroException("The value must be greater than zero!");

        Item itemFromStore = Store.getItemByQuantity(itemId, quantity);
        cartItems.add(new CartElement(itemFromStore, priority, quantity));
        BubbleSort.sort(cartItems);
    }
    public void finishAndBuyCart() throws BuyingEmptyCartException {
        if (cartItems.size() == 0)
            throw new BuyingEmptyCartException("You have to add items before finish.");

        for (var cartItem : cartItems) {
            Item item = cartItem.getItem();

            while (bankAccount >= cartItem.getItem().getPrice() && cartItem.getQuantity() > 0) {
                Item acquiredItem = null;

                var optionalCartElement = acquiredItems
                        .stream()
                        .filter(cartElement -> cartElement.getItem().equals(item))
                        .findFirst();

                if (optionalCartElement.isPresent()) {
                    var element = optionalCartElement.get();
                    element.incrementQuantity();
                    var index = acquiredItems.indexOf(element);
                    acquiredItems.set(index, element);
                } else {
                    acquiredItems.add(new CartElement(item, cartItem.getPriority(), 1));
                }

                bankAccount -= item.getPrice();
                cartItem.decrementQuantity();
            }

            if(cartItem.getQuantity() > 0) {
                remainingItems.add(new CartElement(item, cartItem.getPriority(), cartItem.getQuantity()));
            }
        }
    }

}
