import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryManagement {
    private Dictionary dictionary = new Dictionary();

    public void insertFromCommandLine() {
        int numberOfWord = 0;
        try (Scanner scanner = new Scanner(System.in);) {
            numberOfWord = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < numberOfWord; i++) {
                String word, explain;
                word = scanner.nextLine();
                explain = scanner.nextLine();
                dictionary.insertWord(new Word(word, explain, i));
            }
        }
    }

    public ArrayList<Word> getWordList() {
        return dictionary.getWordList();
    }
}
