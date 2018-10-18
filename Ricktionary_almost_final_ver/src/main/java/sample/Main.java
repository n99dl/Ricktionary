package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent rootSplash = FXMLLoader.load(getClass().getResource("/scene/SplashScreen.fxml"));
        primaryStage.setTitle("Ricktionary");
        rootSplash.setId("pane");
        Scene scene = new Scene(rootSplash, 820, 566);
        scene.getStylesheets().add(String.valueOf(getClass().getResource("/css/splash.css")));
        primaryStage.initStyle(StageStyle.UNDECORATED);
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
