package com.example.cvbuilder;

import com.example.cvbuilder.model.Cv;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PreviewScreenControllerTest extends FXTestBase {

    private PreviewScreenController loadController() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cvbuilder/PreviewScreen.fxml"));
        Parent root = loader.load();
        return loader.getController();
    }

    @Test
    void testApplyTheme() throws Exception {
        PreviewScreenController c = loadController();
        c.applyTheme("Teal");
        assertTrue(c.getRoot().getStyleClass().contains("theme-teal"));
        c.applyTheme("Purple");
        assertFalse(c.getRoot().getStyleClass().contains("theme-teal"));
        assertTrue(c.getRoot().getStyleClass().contains("theme-purple"));
        c.applyTheme(null);
        assertTrue(c.getRoot().getStyleClass().contains("theme-blue"));
    }

    @Test
    void testApplyDesign() throws Exception {
        PreviewScreenController c = loadController();
        c.applyDesign("Sidebar");
        assertTrue(c.getSidebarBox().isVisible());
        assertFalse(c.getContactHeadingRight().isVisible());

        c.applyDesign("Classic");
        assertFalse(c.getSidebarBox().isVisible());
        assertTrue(c.getContactHeadingRight().isVisible());
        assertTrue(c.getSkillsHeadingRight().isVisible());
    }

    @Test
    void testSetDataPopulatesFields() throws Exception {
        PreviewScreenController c = loadController();
        Cv cv = new Cv();
        cv.setFullName("Jane Doe");
        cv.setEmail("jane@ex.com");
        cv.setPhone("+1234567890");
        cv.setAddress("123, Street");
        cv.setEducation("BSc");
        cv.setSkills("Java, Python");
        cv.setExperience("Intern");
        cv.setProjects("Proj1");
        c.setData(cv);
        assertEquals("Jane Doe", c.getNameLabel().getText());
        assertTrue(c.getContactLabel().getText().contains("jane@ex.com"));
        assertEquals("BSc", c.getEducationContent().getText());
        assertEquals("Java, Python", c.getSkillsContent().getText());
        assertEquals("Intern", c.getExperienceContent().getText());
        assertEquals("Proj1", c.getProjectsContent().getText());
    }
}
