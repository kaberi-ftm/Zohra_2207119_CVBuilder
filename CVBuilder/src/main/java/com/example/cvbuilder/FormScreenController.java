package com.example.cvbuilder;

import com.example.cvbuilder.model.Cv;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.cvbuilder.util.ValidationUtil;

public class FormScreenController {
    private Cv currentCv;
    private static final Logger LOGGER = Logger.getLogger(FormScreenController.class.getName());
    @FXML private TextField fullNameField;
    @FXML private TextField emailAddressField;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;

    @FXML private TextArea educationField;
    @FXML private TextArea skillsField;
    @FXML private TextArea experienceField;
    @FXML private TextArea projectsField;

    @FXML private ChoiceBox<String> accentChoice;
    @FXML private ChoiceBox<String> designChoice;
    @FXML private ImageView photoPreview;
    @FXML private javafx.scene.layout.FlowPane topFlow;
    @FXML private ScrollPane scrollRoot;
    @FXML private javafx.scene.layout.HBox designPreviewSidebar;
    @FXML private javafx.scene.layout.HBox designPreviewClassic;

    private String photoPath;

    @FXML
    private void initialize() {
        LOGGER.fine("Initializing FormScreenController");
        if (accentChoice != null && accentChoice.getItems() != null && !accentChoice.getItems().isEmpty()) {
            if (accentChoice.getValue() == null) accentChoice.getSelectionModel().selectFirst();
            accentChoice.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
                if (currentCv != null) currentCv.setAccentTheme(newV);
            });
        }
        if (designChoice != null) {
            if (designChoice.getItems() == null || designChoice.getItems().isEmpty()) return;
            if (designChoice.getValue() == null) designChoice.getSelectionModel().select("Sidebar");
            designChoice.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
                if (currentCv != null) currentCv.setDesignStyle(n);
                applyDesignPreview(n);
            });
        }

        // Simple responsive behavior: two-column on wide, single column when narrow
        if (scrollRoot != null && topFlow != null) {
            topFlow.prefWrapLengthProperty().bind(scrollRoot.widthProperty().subtract(40));
        }

        // initial previews
        applyDesignPreview(designChoice != null ? designChoice.getValue() : "Sidebar");
    }

    @FXML
    private void handleGenerateCV(ActionEvent event) {
        LOGGER.info("Generate CV clicked");
        if (fullNameField == null || emailAddressField == null) {
            LOGGER.severe("Form fields not injected; FXML initialization error");
            new Alert(Alert.AlertType.ERROR, "Form not initialized correctly.").showAndWait();
            return;
        }

        // Clear previous error styles
        clearError(fullNameField);
        clearError(emailAddressField);
        clearError(phoneField);

        if (fullNameField.getText().isBlank()) {
            LOGGER.warning("Validation failed: Full Name is blank");
            addError(fullNameField);
            new Alert(Alert.AlertType.ERROR, "Please enter your Full Name.").showAndWait();
            return;
        }
        if (!ValidationUtil.isValidEmail(emailAddressField.getText())) {
            LOGGER.warning("Validation failed: Invalid email address: " + emailAddressField.getText());
            addError(emailAddressField);
            new Alert(Alert.AlertType.ERROR, "Please enter a valid Email Address.").showAndWait();
            return;
        }
        if (!ValidationUtil.isValidPhone(phoneField.getText())) {
            LOGGER.warning("Validation failed: Invalid phone: " + phoneField.getText());
            addError(phoneField);
            new Alert(Alert.AlertType.ERROR, "Please enter a valid Phone Number.").showAndWait();
            return;
        }

        Cv cv = (currentCv == null) ? new Cv() : currentCv;
        cv.setFullName(fullNameField.getText().trim());
        cv.setEmail(emailAddressField.getText().trim());
        cv.setPhone(phoneField.getText().trim());
        cv.setAddress(addressField.getText().trim());
        cv.setEducation(educationField != null ? educationField.getText().trim() : "");
        cv.setSkills(skillsField != null ? skillsField.getText().trim() : "");
        cv.setExperience(experienceField != null ? experienceField.getText().trim() : "");
        cv.setProjects(projectsField != null ? projectsField.getText().trim() : "");
        cv.setPhotoPath(photoPath);
        cv.setAccentTheme(accentChoice != null && accentChoice.getValue() != null ? accentChoice.getValue() : "Blue");
        cv.setDesignStyle(designChoice != null && designChoice.getValue() != null ? designChoice.getValue() : "Sidebar");
        currentCv = cv;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cvbuilder/PreviewScreen.fxml"));
            Parent root = loader.load();
            PreviewScreenController controller = loader.getController();
            controller.setData(cv);
            controller.applyTheme(cv.getAccentTheme());
            controller.applyDesign(cv.getDesignStyle());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Preview CV");
            Scene previewScene = new Scene(root);
            previewScene.getStylesheets().add(getClass().getResource("/com/example/cvbuilder/Styles.css").toExternalForm());
            stage.setScene(previewScene);
            stage.show();
            LOGGER.info("Navigated to Preview screen successfully");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Failed to open Preview screen", ex);
            new Alert(Alert.AlertType.ERROR, "Unable to open preview: " + ex.getMessage()).showAndWait();
        }
    }

    @FXML
    private void handleUploadPhoto(ActionEvent event) {
        LOGGER.fine("Upload photo clicked");
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Profile Photo");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        java.io.File file = chooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
        if (file != null) {
            photoPath = file.getAbsolutePath();
            try {
                Image img = new Image(file.toURI().toString());
                photoPreview.setImage(img);
                LOGGER.info("Photo selected: " + photoPath);
            } catch (Exception ex) {
                LOGGER.log(Level.WARNING, "Failed to load selected photo: " + photoPath, ex);
            }
        }
    }

    @FXML
    private void handleBackHome(ActionEvent event) {
        LOGGER.info("Back to Home clicked from Form screen");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cvbuilder/Homescreen.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("CV Builder");
            Scene homeScene = new Scene(root);
            homeScene.getStylesheets().add(getClass().getResource("/com/example/cvbuilder/Styles.css").toExternalForm());
            stage.setScene(homeScene);
            stage.show();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Failed to navigate back to Home from Form", ex);
            new Alert(Alert.AlertType.ERROR, "Unable to go back home: " + ex.getMessage()).showAndWait();
        }
    }

    public void setData(Cv cv) {
        this.currentCv = cv;
        if (cv == null) return;
        if (fullNameField != null) fullNameField.setText(safe(cv.getFullName()));
        if (emailAddressField != null) emailAddressField.setText(safe(cv.getEmail()));
        if (phoneField != null) phoneField.setText(safe(cv.getPhone()));
        if (addressField != null) addressField.setText(safe(cv.getAddress()));
        if (educationField != null) educationField.setText(safe(cv.getEducation()));
        if (skillsField != null) skillsField.setText(safe(cv.getSkills()));
        if (experienceField != null) experienceField.setText(safe(cv.getExperience()));
        if (projectsField != null) projectsField.setText(safe(cv.getProjects()));
        if (accentChoice != null && cv.getAccentTheme() != null) accentChoice.getSelectionModel().select(cv.getAccentTheme());
        if (designChoice != null && cv.getDesignStyle() != null) designChoice.getSelectionModel().select(cv.getDesignStyle());
        applyDesignPreview(designChoice != null ? designChoice.getValue() : cv.getDesignStyle());
        if (photoPreview != null) {
            try {
                if (cv.getPhotoPath() != null && !cv.getPhotoPath().isBlank()) {
                    Image img = new Image(new java.io.File(cv.getPhotoPath()).toURI().toString());
                    photoPreview.setImage(img);
                    this.photoPath = cv.getPhotoPath();
                }
            } catch (Exception ex) {
                LOGGER.log(Level.FINE, "Failed to set photo preview from existing CV", ex);
            }
        }
    }

    private String safe(String s) { return s == null ? "" : s; }

    private void applyDesignPreview(String design) {
        boolean classic = design != null && design.equalsIgnoreCase("Classic");
        if (designPreviewSidebar != null) {
            designPreviewSidebar.setVisible(!classic);
            designPreviewSidebar.setManaged(!classic);
        }
        if (designPreviewClassic != null) {
            designPreviewClassic.setVisible(classic);
            designPreviewClassic.setManaged(classic);
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
