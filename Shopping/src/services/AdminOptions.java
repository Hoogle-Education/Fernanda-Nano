package services;

import exceptions.InvalidItemIndexException;
import models.*;

import java.util.List;
import java.util.Scanner;

public class AdminOptions {

    private static Scanner input = new Scanner(System.in);

    public static void showStoreItems() {
        List<StoreElement> storeElements = Store.getItems();

        System.out.println("------------------------------");
        for (int i = 0; i < storeElements.size(); i++) {
            System.out.println(" #" + i + " - " + storeElements.get(i));
        }
        System.out.println("------------------------------");
    }

    public static void addNewItemToStore() {
         Menu.showItemCategorySelection();
         int category = input.nextInt();
         input.nextLine();

        System.out.print("Name: ");
        String name = input.nextLine();

        System.out.println("Price: ");
        double price = input.nextDouble();
        input.nextLine();

        System.out.println("Quantity: ");
        int quantity = input.nextInt();
        input.nextLine();


        // TODO implements food register
         Item toRegister = switch (category) {
             case 1 -> {}
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
         };
    }

}
