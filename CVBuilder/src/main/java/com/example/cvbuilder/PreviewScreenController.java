package com.example.cvbuilder;

import com.example.cvbuilder.model.Cv;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class PreviewScreenController {

    @FXML private Label nameLabel;
    @FXML private Label emailLabel;
    @FXML private Label phoneLabel;
    @FXML private Label addressLabel;
    @FXML private Label educationLabel;
    @FXML private Label skillsLabel;
    @FXML private Label experienceLabel;
    @FXML private Label projectsLabel;
    @FXML private ImageView profilePhotoView;

    public void setData(Cv cv) {
        if (cv == null) return;

        nameLabel.setText(cv.getFullName());
        emailLabel.setText(cv.getEmail());
        phoneLabel.setText(cv.getPhone());
        addressLabel.setText(cv.getAddress());
        educationLabel.setText(cv.getEducation());
        skillsLabel.setText(cv.getSkills());
        experienceLabel.setText(cv.getExperience());
        projectsLabel.setText(cv.getProjects());

        try {
            String photoPath = cv.getPhotoPath();
            if (photoPath != null && !photoPath.isBlank()) {
                File file = new File(photoPath);
                Image img = new Image(file.toURI().toString());
                profilePhotoView.setImage(img);
            }
        } catch (Exception e) {
            System.err.println("Failed to load image: " + e.getMessage());
        }
    }

    @FXML
    private void handleBackToForm(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cvbuilder/FormScreen.fxml"));
            Parent root = loader.load();

            Scene formScene = new Scene(root);

            formScene.getStylesheets().add(
                    getClass().getResource("/com/example/cvbuilder/Styles.css").toExternalForm()
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(formScene);
            stage.setTitle("Create CV");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Unable to go back to form.").showAndWait();
        }
    }
}