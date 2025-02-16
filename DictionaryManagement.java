import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class DictionaryManagement {
    private Trie dictionary;

    public DictionaryManagement(){
        dictionary = new Trie();
    }

    public void insertFromFile() {
        int i = -1;
        try (Scanner scanner = new Scanner(new File("./src/sample/Dictionaries.txt"));) {
            while (scanner.hasNextLine()) {
                i++;
                String allLine;
                allLine = scanner.nextLine();
                String[] wordsArray = allLine.split("\t");
//                System.out.println(wordsArray[0]);
                dictionary.insertToTree(new Word(wordsArray[0], wordsArray[1], i));
            }
        } catch (FileNotFoundException e) {
        }
    }

    public Word dictionaryLookup(String keyWord) {
        ArrayList<Word> word = dictionary.searchInTree(keyWord, 1);
        if (word.size() == 0) {
            return null;
        } else {
            return word.get(0);
        }
    }

    public void dictionarySearch(String keuWord) {
        ArrayList<Word> wordList = dictionary.searchInTree(keuWord,50);
        if (wordList.isEmpty()) {
            for (Word word : wordList)
                word.print();
        }
    }

    public ArrayList<Word> getWordList() {
        ArrayList<Word> res = dictionary.getAllWords();
        int i = 0;
        for (Word word : res){
            word.setId(i++);
        }
        return res;
    }
}