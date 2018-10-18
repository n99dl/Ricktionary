package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Paths;

public class Main extends Application {

    @FXML
    private ImageView splash;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent rootSplash = FXMLLoader.load(getClass().getResource("/scene/SplashScreen.fxml"));
        primaryStage.setTitle("Ricktionary");
        File fIcon = new File("src/main/resources/0e9.jpg");
        primaryStage.getIcons().add(new Image(fIcon.toURI().toURL().toExternalForm()));
        rootSplash.setId("pane");
        Scene scene = new Scene(rootSplash, 600, 300);
        File f = new File("src/main/resources/splash.css");
        scene.getStylesheets().add(f.toURI().toURL().toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
//        long startTime = System.currentTimeMillis();
//        while(System.currentTimeMillis() - startTime > 10000){
//
//        }
//       // Thread.sleep(1000);
//        Parent rootMain= FXMLLoader.load(getClass().getResource("sample.fxml"));
//        thisStage.hide();
//        scene = new Scene(rootMain, 666, 666);
//        f = new File("./src/sample/main.css");
//        scene.getStylesheets().add(f.toURI().toURL().toExternalForm());
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
