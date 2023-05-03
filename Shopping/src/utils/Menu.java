package utils;

public class Menu {
    public static void showEntryMenu() {
        System.out.println("###------------------------###");
        System.out.println("What your role?");
        System.out.println("1 - Admin");
        System.out.println("2 - User");
        System.out.println("0 - to go out");
        System.out.print("Type your option > ");
    }
    public static void showToAdmin() {
        System.out.println("1 - to show items in store");
        System.out.println("2 - to add new Item to store");
        System.out.println("3 - to increment item quantity of an existing item");
        System.out.println("4 - to delete item from store");
        System.out.println("9 - to go back");
        System.out.println("0 - to go out");
        System.out.print("Type your option > ");
    }
    public static void showToUser() {
        System.out.println("1 - to show items list");
        System.out.println("2 - to add item to cart");
        System.out.println("3 - to check your cart");
        System.out.println("4 - to finish and pay");
        System.out.println("9 - to go back");
        System.out.println("0 - to go out");
        System.out.print("Type your option > ");
    }

    public static void showItemCategorySelection() {
        System.out.println("------------------------");
        System.out.println("Witch one is the type of your item?");
        System.out.println("1 - Food");
        System.out.println("2 - Cleaning Supplies");
        System.out.println("3 - Clothing");
        System.out.print("Type your option > ");
    }

}
