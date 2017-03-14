public class WordSearchTest {
    public static void main(String[] args) {
        WordSearch puzzleNumOne = new WordSearch();
        WordSearch puzzleNumTwo = new WordSearch();

        puzzleNumOne.getWords();
        puzzleNumTwo.getWords();

        System.out.println("Puzzle number one: " + puzzleNumOne.returnWords());

        System.out.println("Puzzle number two: " + puzzleNumTwo.returnWords());

    }
}
