package com.company;

import java.util.ArrayList;

public class DictionaryCommandline {
    public static void showAllwWords(DictionaryManagement dictionaryManagement){
        System.out.println("No  | English          |Vietnamese");
        for (Word temp: dictionaryManagement.getWordList()){
            System.out.println(temp.getId() + "  " + temp.getWord_target() + "          " + temp.getWord_explaint());
        }
    }
}
