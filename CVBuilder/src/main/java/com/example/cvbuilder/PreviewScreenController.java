package com.example.cvbuilder;

import com.example.cvbuilder.model.Cv;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PreviewScreenController {
    private static final Logger LOGGER = Logger.getLogger(PreviewScreenController.class.getName());
    @FXML private BorderPane root;
    @FXML private Label nameLabel;
    @FXML private Label contactLabel;
    @FXML private Label educationContent;
    @FXML private Label skillsContent;
    @FXML private Label experienceContent;
    @FXML private Label projectsContent;
    @FXML private ImageView photoView;
    @FXML private Node sidebarBox;
    @FXML private Label contactHeadingRight;
    @FXML private Label contactContentRight;
    @FXML private Label skillsHeadingRight;
    @FXML private Label skillsContentRight;
    @FXML private ScrollPane scroll;
    @FXML private VBox pageRoot;
    @FXML private VBox mainColumn;

    // Getters for tests
    public BorderPane getRoot() { return root; }
    public Label getNameLabel() { return nameLabel; }
    public Label getContactLabel() { return contactLabel; }
    public Label getEducationContent() { return educationContent; }
    public Label getSkillsContent() { return skillsContent; }
    public Label getExperienceContent() { return experienceContent; }
    public Label getProjectsContent() { return projectsContent; }
    public Node getSidebarBox() { return sidebarBox; }
    public Label getContactHeadingRight() { return contactHeadingRight; }
    public Label getContactContentRight() { return contactContentRight; }
    public Label getSkillsHeadingRight() { return skillsHeadingRight; }
    public Label getSkillsContentRight() { return skillsContentRight; }

    private Cv currentCv;

    public void setData(Cv cv) {
        this.currentCv = cv;
        LOGGER.fine("Populating preview with CV data for: " + cv.getFullName());
        nameLabel.setText(cv.getFullName());
        String contact = String.format("%s | %s | %s",
                nullToEmpty(cv.getEmail()), nullToEmpty(cv.getPhone()), nullToEmpty(cv.getAddress()));
        contactLabel.setText(contact);
        contactContentRight.setText(contact);
        educationContent.setText(emptyToPlaceholder(cv.getEducation()));
        String skills = emptyToPlaceholder(cv.getSkills());
        skillsContent.setText(skills);
        skillsContentRight.setText(skills);
        experienceContent.setText(emptyToPlaceholder(cv.getExperience()));
        projectsContent.setText(emptyToPlaceholder(cv.getProjects()));

        if (cv.getPhotoPath() != null && !cv.getPhotoPath().isBlank()) {
            try {
                Image img = new Image(new File(cv.getPhotoPath()).toURI().toString());
                photoView.setImage(img);
                LOGGER.info("Photo loaded for preview from: " + cv.getPhotoPath());
            } catch (Exception ex) {
                LOGGER.log(Level.WARNING, "Failed to load photo in preview: " + cv.getPhotoPath(), ex);
            }
        }
    }

    public void applyDesign(String design) {
        LOGGER.fine("Applying design style: " + design);
        boolean classic = design != null && design.equalsIgnoreCase("Classic");
        // Sidebar visibility
        sidebarBox.setVisible(!classic);
        sidebarBox.setManaged(!classic);

        // Right-side duplicates for classic
        contactHeadingRight.setVisible(classic);
        contactHeadingRight.setManaged(classic);
        contactContentRight.setVisible(classic);
        contactContentRight.setManaged(classic);
        skillsHeadingRight.setVisible(classic);
        skillsHeadingRight.setManaged(classic);
        skillsContentRight.setVisible(classic);
        skillsContentRight.setManaged(classic);
    }

    public void applyTheme(String accent) {
        LOGGER.fine("Applying theme accent: " + accent);
        // Remove old theme classes
        root.getStyleClass().removeAll("theme-blue", "theme-teal", "theme-purple", "theme-crimson");
        String cls = switch (accent == null ? "Blue" : accent) {
            case "Teal" -> "theme-teal";
            case "Purple" -> "theme-purple";
            case "Crimson" -> "theme-crimson";
            default -> "theme-blue";
        };
        if (!root.getStyleClass().contains(cls)) {
            root.getStyleClass().add(cls);
        }
        if (mainColumn != null) {
            double spacing = switch (cls) {
                case "theme-purple" -> 18;
                case "theme-teal" -> 16;
                default -> 16;
            };
            mainColumn.setSpacing(spacing);
        }
    }

    private String nullToEmpty(String s) { return s == null ? "" : s; }
    private String emptyToPlaceholder(String s) { return (s == null || s.isBlank()) ? "—" : s; }

    @FXML
    private void handleBackToEdit(ActionEvent e) throws Exception {
        LOGGER.info("Back to Edit clicked from Preview");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cvbuilder/FormScreen.fxml"));
        Parent newRoot = loader.load();
        FormScreenController form = loader.getController();
        if (currentCv != null) form.setData(currentCv);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Create CV");
        Scene scene = new Scene(newRoot);
        scene.getStylesheets().add(getClass().getResource("/com/example/cvbuilder/Styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleBackHome(ActionEvent e) throws Exception {
        LOGGER.info("Home clicked from Preview");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cvbuilder/Homescreen.fxml"));
        Parent newRoot = loader.load();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("CV Builder");
        Scene scene = new Scene(newRoot);
        scene.getStylesheets().add(getClass().getResource("/com/example/cvbuilder/Styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleExportPdf(ActionEvent e) {
        LOGGER.info("Export to PDF clicked");
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job == null) {
            LOGGER.severe("No printer available for PDF export");
            new Alert(Alert.AlertType.ERROR, "No printer available.").showAndWait();
            return;
        }
        // Prefer a PDF printer if available
        for (Printer p : Printer.getAllPrinters()) {
            if (p.getName() != null && p.getName().toLowerCase().contains("pdf")) {
                try {
                    job.setPrinter(p);
                } catch (Exception ex) {
                    // ignore and fall back to dialog
                }
                break;
            }
        }
        boolean proceed = true;
        if (job.getPrinter() == null || !job.getPrinter().getName().toLowerCase().contains("pdf")) {
            proceed = job.showPrintDialog(stage);
        }
        if (!proceed) {
            LOGGER.fine("User canceled print dialog");
            return;
        }
        boolean success = job.printPage(root);
        job.endJob();
        if (!success) {
            LOGGER.severe("Print/export failed");
            new Alert(Alert.AlertType.ERROR, "Failed to print/export.").showAndWait();
        } else {
            LOGGER.info("Print/export completed successfully");
        }
    }

    @FXML
    private void initialize() {
        if (scroll != null && pageRoot != null) {
            scroll.viewportBoundsProperty().addListener((obs, oldB, newB) -> fitScaleToViewport(newB.getWidth()));
            Platform.runLater(() -> {
                if (scroll.getViewportBounds() != null) {
                    fitScaleToViewport(scroll.getViewportBounds().getWidth());
                }
            });
        }
    }

    private void fitScaleToViewport(double viewportWidth) {
        if (pageRoot == null) return;
        double baseWidth = 794.0;
        double padding = 40.0;
        double scale = Math.min(1.0, (viewportWidth - padding) / baseWidth);
        if (scale <= 0) scale = 1.0;
        pageRoot.setScaleX(scale);
        pageRoot.setScaleY(scale);
    }
}
