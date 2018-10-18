import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryManagement {
    private Dictionary dictionary = new Dictionary();
    private Scanner scanner;

    public void insertFromCommandLine() {
        int numberOfWord = 0;
        numberOfWord = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < numberOfWord; i++) {
            String word, explain;
            word = scanner.nextLine();
            explain = scanner.nextLine();
            dictionary.insertWord(word, explain);
        }
    }

    public void insertFromFile() {
        int i = -1;
        ArrayList<Word> listWords = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("dictionaries.txt"));) {
            while (scanner.hasNextLine()) {
                i++;
                String allLine;
                allLine = scanner.nextLine();
                String[] wordsArray = allLine.split("\t");
                listWords.add(new Word(wordsArray[0], wordsArray[1], i));
                dictionary.insertWord(wordsArray[0], wordsArray[1]);
                scanner.close();
            }
        } catch (FileNotFoundException e) {
        }
    }

    public void dictionaryLookup() {
        String keyWord;
        keyWord = scanner.nextLine();
        System.out.println(keyWord + "test");
        Word word = dictionary.lookupWord(keyWord);
        if (word != null)
            word.print();
    }

    public ArrayList<Word> getWordList() {
        return dictionary.getWordList();
    }
}
