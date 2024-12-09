package com.android.familybudgetapp.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CashFlowTest {

    @Test
    void testConstructorAndGetters() {
        int amount = 100;
        CashFlowCategory category = new Income("test");
        LocalDateTime dateStart = LocalDateTime.now();

        CashFlow cashFlow = new OneOff(amount, category, dateStart);

        assertEquals(amount, cashFlow.getAmount());
        assertEquals(category, cashFlow.getCategory());
        assertEquals(dateStart, cashFlow.getDateStart());
    }

    @Test
    void testSetters() {
        CashFlow cashFlow = new OneOff(100, new Income("test"), LocalDateTime.now());
        int newAmount = 200;
        CashFlowCategory newCategory = new Income("test2");
        LocalDateTime newDateStart = LocalDateTime.now();

        cashFlow.setAmount(newAmount);
        cashFlow.setCategory(newCategory);
        cashFlow.setDateStart(newDateStart);

        assertEquals(newAmount, cashFlow.getAmount());
        assertEquals(newCategory, cashFlow.getCategory());
        assertEquals(newDateStart, cashFlow.getDateStart());
    }
}
