package com.company;

import java.util.ArrayList;

public class Dictionary {
    private ArrayList<Word> wordList = new ArrayList<Word>();
    public void insertWord(Word word){
        wordList.add(word);
    }

    public ArrayList<Word> getWordList() {
        return wordList;
    }
    public Word getWord(int index){
        return wordList.get(index);
    }
}
