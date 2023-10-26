import java.util.Scanner;

public class MathGame {
    private Player player1;
    private Player player2;
    private Player player3;
    private Player currentPlayer;
    private Player winner;
    private Player prevWinner;
    private int streak;
    private boolean gameOver;
    private Scanner scanner;
    private boolean veryUnlucky;

    // create MathGame object
    public MathGame(Player player1, Player player2, Player player3, Scanner scanner) {
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.scanner = scanner;
        currentPlayer = null; // will get assigned at start of game
        winner = null; // will get assigned when a Player wins
        gameOver = false;
    }

    // ------------ PUBLIC METHODS (to be used by client classes) ------------

    // returns winning Player; will be null if neither Player has won yet
    public Player getWinner() {
        return winner;
    }

    // plays a round of the math game
    public void playRound() {
        int fails = 0;
        chooseStartingPlayer();  // this helper method (shown below) sets currentPlayer to either player1 or player2
        while (!gameOver) {
            printGameState();   // this helper method (shown below) prints the state of the Game
            System.out.println("Current player: " + currentPlayer.getName());
            boolean correct = askQuestion();  // this helper method (shown below) asks a question and returns T or F
            if (correct) {
                System.out.println("Correct!");
                fails = 0;
                currentPlayer.incrementScore();  // this increments the currentPlayer's score
                swapPlayers();  // this helper method (shown below) sets currentPlayer to the other Player
            } else if (fails < 1){
                fails++;
            } else {
                System.out.println("INCORRECT!");
                gameOver = true;
                fails = 0;
                determineWinner();
                countStreak();
            }
        }
    }

    // prints the current scores of the two players
    private void printGameState() {
        System.out.println("--------------------------------------");
        System.out.println("Current Scores:");
        System.out.println(player1.getName() + ": " + player1.getScore());
        System.out.println(player2.getName() + ": " + player2.getScore());
        System.out.println(player3.getName() + ": " + player3.getScore());
        System.out.println("--------------------------------------");
    }

    // resets the game back to its starting state
    public void resetGame() {
        player1.reset(); // this method resets the player
        player2.reset();
        player3.reset();
        gameOver = false;
        currentPlayer = null;
        winner = null;
    }

    // ------------ PRIVATE HELPER METHODS (internal use only) ------------

    private double round(double num) {
        return ((int) (num * 100)) / 100.0;
    }

    // randomly chooses one of the Player objects to be the currentPlayer
    private void chooseStartingPlayer() {
        int randNum = (int) (Math.random() * 3) + 1;
        if (randNum == 1) {
            currentPlayer = player1;
        } else if (randNum == 2) {
            currentPlayer = player2;
        } else {
            currentPlayer = player3;
        }
    }

    // asks a math question and returns true if the player answered correctly, false if not
    private boolean askQuestion() {
        double operation = (Math.random() * 10);
        veryUnlucky = false;
        if (Math.random() == 0) {
            operation = 10;
            veryUnlucky = true;
        }
        int num1 = (int) (Math.random() * 100) + 1;
        int num2;
        double correctAnswer;
        System.out.println("Type in your answer as an number (/ is double division, truncated after hundreds place)");
        if (operation <= 4) {
            num2 = (int) (Math.random() * 283) + 1;
            System.out.print(num1 + " + " + num2 + " = ");
            correctAnswer = num1 + num2;
        } else if (operation <= 8) {
            num2 = (int) (Math.random() * 391) + 1;
            System.out.print(num1 + " - " + num2 + " = ");
            correctAnswer = num1 - num2;
        } else if (operation <= 9) {
            System.out.println("Unlucky You!");
            num2 = (int) (Math.random() * 19) + 2;
            System.out.print(num1 + " * " + num2 + " = ");
            correctAnswer = num1 * num2;
        } else if (operation < 10) {
            System.out.println("Unlucky You!");
            num2 = (int) (Math.random() * 53) + 21;
            System.out.print(num1 + " / " + num2 + " = ");
            correctAnswer = round((double) num1 / (double) num2);
        } else {
            System.out.println("You got VERY unlucky!");
            num2 = (int) (Math.random() * 10) + 1;
            System.out.print(num1 + "^" + num2 + " = ");
            correctAnswer = Math.pow(num1, num2);
        }

        double playerAnswer = scanner.nextDouble(); // get player's answer using Scanner
        scanner.nextLine(); // clear text buffer after numeric scanner input


        return playerAnswer == correctAnswer;
    }

    // swaps the currentPlayer to the other player
    private void swapPlayers() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else if (currentPlayer == player2) {
            currentPlayer = player3;
        } else if (currentPlayer == player3) {
            currentPlayer = player1;
        }
    }

    // sets the winner when the game ends based on the player that missed the question
    private void determineWinner() {
        if (currentPlayer == player1) {
            if (player2.getScore() == player3.getScore()) {
                winner = null;
            } else if (player2.getScore() > player3.getScore()) {
                winner = player2;
            } else if (player3.getScore() > player2.getScore()) {
                winner = player3;

            }
        } else if (currentPlayer == player2) {
            if (player1.getScore() == player3.getScore()) {
                winner = null;

            } else if (player1.getScore() > player3.getScore()) {
                winner = player1;

            } else if (player3.getScore() > player2.getScore()) {
                winner = player3;

            }
        } else if (currentPlayer == player3) {
            if (player1.getScore() == player2.getScore()) {
                winner = null;
            } else if (player1.getScore() > player2.getScore()) {
                winner = player1;

            } else if (player2.getScore() > player1.getScore()) {
                winner = player2;

            }
        }
        prevWinner = winner;
    }

    private void countStreak() {
        if (winner != null) {
            if (winner == (prevWinner)) {
                streak++;
                System.out.println("Current Streak: " + winner.getName() + " at " + streak + " wins");
            } else if (winner != prevWinner) {
                streak = 0;
                System.out.println("Current Streak: " + winner.getName() + " at " + streak + " wins");
            }
        } else {
            streak = 0;
            System.out.println("No one won");
        }
    }
}
