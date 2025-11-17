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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FormScreenController {

    @FXML private TextField Name;
    @FXML private TextField mail;
    @FXML private TextField phone;
    @FXML private TextField address;

    @FXML private TextArea education;
    @FXML private TextArea skills;
    @FXML private TextArea experience;
    @FXML private TextArea projects;

    @FXML private ImageView photoPreview;
    @FXML private StackPane photo;
    @FXML private Button uploadPhotoButton;

    private String photoPath;

    @FXML
    private void initialize() {
    }

    @FXML
    private void handleGenerateCV(ActionEvent event) {

        if (Name.getText().isBlank()) {
            addError(Name);
            new Alert(Alert.AlertType.ERROR, "Please enter your Full Name.").showAndWait();
            return;
        } else {
            clearError(Name);
        }

        if (mail.getText().isBlank() || !mail.getText().contains("@")) {
            addError(mail);
            new Alert(Alert.AlertType.ERROR, "Please enter a valid Email Address.").showAndWait();
            return;
        } else {
            clearError(mail);
        }

        if (phone.getText().isBlank()) {
            addError(phone);
            new Alert(Alert.AlertType.ERROR, "Please enter a valid Phone Number.").showAndWait();
            return;
        } else {
            clearError(phone);
        }

        Cv cv = new Cv();
        cv.setFullName(Name.getText().trim());
        cv.setEmail(mail.getText().trim());
        cv.setPhone(phone.getText().trim());
        cv.setAddress(address.getText().trim());
        cv.setEducation(education.getText().trim());
        cv.setSkills(skills.getText().trim());
        cv.setExperience(experience.getText().trim());
        cv.setProjects(projects.getText().trim());
        cv.setPhotoPath(photoPath);

        try {
            String fxml = "/com/example/cvbuilder/PreviewScreen.fxml";

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            PreviewScreenController controller = loader.getController();
            controller.setData(cv);

            Scene previewScene = new Scene(root);

            previewScene.getStylesheets().add(
                    getClass().getResource("/com/example/cvbuilder/Styles.css").toExternalForm()
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(previewScene);
            stage.setTitle("Preview CV");
            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Unable to open preview: " + ex.getMessage()).showAndWait();
        }
    }

    @FXML
    private void handleUploadPhoto(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Profile Photo");

        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File file = chooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        if (file != null) {
            photoPath = file.getAbsolutePath();
            try {
                Image img = new Image(file.toURI().toString());
                photoPreview.setImage(img);

                if (uploadPhotoButton != null) {
                    uploadPhotoButton.setVisible(false);
                    uploadPhotoButton.setManaged(false);
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