package sample.GUI;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class IntegerMask extends RecursiveTreeObject<IntegerMask> {
    private int value;

    public IntegerMask(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}