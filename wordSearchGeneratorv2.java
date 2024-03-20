//Name: Nicholas Alvarez 
//Class: CS 145
//File: wordSearchGenerator.java
//Purpose: Your task is to create a basic word search generator. A word search is a puzzle with many letters
//and inside are several words that a person is supposed to find.
import java.util.*;

public class wordSearchGeneratorv2 {
    private static char[][] solvedWordy;
    private static char [][] _wordy;
    

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to my word search generator.");
        System.out.println("This program will allow you to generate your own word search puzzle ");

        while (true) {
            System.out.println("Please select an option:");

            System.out.println("type '(G)' to generate a new word search\n" +
                    "type '(V)' to view your word search\n" +
                    "type '(S)' to see the solution of the word search\n" +
                    "or '(Q)' to quit");

            String userInput = input.nextLine().toLowerCase();

            switch (userInput) {
                case "g":
                    generate();
                    break;
                case "v":
                    printSolutionGrid(solvedWordy);
                    break;
                case "s":
                    revealSolution();
                    break;
                case "q":
                    System.out.println("BYE");
                    return;
                default:
                    System.out.println("Command is not a part of the menu. Please enter one that is.");
                    break;
            }
        }
    }

    public static void generate() {
        Scanner input = new Scanner(System.in);
        System.out.println("Type in the words for your word search. Once you are done, type 'q'.");
        ArrayList<String> words = new ArrayList<>();

        String word;
        int maxWordLength = 0;

        word = input.nextLine();

        while (!word.equals("q")) {
            if (word.length() > maxWordLength)
                maxWordLength = word.length();
            words.add(word);
            word = input.nextLine();
        }

        int gridSize = Math.max(maxWordLength * 2, words.size() * 2);
        char[][] wordy = new char[gridSize][gridSize];
        solvedWordy = new char[gridSize][gridSize];
        _wordy = new char[gridSize][gridSize];

        Random rnd = new Random();

        // Fill the grid with random characters
        for (int i = 0; i < wordy.length; i++) {
            for (int j = 0; j < wordy[i].length; j++) {
                wordy[i][j] = '_';

            }

        }

        // Place words in the grid
        for (String w : words) {
            boolean iswordplaced =  placeWord(wordy, w);
            while(! iswordplaced){
                iswordplaced= placeWord(wordy, w);
            }
        }
        for (int i = 0; i < wordy.length; i++) {
            System.arraycopy(wordy[i], 0, _wordy[i], 0, wordy[i].length);
        }

        for (int i = 0; i < wordy.length; i++) {
            for (int j = 0; j < wordy[i].length; j++) {
               if (wordy[i][j]== '_') {
              wordy[i][j] = (char) ('a' + rnd.nextInt(26));}
            }
        }

        // Copy wordy to solvedWordy
        for (int i = 0; i < wordy.length; i++) {
            System.arraycopy(wordy[i], 0, solvedWordy[i], 0, wordy[i].length);
        }

        // Print the solution grid
        printSolutionGrid(solvedWordy);
    }

    private static boolean placeWord(char[][] grid, String word) {
        Random rnd = new Random();
        int direction = rnd.nextInt(8); // Random direction: 0 for horizontal, 1 for vertical, 2 for diagonal, etc.

        int row = rnd.nextInt(grid.length);
        int col = rnd.nextInt(grid[0].length);

        int length = word.length();

        switch (direction) {
            case 0:  // Horizontal (left to right)
                if (col + length <= grid[0].length) {
                    for (int i = 0; i < length; i++) {
                        grid[row][col + i] = word.charAt(i);
                    } return true; 
                }
                break;
            case 1:  // Horizontal (right to left)
                if (col - length >= 0) {
                    for (int i = 0; i < length; i++) {
                        grid[row][col - i] = word.charAt(i);
                    }return true; 
                }
                break;
            case 2:  // Vertical (top to bottom)
                if (row + length <= grid.length) {
                    for (int i = 0; i < length; i++) {
                        grid[row + i][col] = word.charAt(i);
                    }return true; 
                }
                break;
            case 3:  // Vertical (bottom to top)
                if (row - length >= 0) {
                    for (int i = 0; i < length; i++) {
                        grid[row - i][col] = word.charAt(i);
                    }return true; 
                }
                break;
            case 4:  // Diagonal (top-left to bottom-right)
                if (row + length <= grid.length && col + length <= grid[0].length) {
                    for (int i = 0; i < length; i++) {
                        grid[row + i][col + i] = word.charAt(i);
                    }return true; 
                }
                break;
            case 5:  // Diagonal (bottom-right to top-left)
                if (row - length >= 0 && col - length >= 0) {
                    for (int i = 0; i < length; i++) {
                        grid[row - i][col - i] = word.charAt(i);
                    }return true; 
                }
                break;
            case 6:  // Diagonal (top-right to bottom-left)
                if (row + length <= grid.length && col - length >= 0) {
                    for (int i = 0; i < length; i++) {
                        grid[row + i][col - i] = word.charAt(i);
                    }return true; 
                }
                break;
            case 7:  // Diagonal (bottom-left to top-right)
                if (row - length >= 0 && col + length <= grid[0].length) {
                    for (int i = 0; i < length; i++) {
                        grid[row - i][col + i] = word.charAt(i);
                    }return true; 
                }
                break;
        }return false;
    } 

    private static void printSolutionGrid(char[][] grid) {
        for (char[] row : grid) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    private static void revealSolution() {
        System.out.println("Inside revealSolution()"); // Debugging output
    
        if (solvedWordy != null) {
            System.out.println("Solution available"); // Debugging output
            char[][] solutionGrid = new char[solvedWordy.length][solvedWordy[0].length];
            for (int i = 0; i < solvedWordy.length; i++) {
                for (int j = 0; j < solvedWordy[i].length; j++) {
                    if (!Character.isLetter(solvedWordy[i][j])) {
                        solutionGrid[i][j] = '_'; // Use underscore to represent empty cells
                    } else {
                        solutionGrid[i][j] = solvedWordy[i][j];
                    }
                }
            }
            printSolutionGrid(_wordy);
        } else {
            System.out.println("No solution available. Generate a word search first.");
        }
    
    
    
    
    
    }
}
