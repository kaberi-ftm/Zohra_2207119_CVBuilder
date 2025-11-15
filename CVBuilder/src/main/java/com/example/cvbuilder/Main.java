package com.example.cvbuilder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
public class Main extends Application {
    @Override
    public void start(Stage primarystage) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("Homescreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        URL css = getClass().getResource("Styles.css");
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        }
        primarystage.setTitle("CV Builder");
        primarystage.setScene(scene);
        primarystage.show();
    }


}
