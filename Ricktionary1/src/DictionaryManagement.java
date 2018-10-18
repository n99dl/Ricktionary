import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryManagement {
    private Dictionary dictionary = new Dictionary();
    private Scanner scanner;

    /**
     * Insert words from command line
     */
    public void insertFromCommandLine() {
        int numberOfWord = 0;
        System.out.print("Enter number of words: ");
        numberOfWord = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the words list (each word in 2 row: word and explanation)");
        for (int i = 0; i < numberOfWord; i++) {
            String word, explain;
            word = scanner.nextLine();
            explain = scanner.nextLine();
            dictionary.insertWord(word, explain);
        }
    }

    /**
     * load scanner to dictionary management
     * @param scanner
     */
    public void loadScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Insert words from file
     */
    public void insertFromFile() {
        int i = -1;
        ArrayList<Word> listWords = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("Dictionaries.txt"));) {
            while (scanner.hasNextLine()) {
                i++;
                String allLine;
                allLine = scanner.nextLine();
                String[] wordsArray = allLine.split("\t");
                listWords.add(new Word(wordsArray[0], wordsArray[1], i));
                dictionary.insertWord(wordsArray[0], wordsArray[1]);
            }
            System.out.println("Load form file dictionaries.txt successfully.");
        } catch (FileNotFoundException e) {
        }
    }

    /**
     * Insert word from command line
     */
    public void insertWordFromCommandLine() {
        System.out.print("Enter new word: ");
        String newWord = scanner.nextLine();
        System.out.print("Enter new word explanation: ");
        String newWordExplain = scanner.nextLine();
        dictionary.insertWord(newWord, newWordExplain);
    }

    /**
     * remove word from command line
     */
    public void removeWordFromCommandLine() {
        System.out.print("Enter removed word: ");
        String removedWord = scanner.nextLine();
        dictionary.removeWord(removedWord);
    }

    /**
     * Edit word from command line
     */
    public void editWordFromCommandLine() {
        System.out.print("Enter edited word : ");
        String newWord = scanner.nextLine();
        System.out.print("Enter edited dexplanation ");
        String newWordExplain = scanner.nextLine();
        if (dictionary.removeWord(newWord))
        dictionary.insertWord(newWord, newWordExplain);
    }

    /**
     * Look up word using Trie
     */
    public void dictionaryLookup() {
        String keyWord;
        while (true) {
            System.out.println("Enter 0 to go back to main menu");
            System.out.print("Enter keyword: ");
            keyWord = scanner.nextLine();
            if (keyWord.charAt(0) == '0') {
                return;
            }
            Word word = dictionary.lookupWord(keyWord);
            if (word != null)
                word.print();
        }
    }

    /**
     * Search word with same prefix using trie
     */
    public void dictionarySearch() {
        String keyWord;
        while (true) {
            System.out.println("Enter 0 to go back to main menu");
            System.out.print("Enter keyword: ");
            keyWord = scanner.nextLine();
            if (keyWord.charAt(0) == '0') {
                return;
            }
            ArrayList<Word> wordList = dictionary.searchWord(keyWord);
            if (wordList != null) {
                for (Word word : wordList)
                    word.print();
            }
        }
    }

    /**
     * Get words list
     * @return list of word in Dictionary
     */
    public ArrayList<Word> getWordList() {
        return dictionary.getWordList();
    }
}
