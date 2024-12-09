package com.android.familybudgetapp.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilitiesTest
{
    @Test
    public void usernameValid()
    {
        assertTrue(Utilities.isUsernameValid("AA"));
        assertTrue(Utilities.isUsernameValid("aA"));
        assertTrue(Utilities.isUsernameValid("Aa"));
        assertTrue(Utilities.isUsernameValid("9B"));
        assertTrue(Utilities.isUsernameValid("9b"));
        assertTrue(Utilities.isUsernameValid("B9"));
        assertTrue(Utilities.isUsernameValid("b9"));
        assertTrue(Utilities.isUsernameValid("9_"));
        assertTrue(Utilities.isUsernameValid("A_"));
        assertTrue(Utilities.isUsernameValid("a_"));
    }

    @Test
    public void usernameInvalid()
    {
        assertFalse(Utilities.isUsernameValid(""));
        assertFalse(Utilities.isUsernameValid(" "));
        assertFalse(Utilities.isUsernameValid("a"));
        assertFalse(Utilities.isUsernameValid("_a"));
        assertFalse(Utilities.isUsernameValid("a b"));
        assertFalse(Utilities.isUsernameValid("ab "));
        assertFalse(Utilities.isUsernameValid(" ab"));
        assertFalse(Utilities.isUsernameValid("a!b"));
    }

    @Test
    public void validAlphanumericWithSpaces()
    {
        assertTrue(Utilities.isAlphanumericWithSpaces("A A"));
        assertTrue(Utilities.isAlphanumericWithSpaces("a9A"));
        assertTrue(Utilities.isAlphanumericWithSpaces("AAa"));
        assertTrue(Utilities.isAlphanumericWithSpaces("99B"));
        assertTrue(Utilities.isAlphanumericWithSpaces("9Ab"));
        assertTrue(Utilities.isAlphanumericWithSpaces("BA9"));
        assertTrue(Utilities.isAlphanumericWithSpaces("bA9"));
    }

    @Test
    public void invalidAlphanumericWithSpaces()
    {
        assertFalse(Utilities.isAlphanumericWithSpaces(""));
        assertFalse(Utilities.isAlphanumericWithSpaces(" "));
        assertFalse(Utilities.isAlphanumericWithSpaces("a"));
        assertFalse(Utilities.isAlphanumericWithSpaces("Aa"));
        assertFalse(Utilities.isAlphanumericWithSpaces("a_b"));
        assertFalse(Utilities.isAlphanumericWithSpaces("ab "));
        assertFalse(Utilities.isAlphanumericWithSpaces(" ab"));
        assertFalse(Utilities.isAlphanumericWithSpaces(" a!b "));
    }
}