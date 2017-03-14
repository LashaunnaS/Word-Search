import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.io.FileNotFoundException;


public class WordSearch {

    /**
     * Private instance variables of my class.
     */
    private char[][] grid;
    private ArrayList<String> wordList;
    private int puzzleSize;

    /**
     * Constructor creates objects of my class.
     */
    public WordSearch() {
        puzzleSize = getNumberOfWords();
        grid = new char[puzzleSize][puzzleSize];
        wordList = new ArrayList<>();
    }

    /**
     * Prompt user for the number of words for their word search.
     */
    private int getNumberOfWords() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please enter the number of words you would like in your word search:");
        int numOfWords = 0;
        while (numOfWords < 5 || numOfWords > 20) {
            try {
                numOfWords = keyboard.nextInt();
                if (numOfWords < 5 || numOfWords > 20)
                    System.out.println("Your number must be between 5 and 20, try again:");
            } catch (InputMismatchException e) {
                System.out.println("Only numbers are accepted, try again:");
                keyboard.nextLine();
            }
        }
        return numOfWords;
    }


    /**
     * This method will continue to prompt the user
     * for the amount of words to fill their puzzle.
     * Once the newWord is validated, it will be upper cased and added to the wordList.
     */
    public void getWords() {
        Scanner keyboard = new Scanner(System.in);
        for (int i = 0; i < puzzleSize; i++) {
            try {
                System.out.println("Enter a word you would like in your puzzle:");
                String newWord = keyboard.nextLine().toUpperCase();
                validateWord(newWord);
                wordList.add(i, newWord);
            } catch (Exception e) {
                i--;
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * This method will return all the words from the wordList as Strings,
     * with each word on a new line.
     */
    public String returnWords() {

        String wordString = "";
        System.out.println("\nHere are the words to find:");
        wordList.sort(String::compareToIgnoreCase);
        for (int i = 0; i < puzzleSize; i++) {
            wordString = wordString + wordList.get(i) + "\n";
        }
        return wordString;
    }


    /**
     * This method will validate that the word is longer than 1 character,
     * have no spaces,
     * be unique,
     * and fit within the bounds of the puzzle.
     * If any of these are true, it will throw an exception.
     */
    private void validateWord(String newWord) {

        if (wordList.contains(newWord))
            throw new IllegalArgumentException("Words must be unique and not repeat.");
        else if (newWord.length() < 2)
            throw new IllegalArgumentException("Words must have more than one character.");
        else if (newWord.contains(" "))
            throw new IllegalArgumentException("Words must not consist of any spaces.");
        else if (newWord.length() > puzzleSize)
            throw new IllegalArgumentException("Words must fit within the bounds of the puzzle size.");
    }

    /**
     * This method will shuffle the wordList then add it to the puzzle starting in random row locations
     */
    private void wordsToPuzzle() {

        Random rand = new Random();
        Collections.shuffle(wordList);
        System.out.println();
        for (int col = 0; col < puzzleSize; col++) {
            int num = (puzzleSize) - (wordList.get(col).length() - 1);
            int randPosition = rand.nextInt(num);
            for (int row = 0; row < wordList.get(col).length(); row++) {
                grid[row + randPosition][col] = wordList.get(col).charAt(row);
            }
        }
    }

    /**
     * This method will fill the empty spaces in the puzzle with a random letter
     */
    private void fillEmpties() {
        for (int i = 0; i < puzzleSize; i++) {
            for (int j = 0; j < puzzleSize; j++) {
                grid[i][j] = ' ';
            }
        }
    }

    /**
     * This method will randomly pick a letter in the alphabet
     */
    private void fill() {

        char arr[] = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        Random letter = new Random();

        for (int col = 0; col < puzzleSize; col++) {
            for (int row = 0; row < puzzleSize; row++) {

                int randLetter = letter.nextInt(arr.length);
                if (grid[col][row] == ' ') {
                    grid[col][row] = arr[randLetter];
                }

            }
        }
    }

    /**
     * This method will print out your puzzle
     */
    private void printTest() {
        System.out.println("Here's your puzzle:\n");
        for (int row = 0; row < puzzleSize; row++) {
            for (int col = 0; col < puzzleSize; col++) {
                System.out.printf("%3s", grid[row][col]);
            }
            System.out.println();
        }
    }


    /**
     * This main method creates a new instance of the WordSearch class
     * and calls methods to get words from the user
     * and prints the words to the screen.
     */
    public static void main(String[] args) throws FileNotFoundException {
        WordSearch myPuzzleObject = new WordSearch();
        myPuzzleObject.fillEmpties();
        myPuzzleObject.getWords();
        myPuzzleObject.wordsToPuzzle();
        myPuzzleObject.fill();
        myPuzzleObject.printTest();
        System.out.print(myPuzzleObject.returnWords());
        myPuzzleObject.printToFile(myPuzzleObject.printString());
    }

    private String printString() {

        String puzzle;
        StringBuilder sb = new StringBuilder();
        StringBuilder wordBuilder = new StringBuilder();

        for (char[] print : grid) {
            sb.append(print);
            sb.append("\n");
        } puzzle = sb.toString();

        for (String words : wordList) {
            wordBuilder.append(words);
            wordBuilder.append("\n");
        }

        return ("\n" + puzzle + "\nHere are the words to find:\n" + wordBuilder.toString());
    }

    /**
     * This method will allow the user to continuously write to the file
     * until the user enters the string "exit"
     */
    private void printToFile(String puzzle) {

        try
        {
            PrintWriter outputStream = new PrintWriter("puzzle.txt");
            outputStream.print(puzzle);
            outputStream.close();
        }
        catch (IOException e)
        {
            System.err.print("Sorry we cannot write to puzzle.txt");
        }

    }
}