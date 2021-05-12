package main;

import java.util.*;

public class TicTacToe extends SQLDatabase {

    //arraylist of integers for player position
    static ArrayList<Integer> playerPositions = new ArrayList<Integer>();
    static ArrayList<Integer> cpuPositions = new ArrayList<Integer>();

//    public static void main(String[] args) {
//
//        //have to place empty spaces with a numbers, for easy playing
//
//        char[][] gameBoard = {{' ', '|', ' ', '|', ' '}, //0
//                {'-', '+', '-', '+', '-'},
//                {' ', '|', ' ', '|', ' '},               //2
//                {'-', '+', '-', '+', '-'},
//                {' ', '|', ' ', '|', ' '}};              //4
//
//        printGameBoard(gameBoard);
//
//        //infinitive loop
//        //in the game loop
//        //get input from the user using a scanner in the position 1-9
//        // stored input and checked is there a winner
//
//        while (true) {
//            Scanner scanner = new Scanner(System.in);
//            System.out.println("Enter your placement (1-9): ");
//            int playerPosition = scanner.nextInt();
//
//            //while they not enter correct position, keep asking to put correct until they do
//            while (playerPositions.contains(playerPosition) ||
//                    cpuPositions.contains(playerPosition)) {
//                System.out.println("Position is taken! Enter a correct position");
//                playerPosition = scanner.nextInt();
//            }
//
//            //always check a winner and the result after each player and cpu move
//            String result = checkWinner();
//            if (result.length() > 0) {
//                System.out.println(result);
//                break;
//            }
//
//            placePiece(gameBoard, playerPosition, "player");
//
//            //printGameBoard(gameBoard);
//
//            result = checkWinner();
//            if (result.length() > 0) {
//                System.out.println(result);
//                break;
//            }
//
//            //cpu makes move
//            //store input
//            //check if there is a winner
//            Random random = new Random();
//            int cpuPosition = random.nextInt(9) + 1;
//            while (playerPositions.contains(cpuPosition) ||
//                    cpuPositions.contains(cpuPosition)) {
//                cpuPosition = random.nextInt(9) + 1;
//            }
//
//            placePiece(gameBoard, cpuPosition, "cpu");
//
//            printGameBoard(gameBoard);
//
//        }
//
//    }
//
    public static void printGameBoard(char[][] gameBoard) {
        for (char[] row : gameBoard) {
            for (char symbol : row) {
                System.out.print(symbol);
            }
            System.out.println();
        }
    }

    public static void placePiece(char[][] gameBoard, int position, String user) {

        char symbol = ' ';

        if (user.equals("player")) {
            symbol = 'X';
            playerPositions.add(position);
        } else if (user.equals("cpu")) {
            symbol = 'O';
            cpuPositions.add(position);
        }

        switch (position) {
            case 1:
                //[row][column]
                gameBoard[0][0] = symbol;
                break;
            case 2:
                gameBoard[0][2] = symbol;
                break;
            case 3:
                gameBoard[0][4] = symbol;
                break;
            case 4:
                gameBoard[2][0] = symbol;
                break;
            case 5:
                gameBoard[2][2] = symbol;
                break;
            case 6:
                gameBoard[2][4] = symbol;
                break;
            case 7:
                gameBoard[4][0] = symbol;
                break;
            case 8:
                gameBoard[4][2] = symbol;
                break;
            case 9:
                gameBoard[4][4] = symbol;
                break;
            default:
                break;
        }
    }

    public static String checkWinner() {

        List topRow = Arrays.asList(1, 2, 3);
        List middleRow = Arrays.asList(4, 5, 6);
        List bottomRow = Arrays.asList(7, 8, 9);

        List leftColumn = Arrays.asList(1, 4, 7);
        List middleColumn = Arrays.asList(2, 5, 8);
        List rightColumn = Arrays.asList(3, 6, 9);

        List cross1 = Arrays.asList(1, 5, 9);
        List cross2 = Arrays.asList(7, 5, 3);

        List<List> winning = new ArrayList<List>();

        winning.add(topRow);
        winning.add(middleRow);
        winning.add(bottomRow);

        winning.add(leftColumn);
        winning.add(middleColumn);
        winning.add(rightColumn);

        winning.add(cross1);
        winning.add(cross2);

        for (List lists : winning) {
            if (playerPositions.containsAll(lists)) {
                return "Congratulations you won!";
            } else if (cpuPositions.containsAll(lists)) {
                return "CPU wins!";
            } else if (playerPositions.size() + cpuPositions.size() == 9) {
                return "TIE!";
            }
        }

        //bug
        // If you place a winning piece as the last move -
        // it will still register as a Tie,
        // since the last "else if" in the for loop
        // Need to put this check as a separate if statement outside the for loop and it solves it.

        //didn't print the game board (put the last number) after print CAT

        return "";
    }


}

