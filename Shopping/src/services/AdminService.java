package services;

import exceptions.InvalidItemIndexException;
import exceptions.NegativeOrZeroException;
import models.*;
import models.abstracts.Item;
import utils.ItemsIO;
import utils.Menu;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class AdminService {

    private static Scanner input = new Scanner(System.in);

    public static void showStoreItems() {
        List<StoreElement> storeElements = Store.getItems();

        System.out.println("------------------------------");
        for (int i = 0; i < storeElements.size(); i++) {
            System.out.println(" #" + i + " - " + storeElements.get(i));
        }
        System.out.println("------------------------------");
    }

    public static void addNewItemToStore()
            throws InvalidItemIndexException {
         Menu.showItemCategorySelection();
         int category = input.nextInt();
         input.nextLine();

        System.out.print("Name: ");
        String name = input.nextLine();

        System.out.print("Price: ");
        double price = input.nextDouble();
        input.nextLine();

        System.out.print("Quantity: ");
        int quantity = input.nextInt();
        input.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");

         Item toRegister = switch (category) {
             case 1 -> {
                 System.out.print("Expires in (mm/dd/yy): ");
                 LocalDate expiresIn = LocalDate.parse(input.nextLine(), formatter);
                 System.out.print("Produces in (mm/dd/yy): ");
                 LocalDate producesIn = LocalDate.parse(input.nextLine(), formatter);
                 yield new FoodItem(name, price, producesIn, expiresIn);
             }
             case 2 -> {
                 System.out.print("Warnings: ");
                 String warnings = input.nextLine();
                 yield new CleaningSuppliesItem(name, price, warnings);
             }
             case 3 -> {
                 System.out.print("Brand: ");
                 String brand = input.nextLine();
                 yield new ClothingItem(name, price, brand);
             }
             default -> throw new InvalidItemIndexException("Unexpected value: " + category);
         };

        var storeElements = Store.getItems();
        storeElements.add(new StoreElement(toRegister, quantity));
        updateStoreInFile(storeElements);
    }

    public static void incrementExistingItemQuantity() {
        List<StoreElement> storeElements = Store.getItems();

        try {
            System.out.print("Item id: ");
            int itemId = input.nextInt();

            if (itemId < 0)
                throw new NegativeOrZeroException("The value must be greater than zero!");

            if (itemId >= storeElements.size())
                throw new InvalidItemIndexException("Unexpected value: " + itemId);

            System.out.println("Current quantity: " + storeElements.get(itemId).getQuantity());

            System.out.print("Quantity to increment: ");
            int quantity = input.nextInt();

            storeElements.get(itemId).incrementQuantity(quantity);
            System.out.println("Quantity updated.");
            updateStoreInFile(storeElements);
        } catch (NegativeOrZeroException | InvalidItemIndexException exception) {
            System.err.println(exception.getMessage());
        }
    }

    public static void deleteItemFromStore() {
        List<StoreElement> storeElements = Store.getItems();

        try {
            System.out.print("Item id: ");
            int itemId = input.nextInt();

            if (itemId < 0)
                throw new NegativeOrZeroException("The value must be greater than zero!");

            if (itemId >= storeElements.size())
                throw new InvalidItemIndexException("Unexpected value: " + itemId);

            var itemToDelete = storeElements.get(itemId).getItem();
            System.out.println("Item to be deleted: " +  itemToDelete);
            System.out.print("Do you confirm delete? (y/n): ");
            String accept = input.next();

            if(accept.equalsIgnoreCase("y")){
                var removedItem = storeElements.remove(itemId);

                if(removedItem == null)
                    throw new InvalidItemIndexException("This index is invalid to delete.");

                System.out.println("Item deleted.");
            } else {
                System.out.println("Deletion aborted.");
            }

            updateStoreInFile(storeElements);
        } catch (InvalidItemIndexException invalidItem) {
            System.err.println(invalidItem.getMessage());
        } catch (NegativeOrZeroException negativeOrZero) {
            System.err.println(negativeOrZero.getMessage());
        }

    }

    private static void updateStoreInFile(List<StoreElement> storeElements) {
        ItemsIO.writeStoreElements(storeElements, "items");
    }
}
