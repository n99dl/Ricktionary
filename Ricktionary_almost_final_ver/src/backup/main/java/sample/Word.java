package sample;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Word extends RecursiveTreeObject<Word> {
    private String word_target, word_explain;
    private int id;

    public Word(String word_target, String word_explain, int id) {
        this.word_target = word_target;
        this.word_explain = word_explain;
        this.id = id;
    }

    public StringProperty getWord_target() {
        return new SimpleStringProperty(this.word_target);
    }

    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    public StringProperty getWord_explain() {
        return new SimpleStringProperty(this.word_explain);
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void print() {
        System.out.println(this.id + "   " + this.word_target + "  " + this.word_explain);
    }
}