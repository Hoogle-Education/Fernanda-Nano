import exceptions.*;
import models.*;
import services.AdminService;
import services.UserService;
import utils.Menu;

import java.util.Scanner;

public class EcommerceManagingApplication {
    private static Scanner input;

    public static int adminInteractionLoop() {
        input.nextLine();
        System.out.print("Admin Username: ");
        String username = input.nextLine();
        System.out.print("Admin password: ");
        String password = input.nextLine();

        int option;

        try {
            boolean isInvalidUsername = !username.equals("admin");
            boolean isInvalidPassword = !username.equals("admin123");

            if (isInvalidUsername && isInvalidPassword) {
                throw new InvalidAdminCredentialsException("Invalid credentials.");
            }

            do {
                Menu.showToAdmin();
                option = input.nextInt();

                switch (option) {
                    case 1 -> AdminService.showStoreItems();
                    case 2 -> AdminService.addNewItemToStore();
                    case 3 -> AdminService.incrementExistingItemQuantity();
                }

            } while (option != 0 && option != 9);

        } catch (InvalidAdminCredentialsException invalidCredentials) {
            System.err.println(invalidCredentials.getMessage());
            return 9; // go back user/admin menu
        } catch (InvalidItemIndexException invalidItem) {
            System.err.println(invalidItem.getMessage());
            return 9;
        }

        return option;
    }

    public static int userInteractionLoop() {
        input.nextLine();
        System.out.print("Name: ");
        String name = input.nextLine();
        System.out.print("Balance: ");
        double balance = input.nextDouble();

        User user = new User(name, balance);
        int option;

        do {
            Menu.showToUser();
            option = input.nextInt();

            switch (option) {
                case 1 -> UserService.showStoreAvailableItems(user);
                case 2 -> UserService.addItemToCart(user);
                case 3 -> UserService.checkItemsInCart(user);
                case 4 -> UserService.finishAndBuyCart(user);
            }

        } while (option != 0 && option != 9);

        System.out.println("Your initial balance: " + user.getInitialBankAccount());
        System.out.println("You've bought: " + user.getBoughtAmount());
        System.out.println("Missing amount to go: " + user.getMissingAmount());
        System.out.println("bye, " + name + "!");
        return option;
    }

    public static void main(String[] args){

        input = new Scanner(System.in);

        System.out.println("Welcome!");
        int option;

        do {
            Menu.showEntryMenu();
            option = input.nextInt();

            option = switch (option) {
                case 1 -> adminInteractionLoop();
                case 2 -> userInteractionLoop();
                default -> 9;
            };

        } while (option != 0);

    }
}