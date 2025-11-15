package com.example.cvbuilder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeScreenController {

    @FXML
    private void handleCreateNewCV(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/cvbuilder/FormScreen.fxml")
        );

        Parent formScreenRoot = loader.load();
        Scene formScene = new Scene(formScreenRoot);


        formScene.getStylesheets().add(
                getClass().getResource("/com/example/cvbuilder/Styles.css").toExternalForm()
        );

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(formScene);
        window.show();
    }
}
