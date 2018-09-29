/**
 * Sample Skeleton for 'sample.fxml' Controller Class
 */

package sample;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

import com.jfoenix.controls.JFXTextArea;
import javafx.scene.input.MouseEvent;
import com.jfoenix.controls.JFXTextField;

public class Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private JFXTextField textField;

    @FXML // fx:id="treeView"
    private JFXTreeTableView<Word> treeView; // Value injected by FXMLLoader

    @FXML // fx:id="textArea"
    private JFXTextArea textArea; // Value injected by FXMLLoader

//    @FXML
//    void changeThings(MouseEvent event) {
//        treeView.getSelectionModel().select(4);
//        treeView.scrollTo(4);
//    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        DictionaryManagement dicMan = new DictionaryManagement();
        dicMan.insertFromFile();
        ArrayList<Word> dicList = dicMan.getWordList();
        assert treeView != null : "fx:id=\"treeView\" was not injected: check your FXML file 'sample.fxml'.";
        JFXTreeTableColumn<Word, String> wordList = new JFXTreeTableColumn<Word, String>("Index");
        wordList.setPrefWidth(160);
        wordList.setSortable(false);
        wordList.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Word, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Word, String> param) {
                    return param.getValue().getValue().getWord_target();
            }
        });
        ObservableList<Word> listWords = FXCollections.observableArrayList();
        for(Word word : dicList){
            listWords.add(word);
        }
//        listWords.add(new Word("Hello", "Xin chào"));
//        listWords.add(new Word("House", "Ngôi nhà"));
//        listWords.add(new Word("High", "Phê"));
//        listWords.add(new Word("Hello2", "Xin chào"));
//        listWords.add(new Word("House2", "Ngôi nhà"));
//        listWords.add(new Word("High2", "Phê"));
//        listWords.add(new Word("Hello3", "Xin chào"));
//        listWords.add(new Word("House3", "Ngôi nhà"));
//        listWords.add(new Word("High3", "Phê"));

        final TreeItem<Word> root = new RecursiveTreeItem<Word>(listWords, RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(wordList);
        treeView.setRoot(root);
        treeView.setShowRoot(false);

        treeView.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                if(mouseEvent.getClickCount() == 1)
                {
                    TreeItem<Word> item = treeView.getSelectionModel().getSelectedItem();
                    System.out.println("Selected Text : " + item.getValue().getWord_explaint().getValue());
                    textArea.setDisable(false);
                    textArea.setText(item.getValue().getWord_explaint().getValue());
                    textField.setText(item.getValue().getWord_target().getValue());
                }
            }
        });

        textField.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    String input = textField.getText();
                    Word wordFound = dicMan.dictionaryLookup(input);
                    if (wordFound == null){
                        textArea.setText("Not found!");
                    } else{
                        treeView.getSelectionModel().select(wordFound.getId());
                        treeView.scrollTo(wordFound.getId());
                        TreeItem<Word> item = treeView.getSelectionModel().getSelectedItem();
                        System.out.println("Selected Text : " + item.getValue().getWord_explaint().getValue());
                        textArea.setDisable(false);
                        textArea.setText(item.getValue().getWord_explaint().getValue());
                    }
                }
            }
        });

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            String input = textField.getText();
            Word wordFound = dicMan.dictionaryLookup(input);
            if (wordFound != null){
                treeView.scrollTo(wordFound.getId());
            }
        });
//        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
////                if(newValue){
//                    String input = textField.getText();
//                    Word wordFound = dicMan.dictionaryLookup(input);
//                    if (wordFound == null){
//                        textArea.setText("Not found!");
//                    } else{
//                        treeView.getSelectionModel().select(wordFound.getId());
//                        treeView.scrollTo(wordFound.getId());
//                        TreeItem<Word> item = treeView.getSelectionModel().getSelectedItem();
//                        System.out.println("Selected Text : " + item.getValue().getWord_explaint().getValue());
//                        textArea.setDisable(false);
//                        textArea.setText(item.getValue().getWord_explaint().getValue());
//                    }
//                }
////            }
//        });
    }

//    class Word extends RecursiveTreeObject<Word> {
//
//        StringProperty word;
//        StringProperty explain;
//
//        public Word(String word, String explain) {
//            this.word = new SimpleStringProperty(word);
//            this.explain = new SimpleStringProperty(explain);
//        }
//    }
}
