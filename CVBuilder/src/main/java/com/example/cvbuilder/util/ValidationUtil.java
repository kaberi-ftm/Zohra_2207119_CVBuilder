package com.example.cvbuilder.util;

public final class ValidationUtil {
    private ValidationUtil() {}

    public static boolean isNonBlank(String s) {
        return s != null && !s.trim().isEmpty();
    }

    public static boolean isValidEmail(String s) {
        if (!isNonBlank(s)) return false;
        return s.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    }

    public static boolean isValidPhone(String s) {
        if (!isNonBlank(s)) return false;
        // Allow digits, spaces, +, -, parentheses; 7–20 significant characters
        String digits = s.replaceAll("[^0-9]", "");
        return digits.length() >= 7 && digits.length() <= 20;
    }
}

