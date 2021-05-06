


    public class TicTacToe {

        public static void main(String[] args) {

            //create a game board array
            char[][] gameBoard = {{' ', '|', ' ', '|', ' '},
                    {' ', '|', ' ', '|', ' '},
                    {' ', '|', ' ', '|', ' '}};

            printGameBoard(gameBoard);


        }

        //print out array
        public static void printGameBoard(char[][] gameBoard) {
            System.out.println("\n-+-+-");
            for (char[] row : gameBoard) {
                for (char c : row) {
                    System.out.print(c);
                }
                System.out.println("\n-+-+-");
            }
        }

    }
