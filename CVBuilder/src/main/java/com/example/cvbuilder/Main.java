package com.example.cvbuilder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
public class Main extends Application {
    @Override
    public void start(Stage primarystage) throws IOException {
        // Configure logging
        try (InputStream is = Main.class.getResourceAsStream("/logging.properties")) {
            if (is != null) {
                LogManager.getLogManager().readConfiguration(is);
            }
        } catch (Exception ex) {
            // Fallback will use default Java logging config
            ex.printStackTrace();
        }

        // Catch uncaught exceptions to avoid silent failures
        Thread.setDefaultUncaughtExceptionHandler((t, e) ->
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Uncaught exception on thread " + t.getName(), e)
        );

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cvbuilder/Homescreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        URL css = getClass().getResource("/com/example/cvbuilder/Styles.css");
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        }
        primarystage.setTitle("CV Builder");
        primarystage.setScene(scene);
        primarystage.setMinWidth(600);
        primarystage.setMinHeight(400);
        primarystage.show();
    }


}
