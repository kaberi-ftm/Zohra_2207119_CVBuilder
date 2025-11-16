package com.example.cvbuilder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeScreenController {
    private static final Logger LOGGER = Logger.getLogger(HomeScreenController.class.getName());

    @FXML
    private void handleCreateNewCV(ActionEvent event) {
        LOGGER.info("Navigating to Form screen (Create New CV)");
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/cvbuilder/FormScreen.fxml")
            );

            Parent formScreenRoot = loader.load();
            Scene formScene = new Scene(formScreenRoot);

            formScene.getStylesheets().add(
                    getClass().getResource("/com/example/cvbuilder/Styles.css").toExternalForm()
            );

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Create CV");
            window.setScene(formScene);
            window.show();
            LOGGER.fine("Form screen displayed successfully");
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Failed to navigate to Form screen", ex);
        }
    }
}
