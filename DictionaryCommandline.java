package com.company;

import java.util.ArrayList;

public class DictionaryCommandline {
    public static void showAllwWords(DictionaryManagement dictionaryManagement){
        System.out.println("No" + "\t" + "| English" + "\t" + "|Vietnamese");
        for (Word temp: dictionaryManagement.getWordList()){
            System.out.println((temp.getId()+1) + "\t" + temp.getWord_target() + "\t" + temp.getWord_explaint());
        }
    }
}
