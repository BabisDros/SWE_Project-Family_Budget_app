package com.android.familybudgetapp.utilities;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommonStringValidationsTest {

    @Test
    public void isUsernameValid() {
        assertFalse(CommonStringValidations.isUsernameValid("$Name"));
        assertFalse(CommonStringValidations.isUsernameValid("_Name"));
        assertFalse(CommonStringValidations.isUsernameValid("A"));
        assertFalse(CommonStringValidations.isUsernameValid("a"));
        assertTrue(CommonStringValidations.isUsernameValid("Name"));
        assertTrue(CommonStringValidations.isUsernameValid("0a"));
        assertTrue(CommonStringValidations.isUsernameValid("a0"));
        assertTrue(CommonStringValidations.isUsernameValid("Aa"));
        assertTrue(CommonStringValidations.isUsernameValid("Name02sad"));
    }

    @Test
    public void isPasswordValid() {
        assertFalse(CommonStringValidations.isPasswordValid("aA0"));
        assertFalse(CommonStringValidations.isPasswordValid("aA0%"));
        assertFalse(CommonStringValidations.isPasswordValid("&aA0&"));
        assertFalse(CommonStringValidations.isPasswordValid("pass_word"));
        assertTrue(CommonStringValidations.isPasswordValid("password"));
        assertTrue(CommonStringValidations.isPasswordValid("PaSsWoRd"));
        assertTrue(CommonStringValidations.isPasswordValid("002asd0s1sjAs89Hs"));
    }

    @Test
    public void isAlphanumericWithSpaces() {
        assertFalse(CommonStringValidations.isAlphanumericWithSpaces("Jo"));
        assertFalse(CommonStringValidations.isAlphanumericWithSpaces("John "));
        assertFalse(CommonStringValidations.isAlphanumericWithSpaces(" John"));
        assertFalse(CommonStringValidations.isAlphanumericWithSpaces("xXJohn_PopXx"));
        assertTrue(CommonStringValidations.isAlphanumericWithSpaces("John Pop the 2nd"));
        assertTrue(CommonStringValidations.isAlphanumericWithSpaces("John Pop jr"));


    }
}