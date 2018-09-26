package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        DictionaryManagement dictionaryManagement = new DictionaryManagement();
        dictionaryManagement.insertFromCommandLine();
        DictionaryCommandline.showAllwWords(dictionaryManagement);
    }
}
