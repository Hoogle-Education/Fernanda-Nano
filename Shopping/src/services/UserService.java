package services;

import exceptions.BuyingEmptyCartException;
import exceptions.InvalidItemIndexException;
import exceptions.NegativeOrZeroException;
import exceptions.OutOfStockException;
import models.CartElement;
import models.Store;
import models.StoreElement;
import models.User;
import models.abstracts.Item;
import utils.BubbleSort;
import utils.ItemsIO;

import java.util.List;
import java.util.Scanner;

public class UserService {

    private static Scanner input = new Scanner(System.in);

    public static void showStoreAvailableItems(User user) {
        List<StoreElement> availableStoreElements = Store.getAvailableItems();

        System.out.println("------------------------------");
        for (int i = 0; i < availableStoreElements.size(); i++) {
            System.out.println(" #" + i + " - " + availableStoreElements.get(i));
        }
        System.out.println("------------------------------");
    }

    public static void addItemToCart(User user) {
        try {
            System.out.println("------------------------------");
            System.out.print("Type id of item > ");
            int itemId = input.nextInt();

            System.out.print("Type the priority of this item > ");
            int priority = input.nextInt();

            System.out.print("Type the quantity of this item > ");
            int quantity = input.nextInt();

            addItemToUserCart(user, itemId, priority, quantity);
        } catch (InvalidItemIndexException invalidIndex) {
            System.err.println(invalidIndex.getMessage());
        } catch (NegativeOrZeroException negativeOrZero) {
            System.err.println(negativeOrZero.getMessage());
        } catch (OutOfStockException outOfStock) {
            System.err.println(outOfStock.getMessage());
        } finally {
            System.out.println("------------------------------");
        }
    }

    public static void checkItemsInCart(User user) {

        System.out.println("------------------------------");
        System.out.println("Items in your cart:");
        System.out.println("------------------------------");
        System.out.println("Total in cart: $" + user.getTotalFromCart());
        System.out.println("------------------------------");

        List<CartElement> userCart = user.getCartItems();
        for (int i = 0; i < userCart.size(); i++) {
            System.out.println(" #" + i + " - " + userCart.get(i));
        }

        System.out.println("------------------------------");
    }

    public static void finishAndBuyCart(User user) {
        try {
            finishAndBuyUserCart(user);
            List<CartElement> acquiredItems = user.getAcquiredItems();
            List<CartElement> remainingItems = user.getRemainingItems();

            System.out.println("------------------------------");
            System.out.println("Items acquired: ");

            System.out.println("------------------------------");

            for (int i = 0; i < acquiredItems.size(); i++) {
                var itemBought = acquiredItems.get(i);
                System.out.println("#" + i + " - " + itemBought);
            }

            System.out.println("------------------------------");

            if (remainingItems.size() > 0) {

                for (int i = 0; i < remainingItems.size(); i++) {
                    var itemRemaining = remainingItems.get(i);
                    System.out.println("#" + i + " - " + itemRemaining);
                }

                System.out.println("------------------------------");
                System.out.println("Missing amount: $" + user.getMissingAmount());
            }

            System.out.println("Balance remaining: $" + user.getBankAccount());

        } catch (BuyingEmptyCartException buyingEmptyCart) {
            System.err.println(buyingEmptyCart.getMessage());
        } finally {
            System.out.println("------------------------------");
        }

        ItemsIO.writeCartElements(user.getAcquiredItems(), "acquired_items");
        ItemsIO.writeCartElements(user.getRemainingItems(), "items_remaining");
    }

    private static void addItemToUserCart(User user, int itemId, int priority, int quantity)
            throws InvalidItemIndexException, NegativeOrZeroException, OutOfStockException {

        if (itemId > Store.getItems().size())
            throw new InvalidItemIndexException("The index must be in the items available items");

        if (itemId < 0)
            throw new NegativeOrZeroException("The value must be greater or equal than zero!");

        if (priority <= 0 || quantity <= 0)
            throw new NegativeOrZeroException("The value must be greater than zero!");

        Item itemFromStore = Store.getItemByQuantity(itemId, quantity);
        user.getCartItems().add(new CartElement(itemFromStore, priority, quantity));
        BubbleSort.sort(user.getCartItems());
    }

    private static void finishAndBuyUserCart(User user)
            throws BuyingEmptyCartException {

        var cartItems = user.getCartItems();
        var acquiredItems = user.getAcquiredItems();
        var remainingItems = user.getRemainingItems();

        if (cartItems.size() == 0)
            throw new BuyingEmptyCartException("You have to add items before finish.");

        for (var cartItem : cartItems) {
            Item item = cartItem.getItem();

            while (user.getBankAccount() >= cartItem.getItem().getPrice() && cartItem.getQuantity() > 0) {
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

                user.payWithBankAccount(item.getPrice());
                cartItem.decrementQuantity();
            }

            if(cartItem.getQuantity() > 0) {
                remainingItems.add(new CartElement(item, cartItem.getPriority(), cartItem.getQuantity()));
            }
        }
    }
}
