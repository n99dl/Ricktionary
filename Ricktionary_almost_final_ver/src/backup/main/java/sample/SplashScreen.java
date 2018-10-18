package sample;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SplashScreen implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView splash;

    @FXML
    private Pane rootPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        assert splash != null : "fx:id=\"splash\" was not injected: check your FXML file 'SplashScreen.fxml'.";
//        System.out.println("asfadf");
        new Controllerz().start();
    }

    class Controllerz extends Thread {

        @Override
        public void run(){
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            long startTime = System.currentTimeMillis();
//            while(System.currentTimeMillis() - startTime < 10000){
//
//            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Parent root = null;
                    try{
                        root = FXMLLoader.load(Controllerz.this.getClass().getResource("/scene/sample.fxml"));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene scene = new Scene(root, 666, 666);
                    File f = new File("src/main/resources/main.css");
                    try {
                        scene.getStylesheets().add(f.toURI().toURL().toExternalForm());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    Stage stage = new Stage();
                    stage.setTitle("Ricktionary");
                    File fIcon = new File("src/main/resources/0e9.jpg");
                    try {
                        stage.getIcons().add(new Image(fIcon.toURI().toURL().toExternalForm()));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    stage.setScene(scene);
                    stage.show();

                    rootPane.getScene().getWindow().hide();
                }
            });

        }
    }
}
