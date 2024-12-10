package com.android.familybudgetapp.domain;

import static org.junit.Assert.*;

import com.android.familybudgetapp.utilities.CommonStringValidations;

import org.junit.Test;

public class CommonStringValidationsTest
{
    @Test
    public void usernameValid()
    {
        assertTrue(CommonStringValidations.isUsernameValid("AA"));
        assertTrue(CommonStringValidations.isUsernameValid("aA"));
        assertTrue(CommonStringValidations.isUsernameValid("Aa"));
        assertTrue(CommonStringValidations.isUsernameValid("9B"));
        assertTrue(CommonStringValidations.isUsernameValid("9b"));
        assertTrue(CommonStringValidations.isUsernameValid("B9"));
        assertTrue(CommonStringValidations.isUsernameValid("b9"));
        assertTrue(CommonStringValidations.isUsernameValid("9_"));
        assertTrue(CommonStringValidations.isUsernameValid("A_"));
        assertTrue(CommonStringValidations.isUsernameValid("a_"));
    }

    @Test
    public void usernameInvalid()
    {
        assertFalse(CommonStringValidations.isUsernameValid(""));
        assertFalse(CommonStringValidations.isUsernameValid(" "));
        assertFalse(CommonStringValidations.isUsernameValid("a"));
        assertFalse(CommonStringValidations.isUsernameValid("_a"));
        assertFalse(CommonStringValidations.isUsernameValid("a b"));
        assertFalse(CommonStringValidations.isUsernameValid("ab "));
        assertFalse(CommonStringValidations.isUsernameValid(" ab"));
        assertFalse(CommonStringValidations.isUsernameValid("a!b"));
    }

    @Test
    public void validAlphanumericWithSpaces()
    {
        assertTrue(CommonStringValidations.isAlphanumericWithSpaces("A A"));
        assertTrue(CommonStringValidations.isAlphanumericWithSpaces("a9A"));
        assertTrue(CommonStringValidations.isAlphanumericWithSpaces("AAa"));
        assertTrue(CommonStringValidations.isAlphanumericWithSpaces("99B"));
        assertTrue(CommonStringValidations.isAlphanumericWithSpaces("9Ab"));
        assertTrue(CommonStringValidations.isAlphanumericWithSpaces("BA9"));
        assertTrue(CommonStringValidations.isAlphanumericWithSpaces("bA9"));
    }

    @Test
    public void invalidAlphanumericWithSpaces()
    {
        assertFalse(CommonStringValidations.isAlphanumericWithSpaces(""));
        assertFalse(CommonStringValidations.isAlphanumericWithSpaces(" "));
        assertFalse(CommonStringValidations.isAlphanumericWithSpaces("a"));
        assertFalse(CommonStringValidations.isAlphanumericWithSpaces("Aa"));
        assertFalse(CommonStringValidations.isAlphanumericWithSpaces("a_b"));
        assertFalse(CommonStringValidations.isAlphanumericWithSpaces("ab "));
        assertFalse(CommonStringValidations.isAlphanumericWithSpaces(" ab"));
        assertFalse(CommonStringValidations.isAlphanumericWithSpaces(" a!b "));
    }
}