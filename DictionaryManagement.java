
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
                dictionary.insertWord(new Word(word, explain, i));
            }
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
            }
            Collections.sort(listWords, new SortWords()); // Sort words
            int count = 0; // 0-indexed
            for(Word word : listWords){
                word.setId(count++); // Set new ID
                dictionary.insertWord(word); 
            }
        } catch (FileNotFoundException e) {
        }
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
