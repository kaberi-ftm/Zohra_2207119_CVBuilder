package com.example.cvbuilder.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CvTest {
    @Test
    void testGettersSetters() {
        Cv cv = new Cv();
        cv.setFullName("Name");
        cv.setEmail("a@b.com");
        cv.setPhone("0123");
        cv.setAddress("Addr");
        cv.setEducation("Edu");
        cv.setSkills("Skills");
        cv.setExperience("Exp");
        cv.setProjects("Proj");
        cv.setPhotoPath("/tmp/x.png");
        cv.setAccentTheme("Teal");
        cv.setDesignStyle("Classic");

        assertEquals("Name", cv.getFullName());
        assertEquals("a@b.com", cv.getEmail());
        assertEquals("0123", cv.getPhone());
        assertEquals("Addr", cv.getAddress());
        assertEquals("Edu", cv.getEducation());
        assertEquals("Skills", cv.getSkills());
        assertEquals("Exp", cv.getExperience());
        assertEquals("Proj", cv.getProjects());
        assertEquals("/tmp/x.png", cv.getPhotoPath());
        assertEquals("Teal", cv.getAccentTheme());
        assertEquals("Classic", cv.getDesignStyle());
    }
}

