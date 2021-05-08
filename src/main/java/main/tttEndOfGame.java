package main;

import java.util.Scanner;

public class tttEndOfGame {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean quit = false;

        printActions();

        while (!quit) {
            int choice = 0;
            System.out.println("Please choose next action:");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    //  printOptions();
                    break;
                case 1:
                    //  playAgain();
                    break;
                case 2:
                    //  logOut();
                    break;
                case 3:
                    //   listOfGames();
                case 4:
                    //   chooseGameToSeeMoves();
                    break;
                case 5:
                    quit = true;
                    break;
                default:
                    System.out.println("Wrong input!");
                    break;
            }
        }
    }

    private static void printActions() {
        System.out.println("\nPress");
        System.out.println("\t 0 - print actions");
        System.out.println("\t 1 - I want to play again");
        System.out.println("\t 2 - I want to log out");
        System.out.println("\t 3 - print a list of my games");
        System.out.println("\t 4 - show moves of game");
        System.out.println("\t 5 - quit the application");
    }
}
