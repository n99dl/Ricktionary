package sample;

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
        try (Scanner scanner = new Scanner(new File("./src/sample/dictionaries_out.txt"))) {
            while (scanner.hasNextLine()) {
                i++;
//                if(i > 10000) break;
                String allLine;
                allLine = scanner.nextLine();
                String[] wordsArray = allLine.split("\t");
                ArrayList<String> partition = TextUtils.preProcessText(wordsArray[1], "<br />");
                String newExplain = "";
                for(String s : partition){
                    newExplain = newExplain + s + "\n";
                }
                dictionary.insertToTree(new Word(wordsArray[0].toLowerCase(), newExplain, i));
            }
        } catch (FileNotFoundException e) {
        }
    }

    public ArrayList<Word> dictionaryLookup(String keyWord) {
        ArrayList<Word> word = dictionary.searchInTree(keyWord, 1);
        return word;
    }

    public boolean addNewWord(String newWordTarget, String newWordExplain, int wordID){
        newWordTarget = newWordTarget.toLowerCase();
        ArrayList<Word> words = dictionaryLookup(newWordTarget);
        if(words.get(0).getWord_target().getValue().equals(newWordTarget)){
            return false;
        }
        dictionary.insertToTree(new Word(newWordTarget, newWordExplain, wordID));
        return true;
    }

    public void removeWord(String word){
        dictionary.removeFromTree(word);
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
