import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class DictionaryManagement {
    private Dictionary dictionary;

    public DictionaryManagement(){
        dictionary = new Dictionary();
    }

    public void insertFromCommandLine() {
        int numberOfWord = 0;
        try (Scanner scanner = new Scanner(System.in);) {
            numberOfWord = scanner.nextInt();
            scanner.nextLine();
            for (int i = 0; i < numberOfWord; i++) {
                String word, explain;
                word = scanner.nextLine();
                explain = scanner.nextLine();
                dictionary.insertWord(word,explain);
            }
        }
    }

    public void insertWordFromCommandLine(){
        Scanner scanner = new Scanner(System.in);
        String newWord = scanner.nextLine();
        String newWordExplain = scanner.nextLine();
        dictionary.insertWord(newWord, newWordExplain);
    }

    public void deleteWordFromCommandLine(){
        Scanner scanner = new Scanner(System.in);
        String newWord = scanner.nextLine();
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
            }
        } catch (FileNotFoundException e) {
        }
    }

    public void sortWord(){
        dictionary.sort();
    }

    public void dictionaryLookup() {
        String keyWord;
        Scanner scanner = new Scanner(System.in);
        keyWord = scanner.nextLine();
        Word word = dictionary.lookupWord(keyWord);
        word.print();
        scanner.close();
    }

    public ArrayList<Word> getWordList() {
        return dictionary.getWordList();
    }
}
