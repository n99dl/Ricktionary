package sample.GUI;

import sample.GUI.IntegerMask;

import java.util.Comparator;

public class SortIntegerMask implements Comparator<IntegerMask>{
    public int compare(IntegerMask a, IntegerMask b){
        if(a.getValue() > b.getValue()) return 1;
        if(a.getValue() < b.getValue()) return -1;
        return 0;
    }
}