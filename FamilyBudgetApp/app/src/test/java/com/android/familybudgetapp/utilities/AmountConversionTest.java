package com.android.familybudgetapp.utilities;

import static org.junit.Assert.*;

import org.junit.Test;

public class AmountConversionTest {

    @Test
    public void toEuro() {
        String toEuro = AmountConversion.toEuro(12575);
        assertEquals("125,75€", toEuro);
    }
}