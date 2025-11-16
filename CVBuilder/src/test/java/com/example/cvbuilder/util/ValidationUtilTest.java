package com.example.cvbuilder.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationUtilTest {

    @Test
    void testIsNonBlank() {
        assertTrue(ValidationUtil.isNonBlank("a"));
        assertTrue(ValidationUtil.isNonBlank(" a "));
        assertFalse(ValidationUtil.isNonBlank(" "));
        assertFalse(ValidationUtil.isNonBlank(null));
    }

    @Test
    void testIsValidEmail() {
        assertTrue(ValidationUtil.isValidEmail("user@example.com"));
        assertTrue(ValidationUtil.isValidEmail("u.ser+tag@sub.domain.org"));

        assertFalse(ValidationUtil.isValidEmail(null));
        assertFalse(ValidationUtil.isValidEmail(""));
        assertFalse(ValidationUtil.isValidEmail("user@"));
        assertFalse(ValidationUtil.isValidEmail("@domain.com"));
        assertFalse(ValidationUtil.isValidEmail("user@domain"));
        assertFalse(ValidationUtil.isValidEmail("user domain@example.com"));
    }

    @Test
    void testIsValidPhone() {
        assertTrue(ValidationUtil.isValidPhone("01234567"));
        assertTrue(ValidationUtil.isValidPhone("+1 (202) 555-0182"));
        assertTrue(ValidationUtil.isValidPhone("+880 1711-123-456"));

        assertFalse(ValidationUtil.isValidPhone("123"));
        assertFalse(ValidationUtil.isValidPhone("abcdefg"));
        assertFalse(ValidationUtil.isValidPhone("   "));
        assertFalse(ValidationUtil.isValidPhone(null));
    }
}

