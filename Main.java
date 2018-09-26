package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        DictionaryManagement dictionaryManagement = new DictionaryManagement();
        dictionaryManagement.insertFromFile();
        DictionaryCommandline.showAllwWords(dictionaryManagement);
        Word temp = new Word("Hello","Xin Chao", 1);
        temp.print();
    }
}
