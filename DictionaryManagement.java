package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryManagement {
    private Dictionary dictionary = new Dictionary();
    public void insertFromCommandLine(){
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
    public void insertFromFile(){
        int i = -1;
        try (Scanner scanner = new Scanner(new File("dictionaries.txt"));) {
            while (scanner.hasNextLine())
            {
                i++;
                String allLine ;
                allLine = scanner.nextLine();
                String[] wordsArray = allLine.split("\t");
                dictionary.insertWord(new Word(wordsArray[0], wordsArray[1], i ));
            }
        }catch (FileNotFoundException e) {
        }
    }
    public void dictionaryLookup(){
        String keyWord;
        Scanner scanner = new Scanner(System.in);
        keyWord = scanner.nextLine();
        Word word = dictionary.lookupWord(keyWord);
        word.print();
    }
    public ArrayList<Word> getWordList(){
        return dictionary.getWordList();
    }
}
