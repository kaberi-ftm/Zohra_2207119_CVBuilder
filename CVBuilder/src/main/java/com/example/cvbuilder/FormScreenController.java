package com.example.cvbuilder;

import com.example.cvbuilder.model.Cv;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class FormScreenController {

    @FXML private TextField fullNameField;
    @FXML private TextField emailAddressField;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;

    @FXML private TextArea educationField;
    @FXML private TextArea skillsField;
    @FXML private TextArea experienceField;
    @FXML private TextArea projectsField;

    @FXML private ImageView photoPreview;
    @FXML private GridPane topGrid;
    @FXML private ScrollPane scrollRoot;
    @FXML private StackPane photoBox;
    @FXML private Button uploadPhotoButton;
    @FXML private Label photoTitleLabel;

    private String photoPath;

    @FXML
    private void initialize() {
        if (scrollRoot != null && topGrid != null) {
            topGrid.prefWidthProperty().bind(scrollRoot.widthProperty().subtract(40));
        }
        if (photoBox != null) {
            photoBox.widthProperty().addListener((obs, ow, nw) -> {
                double w = nw.doubleValue();
                photoBox.setPrefHeight(w);
                photoBox.setMinHeight(w);
                photoBox.setMaxHeight(w);
                if (photoPreview != null) {
                    photoPreview.setFitWidth(Math.max(0, w - 20));
                }
            });
        }
    }

    @FXML
    private void handleGenerateCV(ActionEvent event) {

        if (fullNameField.getText().isBlank()) {
            addError(fullNameField);
            new Alert(Alert.AlertType.ERROR, "Please enter your Full Name.").showAndWait();
            return;
        } else clearError(fullNameField);

        if (emailAddressField.getText().isBlank() || !emailAddressField.getText().contains("@")) {
            addError(emailAddressField);
            new Alert(Alert.AlertType.ERROR, "Please enter a valid Email Address.").showAndWait();
            return;
        } else clearError(emailAddressField);

        if (phoneField.getText().isBlank()) {
            addError(phoneField);
            new Alert(Alert.AlertType.ERROR, "Please enter a valid Phone Number.").showAndWait();
            return;
        } else clearError(phoneField);

        Cv cv = new Cv();
        cv.setFullName(fullNameField.getText().trim());
        cv.setEmail(emailAddressField.getText().trim());
        cv.setPhone(phoneField.getText().trim());
        cv.setAddress(addressField.getText().trim());
        cv.setEducation(educationField.getText().trim());
        cv.setSkills(skillsField.getText().trim());
        cv.setExperience(experienceField.getText().trim());
        cv.setProjects(projectsField.getText().trim());
        cv.setPhotoPath(photoPath);

        try {
            String fxml = "/com/example/cvbuilder/PreviewScreen.fxml";

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            PreviewScreenController controller = loader.getController();
            controller.setData(cv);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Preview CV");
            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Unable to open preview: " + ex.getMessage()).showAndWait();
        }
    }

    @FXML
    private void handleUploadPhoto(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Profile Photo");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File file = chooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());

        if (file != null) {
            photoPath = file.getAbsolutePath();
            try {
                Image img = new Image(file.toURI().toString());
                photoPreview.setImage(img);

                if (uploadPhotoButton != null) {
                    uploadPhotoButton.setVisible(false);
                    uploadPhotoButton.setManaged(false);
                }
                if (photoTitleLabel != null) {
                    photoTitleLabel.setVisible(false);
                    photoTitleLabel.setManaged(false);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void addError(javafx.scene.control.Control c) {
        if (c == null) return;
        if (!c.getStyleClass().contains("field-error")) c.getStyleClass().add("field-error");
    }

    private void clearError(javafx.scene.control.Control c) {
        if (c == null) return;
        c.getStyleClass().remove("field-error");
    }
}
