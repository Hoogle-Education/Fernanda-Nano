import exceptions.*;
import models.*;
import services.AdminOptions;
import services.UserOptions;
import utils.ItemsIO;
import services.Menu;

import java.util.List;
import java.util.Scanner;

public class Application {

    private static Scanner input;

    public static int adminInteractionLoop() {
        input.nextLine();
        System.out.print("Admin Username: ");
        String username = input.nextLine();
        System.out.print("Admin password: ");
        String password = input.nextLine();

        try {
            boolean isInvalidUsername = !username.equals("admin");
            boolean isInvalidPassword = !username.equals("admin123");

            if (isInvalidUsername && isInvalidPassword) {
                throw new InvalidAdminCredentialsException("Invalid credentials.")
            }
        } catch (InvalidAdminCredentialsException invalidCredentials) {
            System.err.println(invalidCredentials.getMessage());
            return 9; // go back user/admin menu
        }

        int option;

        do {
            Menu.showToUser();
            option = input.nextInt();

            switch (option) {
                case 1 -> AdminOptions.showStoreItems();
                case 2 -> UserOptions.addItemToCart(user);
                case 3 -> UserOptions.checkItemsInCart(user);
                case 4 -> UserOptions.finishAndBuyCart(user);
            }

        } while (option != 0 && option != 9);

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
                case 1 -> UserOptions.showStoreAvailableItems(user);
                case 2 -> UserOptions.addItemToCart(user);
                case 3 -> UserOptions.checkItemsInCart(user);
                case 4 -> UserOptions.finishAndBuyCart(user);
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

            if(option == 1) {
                System.out.println("admin :)");
            } else if (option == 2) {
                option = userInteractionLoop();
            }

        } while (option != 0);

    }
}