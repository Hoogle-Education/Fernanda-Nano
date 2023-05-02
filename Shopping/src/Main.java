import exceptions.BuyingEmptyCartException;
import exceptions.InvalidItemIndexException;
import exceptions.NegativeOrZeroException;
import models.*;
import utils.BubbleSort;
import utils.ItemsIO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static List<Item> items;
    private static List<CartElement> shoppingCart;
    private static List<CartElement> itemsAcquired;
    private static List<CartElement> itemsRemaining;
    private static Scanner input;

    public static void optionShowItems() {
        for(int i = 0; i < items.size(); i++) {
            System.out.println("#" + i + " - " + items.get(i));
        }
        System.out.println("------------------------------");
    }

    public static void optionAddItemToCart() {
        try {
            System.out.println("------------------------------");
            System.out.print("Type id of item > ");
            int itemId = input.nextInt();

            if (itemId > items.size()) throw new InvalidItemIndexException("The index must be in the items available items");
            if(itemId <= 0) throw new NegativeOrZeroException("The value must be greater or equal than zero!");

            Item itemFromAvailable = items.get(itemId);
            Item itemToAdd = null;

            if (itemFromAvailable instanceof FoodItem foodItem)
                itemToAdd = new FoodItem(foodItem);
            else if (itemFromAvailable instanceof ClothingItem clothingItem)
                itemToAdd = new ClothingItem(clothingItem);
            else if (itemFromAvailable instanceof CleaningSuppliesItem cleaningSuppliesItem)
                itemToAdd = new CleaningSuppliesItem(cleaningSuppliesItem);
            else return;

            System.out.print("Type the priority of this item > ");
            int newItemPriority = input.nextInt();

            if(newItemPriority <= 0) throw new NegativeOrZeroException("The value must be greater than zero!");

            System.out.print("Type the quantity of this item > ");
            int quantity = input.nextInt();
            if(quantity <= 0) throw new NegativeOrZeroException("The value must be greater than zero!");

            CartElement cartElement = new CartElement(itemToAdd, newItemPriority, quantity);
            shoppingCart.add(cartElement);
            BubbleSort.sort(shoppingCart);
        } catch (InvalidItemIndexException invalidIndex) {
            System.err.println(invalidIndex.getMessage());
        } catch (NegativeOrZeroException negativeOrZero) {
            System.err.println(negativeOrZero.getMessage());
        } finally {
            System.out.println("------------------------------");
        }
    }

    public static void optionCheckItemsInCart() {
        double total = 0.0;
        for(var itemInCart : shoppingCart) {
            total += itemInCart.getSubtotal();
        }

        System.out.println("------------------------------");
        System.out.println("Items in your cart:");
        System.out.println("------------------------------");
        System.out.println("Total in cart: $" + total);
        System.out.println("------------------------------");

        for(int i = 0; i < shoppingCart.size(); i++) {
            System.out.println("#" + i + " - " + shoppingCart.get(i));
        }

        System.out.println("------------------------------");
    }

    public static void optionFinishAndBuyCart(User user) {
        try {

            if(shoppingCart.size() == 0) throw new BuyingEmptyCartException("You have to add items before finish.");

            for (var itemInCart : shoppingCart) {
                while (user.getBankAccount() >= itemInCart.getItem().getPrice() && itemInCart.getQuantity() > 0) {
                    Item acquiredItem = null;
                    if (itemInCart.getItem() instanceof FoodItem foodItem) {
                        var optionalCartElement = itemsAcquired
                                .stream()
                                .filter(cartElement -> cartElement.getItem().equals(foodItem))
                                .findFirst();

                        if (optionalCartElement.isPresent()) {
                            var element = optionalCartElement.get();
                            element.incrementQuantity();
                            var index = itemsAcquired.indexOf(element);
                            itemsAcquired.set(index, element);
                        } else {
                            itemsAcquired.add(new CartElement(foodItem, itemInCart.getPriority(), 1));
                        }
                    } else if (itemInCart.getItem() instanceof ClothingItem clothingItem) {
                        var optionalCartElement = itemsAcquired
                                .stream()
                                .filter(cartElement -> cartElement.getItem().equals(clothingItem))
                                .findFirst();

                        if (optionalCartElement.isPresent()) {
                            var element = optionalCartElement.get();
                            element.incrementQuantity();
                            var index = itemsAcquired.indexOf(element);
                            itemsAcquired.set(index, element);
                        } else {
                            itemsAcquired.add(new CartElement(clothingItem, itemInCart.getPriority(), 1));
                        }
                    } else if (itemInCart.getItem() instanceof CleaningSuppliesItem cleaningSuppliesItem) {
                        var optionalCartElement = itemsAcquired
                                .stream()
                                .filter(cartElement -> cartElement.getItem().equals(cleaningSuppliesItem))
                                .findFirst();

                        if (optionalCartElement.isPresent()) {
                            var element = optionalCartElement.get();
                            element.incrementQuantity();
                            var index = itemsAcquired.indexOf(element);
                            itemsAcquired.set(index, element);
                        } else {
                            itemsAcquired.add(new CartElement(cleaningSuppliesItem, itemInCart.getPriority(), 1));
                        }
                    }

                    user.debitBalance(itemInCart.getItem().getPrice());
                    itemInCart.decrementQuantity();
                }

                if (itemInCart.getQuantity() > 0) {
                    if (itemInCart.getItem() instanceof FoodItem foodItem)
                        itemsRemaining.add(new CartElement(foodItem, itemInCart.getPriority(), itemInCart.getQuantity()));
                    else if (itemInCart.getItem() instanceof ClothingItem clothingItem)
                        itemsRemaining.add(new CartElement(clothingItem, itemInCart.getPriority(), itemInCart.getQuantity()));
                    else if (itemInCart.getItem() instanceof CleaningSuppliesItem cleaningSuppliesItem)
                        itemsRemaining.add(new CartElement(cleaningSuppliesItem, itemInCart.getPriority(), itemInCart.getQuantity()));
                }
            }

            double totalOfRemainingItems = 0.0;

            for (var itemRemaining : itemsRemaining) {
                totalOfRemainingItems += itemRemaining.getSubtotal();
            }

            System.out.println("------------------------------");
            System.out.println("Items acquired: ");

            System.out.println("------------------------------");

            for (int i = 0; i < itemsAcquired.size(); i++) {
                var itemBought = itemsAcquired.get(i);
                System.out.println("#" + i + " - " + itemBought);
            }

            System.out.println("------------------------------");

            if (itemsRemaining.size() > 0) {

                for (int i = 0; i < itemsRemaining.size(); i++) {
                    var itemRemaining = itemsRemaining.get(i);
                    System.out.println("#" + i + " - " + itemRemaining);
                }

                System.out.println("------------------------------");
                var missingAmount = (totalOfRemainingItems - user.getBankAccount());
                user.addMissingAmount(missingAmount);
                System.out.println("Missing amount: $" + missingAmount);
            }

            System.out.println("Balance remaining: $" + user.getBankAccount());

        } catch (BuyingEmptyCartException buyingEmptyCart) {
            System.err.println(buyingEmptyCart.getMessage());
        } finally {
            System.out.println("------------------------------");
        }

    }

    public static void showMenu() {
        System.out.println("1 - to show items list");
        System.out.println("2 - to add item to cart");
        System.out.println("3 - to check your cart");
        System.out.println("4 - to finish and pay");
        System.out.println("0 - to go out");
        System.out.print("Type your option > ");
    }

    public static void main(String[] args) throws IOException {

        input = new Scanner(System.in);
        items = ItemsIO.read("shopping_items");
        shoppingCart = new ArrayList<>();
        itemsAcquired = new ArrayList<>();
        itemsRemaining = new ArrayList<>();

        int option;
        System.out.println("Welcome!");

        System.out.print("Name: ");
        String name = input.nextLine();
        System.out.print("Balance: ");
        double balance = input.nextDouble();

        User user = new User(name, balance);

        do {
            showMenu();
            option = input.nextInt();

            if (option == 1) {
                optionShowItems();
            } else if (option == 2) {
                optionAddItemToCart();
            } else if(option == 3) {
                optionCheckItemsInCart();
            } else if(option == 4) {
                optionFinishAndBuyCart(user);
            }

            ItemsIO.write(itemsAcquired, "acquired_items");
            ItemsIO.write(itemsRemaining, "itemsRemaining");

        } while(option != 0);

        System.out.println("Your initial balance: " + user.getInitialBankAccount());
        System.out.println("You've bought: " + user.getBoughtAmount());
        System.out.println("Missing amount to go: " + user.getMissingAmount());
        System.out.println("bye, " + name + "!");
    }
}

//    Item food = new Item("bread", 2.5);
//// food.nome = "bread";
//// food.price = 2.5;
//// food.priority = 5;
//
//        food.setName("chesse");
//
//                System.out.println(food.getName());
//                System.out.println(food.getPrice());