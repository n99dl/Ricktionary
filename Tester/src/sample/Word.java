package sample;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Word extends RecursiveTreeObject<Word> {
    private String word_target, word_explaint;
    private int id;

    public Word(String word_target, String word_explaint, int id) {
        this.word_target = word_target;
        this.word_explaint = word_explaint;
        this.id = id;
    }

    public StringProperty getWord_target() {
        return new SimpleStringProperty(this.word_target);
    }

    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    public StringProperty getWord_explaint() {
        return new SimpleStringProperty(this.word_explaint);
    }

    public void setWord_explaint(String word_explaint) {
        this.word_explaint = word_explaint;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void print() {
        System.out.println(this.id + "   " + this.word_target + "  " + this.word_explaint);
    }
}