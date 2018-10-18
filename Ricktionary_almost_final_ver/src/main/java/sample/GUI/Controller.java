/**
 * Sample Skeleton for 'sample.fxml' Controller Class
 */

package sample.GUI;

import com.jfoenix.controls.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.List;

import com.jfoenix.controls.JFXTabPane;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.scene.input.MouseEvent;
import org.fxmisc.richtext.*;
import sample.dictionary.DictionaryManagement;
import sample.dictionary.Word;
import sample.googleApi.GoogleAPITextToSpeech;
import sample.googleApi.GoogleAPITranslate;


public class Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private JFXTextField textField;

    @FXML
    private StackPane rootPane;

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private JFXPopup popup;

    @FXML
    private JFXTextField addTarget;

    @FXML
    private JFXButton addWord;

    @FXML
    private JFXTextArea addExplain;

    @FXML // fx:id="treeView"
    private JFXTreeTableView<IntegerMask> treeView; // Value injected by FXMLLoader

    @FXML // fx:id="textArea"
    private JFXTextArea textArea; // Value injected by FXMLLoader

    @FXML
    private JFXButton resetChange;

    @FXML
    private JFXTextField showTarget;

    @FXML
    private JFXTextField showSound;

    @FXML
    private JFXTextArea showExplain;

    @FXML
    private ImageView hisButton;
    @FXML
    private JFXButton googleButton;

    @FXML
    private JFXTextArea googleTrans;

    @FXML
    private JFXTextArea googleText;

    @FXML
    private ImageView speakerBut;

    @FXML
    private InlineCssTextArea inlineText;

    private List<Word> hisWord = new ArrayList<>();
    private DictionaryManagement dicMan = new DictionaryManagement();
    private JFXTreeTableColumn<IntegerMask, String> wordList;
    private ObservableList<IntegerMask> listWords;
    private boolean canScroll = true;
    ArrayList<Word> dicList;
    HashMap<IntegerMask, Word> mp = new HashMap<>();
    boolean isFirstTime = true;
    ArrayList<Integer> listPoS = new ArrayList<>();


    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        // Dictionary management initialized
        dicMan.insertFromDatabase();
        dicList = dicMan.getWordList();
        int i = 0;
        for (Word word : dicList) {
            IntegerMask id = new IntegerMask(i);
            mp.put(id, word);
            i++;
        }

        // Tree view created
        assert treeView != null : "fx:id=\"treeView\" was not injected: check your FXML file 'sample.fxml'.";
        wordList = new JFXTreeTableColumn<IntegerMask, String>("");
        wordList.setPrefWidth(160);
        wordList.setSortable(false);
        wordList.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<IntegerMask, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<IntegerMask, String> param) {
                return mp.get(param.getValue().getValue()).getWord_target();
            }
        });
        listWords = FXCollections.observableArrayList();
        for (IntegerMask key : mp.keySet()) {
            listWords.add(key);
        }
        Collections.sort(listWords, new SortIntegerMask());

        final TreeItem<IntegerMask> root = new RecursiveTreeItem<IntegerMask>(listWords, RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(wordList);
        treeView.setRoot(root);
        treeView.setShowRoot(false);

        // Right mouse clicked and Left mouse clicked Event on tree View
        treeView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                TreeItem<IntegerMask> item = treeView.getSelectionModel().getSelectedItem();
                if (mouseEvent.isPrimaryButtonDown()) {
                    addHistory(mp.get(item.getValue()));
                    System.out.println("Selected Text : " + mp.get(item.getValue()).getId() + ' ' + mp.get(item.getValue()).getWord_target().getValue());
                    setTextOnArea(mp.get(item.getValue()).getWord_explain().getValue());
                    canScroll = false;
                    textField.setText(mp.get(item.getValue()).getWord_target().getValue());
                    canScroll = true;
                } else if (mouseEvent.isSecondaryButtonDown()) {
                    initPopupRE();
                    popup.show(treeView, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, mouseEvent.getX(), mouseEvent.getY());
                }
            }
        });

        // Enter pressed Event on tree View
        treeView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    TreeItem<IntegerMask> item = treeView.getSelectionModel().getSelectedItem();
                    addHistory(mp.get(item.getValue()));
                    System.out.println("Selected Text : " + mp.get(item.getValue()).getWord_explain().getValue());
                    setTextOnArea(mp.get(item.getValue()).getWord_explain().getValue());
                    textField.setText(mp.get(item.getValue()).getWord_target().getValue());
                }
            }
        });

        // Press Enter event in textField
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    String input = textField.getText();
                    ArrayList<Word> listWords = dicMan.dictionaryLookup(input);
                    if (listWords.get(0).getId() == -1) {
                        initPopupDYM(listWords.subList(1, (listWords.size())), "Did you mean...");
                        popupShow();
                    } else {
                        Word wordFound = listWords.get(0);
                        setSelectionText(wordFound);
                    }
                }
            }
        });

        // Tree view changes as textField changes
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            String input = textField.getText();
            ArrayList<Word> wordLists = dicMan.dictionaryLookup(input);
            Word wordFound = wordLists.get(0);
            if (wordFound.getId() != -1 && canScroll) {
                treeView.scrollTo(wordFound.getId());
            }
        });

        /// Show Target
        showTarget.setMinWidth(2);
        showTarget.setPrefWidth(50);
        showTarget.setMaxWidth(400);
        showTarget.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                showTarget.setPrefWidth(TextUtils.computeTextWidth(showTarget.getFont(),
                        showTarget.getText(), 0.0D) + 12 + 14 + 2); // why 7? Totally trial number.
            }
        });

        /// Show Pronunciation
        showSound.setMinWidth(2);
        showSound.setPrefWidth(50);
        showSound.setMaxWidth(400);
        showSound.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                showSound.setPrefWidth(TextUtils.computeTextWidth(showSound.getFont(),
                        showSound.getText(), 0.0D) + 12 + 14 + 2); // why 7? Totally trial number
                speakerBut.setLayoutX(252 + TextUtils.computeTextWidth(showSound.getFont(),
                        showSound.getText(), 0.0D) + 12 + 14 + 5);
            }
        });
    }

    /*
    Normal dialog show
     */
    private void showDialog(String bodyText, String buttonText) {
        BoxBlur blur = new BoxBlur(3, 3, 3);
        tabPane.setEffect(blur);
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXButton button = new JFXButton(buttonText);
        button.getStyleClass().add("dialog-button");
        JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
            dialog.close();
        });
        dialogLayout.setHeading(new Text(bodyText));
        dialogLayout.setActions(button);
        dialog.getStyleClass().add("dialog-head");
        dialog.show();
        dialog.setOnDialogClosed((JFXDialogEvent event) -> {
            tabPane.setEffect(null);
        });
    }

    /*
        Yes / No dialog show
     */
    private void showYNDialog(String bodyText, List<JFXButton> controls) {
        BoxBlur blur = new BoxBlur(3, 3, 3);
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(rootPane, dialogLayout, JFXDialog.DialogTransition.TOP);
        dialogLayout.setHeading(new Text(bodyText));
        dialogLayout.setActions(controls);
        dialog.getStyleClass().add("dialog-head");
        dialog.show();
        controls.forEach(controlButton -> {
            controlButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
                dialog.close();
            });
        });
        dialog.setOnDialogClosed((JFXDialogEvent event) -> {
            tabPane.setEffect(null);
        });
        tabPane.setEffect(blur);
    }

    /*
        Is string a less alphabetically than string b
     */
    private boolean isSmaller(String a, String b) {
        int len = Math.min(a.length(), b.length());
        for (int i = 0; i < len; i++) {
            if (a.charAt(i) < b.charAt(i)) return true;
            else if (a.charAt(i) > b.charAt(i)) return false;
        }
        if (a.length() < b.length()) return true;
        else return false;
    }

    /*
    Add / Edit new word
     */
    @FXML
    void addNewWord(MouseEvent event) {
        String newWordTarget = addTarget.getText().toLowerCase();
        String newWordExplain = addExplain.getText();
        if (addTarget.isDisable()) {
            /// Edit Mode
            Word newWord = dicMan.dictionaryLookup(newWordTarget).get(0);
            newWord.setWord_explain(newWordExplain);
            dicMan.updateWord(newWordTarget, newWordExplain);
            showDialog("All Done!", "Thankssss!!");
            addExplain.setText("");
            addTarget.setText("");
            addTarget.setDisable(false);
            tabPane.getSelectionModel().select(0);
        } else {
            /// Add Mode
            if (newWordTarget.equals("") || newWordExplain.equals("")) {
                /// Error
                showDialog("Don't let Word Target or Word Explain be empty :<", "Hm, my mistake.");
            } else {
//                newWordExplain = "<C><F><I><N><Q>" + newWordExplain + "</Q></N></I></F></C>";
                if (dicMan.addNewWord(newWordTarget, newWordExplain, listWords.size())) {
                    Word newWord = dicMan.dictionaryLookup(newWordTarget).get(0);
                    IntegerMask ID = new IntegerMask(listWords.size());
                    mp.put(ID, newWord);
                    int nextID;
                    int l = -1, r = listWords.size(), mid;
                    while (r - l > 1) {
                        mid = (l + r) / 2;
                        if (isSmaller(mp.get(listWords.get(mid)).getWord_target().getValue(), newWordTarget)) {
                            l = mid;
                        } else r = mid;
                    }
                    nextID = r;
                    System.out.println(nextID);
                    newWord.setId(nextID);
                    for (int i = nextID; i < listWords.size(); i++) {
                        int thisID = mp.get(listWords.get(i)).getId();
                        mp.get(listWords.get(i)).setId(thisID + 1);
                    }
                    listWords.add(ID);
                    for (int i = nextID; i < listWords.size() - 1; i++) {
                        Word temp = mp.get(listWords.get(i));
                        mp.put(listWords.get(i), mp.get(listWords.get(listWords.size() - 1)));
                        mp.put(listWords.get(listWords.size() - 1), temp);
                    }
                    treeView.getColumns().setAll(wordList);
                    /// Message
                    setSelectionText(newWord);
                    showDialog("All Done!", "Thankssss!!");
                    addExplain.setText("");
                    addTarget.setText("");
                    tabPane.getSelectionModel().select(0);
                } else {
                    /// Error
                    showDialog("This word has already been in the dictionary :)", "Oh okay :>");
                }
            }
        }
    }

    /*
    Delete word
     */
    private void deleteWord(IntegerMask id) {
        System.out.println("Selected To Be Removed Word: " + mp.get(id).getWord_target().getValue());
        String toBeDeletedWord = mp.get(id).getWord_target().getValue();
        mp.put(id, null);
        for (int i = id.getValue(); i < listWords.size() - 1; i++) {
            Word temp = mp.get(listWords.get(i));
            mp.put(listWords.get(i), mp.get(listWords.get(i + 1)));
            mp.put(listWords.get(i + 1), temp);
        }
        listWords.remove(listWords.size() - 1);
        for (int i = id.getValue(); i < listWords.size(); i++) {
            mp.get(listWords.get(i)).setId(i);
        }
        dicMan.removeWord(toBeDeletedWord);
        treeView.getColumns().setAll(wordList);
    }

    /*
    Edit word
     */
    private void editWord(IntegerMask id) {
        System.out.println("Selected To Be Edited Word: " + mp.get(id).getWord_target().getValue());
        Word toBeEditedWord = mp.get(id);
        addTarget.setText(toBeEditedWord.getWord_target().getValue());
        addExplain.setText(toBeEditedWord.getWord_explain().getValue());
        addTarget.setDisable(true);
        tabPane.getSelectionModel().select(1);
    }

    /*
    Reset word button
     */
    @FXML
    void resetChangeWord(MouseEvent event) {
        addExplain.setText("");
        addTarget.setText("");
        addTarget.setDisable(false);
    }

    /*
    History pop-up
     */
    @FXML
    void hisPopup(MouseEvent event) {
        initPopupDYM(hisWord, "History: ");
        popup.show(hisButton, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.getX(), event.getY());
    }

    /*
    Popup - show
     */
    private void popupShow() {
        popup.show(textField, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
    }

    /*
        Set selection text on word explain Pane
     */
    private void setSelectionText(Word word) {
        addHistory(word);
        treeView.getSelectionModel().select(word.getId());
        treeView.setFocusTraversable(true);
        treeView.scrollTo(word.getId());
        TreeItem<IntegerMask> item = treeView.getSelectionModel().getSelectedItem();
        System.out.println("Selected Text : " + mp.get(item.getValue()).getWord_explain().getValue());
        setTextOnArea(mp.get(item.getValue()).getWord_explain().getValue());
        textField.setText(mp.get(item.getValue()).getWord_target().getValue());
    }

    /*
    Format text when showed on word explain Pane
     */
    private void setTextOnArea(String content) {
        listPoS.clear();
        textArea.setDisable(false);
        textArea.setPromptText("");
        ArrayList<String> listE = TextUtils.preProcessText(content, "\n");
        /// Show Target and Sound
        showTarget.setVisible(true);
        showSound.setVisible(true);
        if (listE.get(0).indexOf("/") >= 0) {
            showSound.setVisible(true);
            String[] part1 = listE.get(0).split("/");
            String sTarget = part1[0].substring(1, part1[0].length());
            String sSound = "/" + part1[1] + "/";
            showTarget.setText(sTarget);
//            GoogleAPITextToSpeech demo = new GoogleAPITextToSpeech(sTarget);
//            demo.start();
            showSound.setText(sSound);
            speakerBut.setVisible(true);
        } else {
            String sTarget = listE.get(0).substring(1, listE.get(0).length());
            showTarget.setText(sTarget);
            showSound.setText("None");
            speakerBut.setVisible(false);
        }
        /// Show Explain
        showExplain.setVisible(true);
        listE.remove(0);
        String sExplain = "";
        int count = 0;
        for (String c : listE) {
            if (c.length() > 1 && (c.charAt(0) == '*' || c.charAt(0) == '@')) {
                listPoS.add(count);
                if (c.charAt(0) == '*')
                    c = c.substring(1);
                c += ' ';
            } else {
                c = ' ' + c;
            }
            count++;
            sExplain = sExplain + c + "\n";
        }
        System.out.println(count);
        showExplain.setText(sExplain);
        inlineText.replaceText(sExplain);
        for (int x = 0; x < count; x++) {
            inlineText.setStyle(x, "-fx-font-family: Leelawadee UI; -fx-font-size: 15px; -fx-fill: #49566A;  -fx-font-weight: bold;");
        }
        for (Integer x : listPoS) {
            inlineText.setStyle(x, "-fx-font-family: Leelawadee UI; -fx-font-size: 15px; -rtfx-background-color: #7A8AA0 ; -fx-fill: #FAEF66; -fx-font-weight: bold; -rtfx-border-radius: 100px; ");
        }
    }

    /*
    History word controller
     */
    private void addHistory(Word word) {
        if (hisWord.isEmpty() == false && word.getWord_target().getValue().equals(hisWord.get(0).getWord_target().getValue()) == false) {
            if (hisWord.size() == 5) hisWord.remove(hisWord.size() - 1);
            hisWord.add(0, word);
        }
        if (hisWord.isEmpty() == true) {
            hisWord.add(0, word);
        }
    }

    /*
    Show Remove / Edit pop-up
     */
    private void initPopupRE() {
        JFXButton labelRemove = new JFXButton("Remove");
        JFXButton labelEdit = new JFXButton("Edit");
        JFXListView<JFXButton> list = new JFXListView<>();
        list.getItems().add(labelRemove);
        list.getItems().add(labelEdit);
        labelRemove.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                popup.hide();
                TreeItem<IntegerMask> item = treeView.getSelectionModel().getSelectedItem();
                JFXButton buttonYES = new JFXButton("Let it go!");
                buttonYES.getStyleClass().add("dialog-button");
                JFXButton buttonNO = new JFXButton("Not sure...");
                buttonNO.getStyleClass().add("dialog-button");
                buttonYES.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
                    deleteWord(item.getValue());
                    showDialog("All work done!!", "Good job!! :>");
                });
                showYNDialog("Really want to say good bye to \"" + mp.get(item.getValue()).getWord_target().getValue() + "\"", Arrays.asList(buttonYES, buttonNO));
            }
        });
        labelEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                popup.hide();
                TreeItem<IntegerMask> item = treeView.getSelectionModel().getSelectedItem();
                editWord(item.getValue());
            }
        });
        popup = new JFXPopup(list);

    }

    /*
    Show Did you mean pop-up
     */
    private void initPopupDYM(List<Word> listOfWords, String text) {
        JFXButton label = new JFXButton(text);
        label.setDisable(true);
        JFXListView<JFXButton> list = new JFXListView<>();
        list.getItems().add(label);
        label.setPadding(new Insets(5));
        for (Word word : listOfWords) {
            JFXButton bt = new JFXButton(word.getWord_target().getValue());
            bt.setPadding(new Insets(5));
            bt.setId(Integer.toString(word.getId()));
            list.getItems().add(bt);
            bt.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    setSelectionText(word);
                    popup.hide();
                }
            });
            list.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        ObservableList<JFXButton> item = list.getSelectionModel().getSelectedItems();
//                        item.get(0).getOnMouseClicked();
                        int id = Integer.parseInt(item.get(0).getId());
                        setSelectionText(new Word(item.get(0).getText(), "1", id));
                        popup.hide();
                    }
                }
            });

        }

        popup = new JFXPopup(list);
    }

    /*
    Play word's sound
     */
    @FXML
    void playWord(MouseEvent event) throws MalformedURLException {
//        File f = new File("src/main/resources/audio/output.mp3");
////        File f = new File("output.mp3");
//        Media media = new Media(f.toURI().toURL().toExternalForm());
//        MediaPlayer mp = new MediaPlayer(media);
//        mp.play();

        GoogleAPITextToSpeech demo = new GoogleAPITextToSpeech(showTarget.getText());
        demo.start();
        System.out.println("Playing...");
    }

    /*
    Check if Internet is available
     */
    private boolean netIsAvailable() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }

    /*
    Google API translation
     */
    @FXML
    void getTranslation(MouseEvent event) {
        if (netIsAvailable()) {
            String[] inputs = googleText.getText().split("\n");
            String output = "";
            GoogleAPITranslate apiT = new GoogleAPITranslate(inputs, googleTrans);
            apiT.start();
        } else {
            showDialog("Please check your Internet connection :<", "Okay I will :(");
        }
    }

}
