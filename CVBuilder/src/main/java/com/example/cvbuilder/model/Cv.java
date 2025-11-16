package com.example.cvbuilder.model;

public class Cv {
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String education;
    private String skills;
    private String experience;
    private String projects;
    private String photoPath;
    private String accentTheme; // e.g., Blue, Teal, Purple, Crimson
    private String designStyle; // e.g., Sidebar, Classic

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    public String getProjects() { return projects; }
    public void setProjects(String projects) { this.projects = projects; }

    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }

    public String getAccentTheme() { return accentTheme; }
    public void setAccentTheme(String accentTheme) { this.accentTheme = accentTheme; }

    public String getDesignStyle() { return designStyle; }
    public void setDesignStyle(String designStyle) { this.designStyle = designStyle; }
}
