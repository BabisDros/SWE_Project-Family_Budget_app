package com.android.familybudgetapp.domain;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CashFlowTest {

    @Test
    void testConstructorAndGetters() {
        int amount = 100;
        CashFlowCategory category = CashFlowCategory.category1;
        Date dateStart = new Date();

        CashFlow cashFlow = new CashFlow(amount, category, dateStart);

        assertEquals(amount, cashFlow.getAmount());
        assertEquals(category, cashFlow.getCategory());
        assertEquals(dateStart, cashFlow.getDateStart());
    }

    @Test
    void testSetters() {
        CashFlow cashFlow = new CashFlow(100, CashFlowCategory.category1, new Date());
        int newAmount = 200;
        CashFlowCategory newCategory = CashFlowCategory.category1;
        Date newDateStart = new Date();

        cashFlow.setAmount(newAmount);
        cashFlow.setCategory(newCategory);
        cashFlow.setDateStart(newDateStart);

        assertEquals(newAmount, cashFlow.getAmount());
        assertEquals(newCategory, cashFlow.getCategory());
        assertEquals(newDateStart, cashFlow.getDateStart());
    }

    @Test
    void testToString() {
        int amount = 100;
        CashFlowCategory category = CashFlowCategory.category1;
        Date dateStart = new Date();

        CashFlow cashFlow = new CashFlow(amount, category, dateStart);

        String toStringResult = cashFlow.toString();

        assertTrue(toStringResult.contains("amount=100"));
        assertTrue(toStringResult.contains("category=INCOME"));
        assertTrue(toStringResult.contains("dateStart="));
    }
}
