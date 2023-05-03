package services;

import exceptions.BuyingEmptyCartException;
import exceptions.InvalidItemIndexException;
import exceptions.NegativeOrZeroException;
import exceptions.OutOfStockException;
import models.CartElement;
import models.Store;
import models.StoreElement;
import models.User;
import utils.ItemsIO;

import java.util.List;
import java.util.Scanner;

public class UserOptions {

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

            user.addItemToCart(itemId, priority, quantity);
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
            user.finishAndBuyCart();
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

        ItemsIO.write(user.getAcquiredItems(), "acquired_items");
        ItemsIO.write(user.getRemainingItems(), "items_remaining");
    }
}
