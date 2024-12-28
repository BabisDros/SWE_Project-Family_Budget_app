package com.android.familybudgetapp.dao;

import com.android.familybudgetapp.domain.Allowance;
import com.android.familybudgetapp.domain.CashFlow;
import com.android.familybudgetapp.domain.Expense;
import com.android.familybudgetapp.domain.FamPos;
import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.Income;
import com.android.familybudgetapp.domain.MoneyBox;
import com.android.familybudgetapp.domain.OneOff;
import com.android.familybudgetapp.domain.Repeating;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.domain.recurPeriod;

import java.time.LocalDateTime;

public abstract class Initializer {

    /**
     * The ID of the logged on user
     */
    public static long currentUserID;
    /**
     * Clears data before usage
     */
    protected abstract void eraseData();

    /**
     * Initialize testing data
     * sets user1 as current logged user
     */
    public void prepareData()
    {
        eraseData();

        //families
        FamilyDAO familyDAO = getFamilyDAO();
        Family family = new Family("Family surname test");
        familyDAO.save(family);

        //categories
        Expense categoryExpense1 = new Expense("Food", 200);
        family.addCashFlowCategory(categoryExpense1);
        Expense categoryExpense2 = new Expense("Clothes", 500);
        family.addCashFlowCategory(categoryExpense2);
        Expense categoryExpense3 = new Expense("Gift", 800);
        family.addCashFlowCategory(categoryExpense3);
        Income categoryIncome1 = new Income("Job");
        family.addCashFlowCategory(categoryIncome1);
        Income categoryIncome2 = new Income("Casino");
        family.addCashFlowCategory(categoryIncome2);

        //users
        UserDAO userDAO = getUserDAO();
        User user1 = new User("Name test", "usernameTest", "passwordTest", FamPos.Protector, family);
        User user2 = new User("Name test2", "usernameTest2", "passwordTest2", FamPos.Member, family);
        userDAO.save(user1);
        userDAO.save(user2);

        family.addMember(user1);
        family.addMember(user2);

        user1.setFamily(family);
        user2.setFamily(family);

        currentUserID = user1.getID();

        // Requires DebugSet, since we are re-adding old cashFlows
        Repeating repeating;
        OneOff oneOff;

        //expenses
        repeating = new Repeating(150, categoryExpense1, LocalDateTime.now(), LocalDateTime.now().plusMonths(1), recurPeriod.Monthly);
        repeating.DebugSetDateStart(LocalDateTime.of(2024, 12, 20, 0, 0));
        repeating.DebugSetDateEnd(LocalDateTime.of(2026, 12, 20, 0, 0));
        user1.addCashFlow(repeating);

        repeating = new Repeating(20, categoryExpense1, LocalDateTime.now(), LocalDateTime.now().plusMonths(1), recurPeriod.Monthly);
        repeating.DebugSetDateStart(LocalDateTime.of(2025, 12, 20, 0, 0));
        repeating.DebugSetDateEnd(LocalDateTime.of(2026, 12, 20, 0, 0));
        user1.addCashFlow(repeating);

        repeating = new Repeating(80, categoryExpense2, LocalDateTime.now(), LocalDateTime.now().plusMonths(1), recurPeriod.Weekly);
        repeating.DebugSetDateStart(LocalDateTime.of(2024, 12, 20, 0, 0));
        repeating.DebugSetDateEnd(LocalDateTime.of(2026, 12, 20, 0, 0));
        user1.addCashFlow(repeating);

        repeating = new Repeating(110, categoryExpense3, LocalDateTime.now(), LocalDateTime.now().plusMonths(1), recurPeriod.Monthly);
        repeating.DebugSetDateStart(LocalDateTime.of(2024, 5, 20, 0, 0));
        repeating.DebugSetDateEnd(LocalDateTime.of(2024, 11, 20, 0, 0));
        user1.addCashFlow(repeating);

        repeating = new Repeating(500, categoryExpense2, LocalDateTime.now(), LocalDateTime.now().plusMonths(1), recurPeriod.Monthly);
        repeating.DebugSetDateStart(LocalDateTime.of(2024, 12, 20, 0, 0));
        repeating.DebugSetDateEnd(LocalDateTime.of(2026, 12, 20, 0, 0));
        user2.addCashFlow(repeating);

        //income
        repeating = new Repeating(600, categoryIncome1, LocalDateTime.now(), LocalDateTime.now().plusMonths(1), recurPeriod.Monthly);
        repeating.DebugSetDateStart(LocalDateTime.of(2024, 12, 20, 0, 0));
        repeating.DebugSetDateEnd(LocalDateTime.of(2026, 12, 20, 0, 0));
        user1.addCashFlow(repeating);

        oneOff = new OneOff(1000, categoryIncome2, LocalDateTime.now());
        oneOff.DebugSetDateStart(LocalDateTime.of(2024, 12, 20, 0, 0));
        user1.addCashFlow(oneOff);

        oneOff = new OneOff(3000, categoryIncome2, LocalDateTime.now());
        oneOff.DebugSetDateStart(LocalDateTime.of(2025, 1, 20, 0, 0));
        user1.addCashFlow(oneOff);

        oneOff = new OneOff(10000, categoryIncome2, LocalDateTime.now());
        oneOff.DebugSetDateStart(LocalDateTime.of(2024, 5, 20, 0, 0));
        user1.addCashFlow(oneOff);

        //moneyboxes
        MoneyBox moneyBox1 = new MoneyBox("Laptop", 500);
        MoneyBox moneyBox2 = new MoneyBox("Drawing board", 200);
        user1.addMoneyBox(moneyBox1);
        user1.addMoneyBox(moneyBox2);

        MoneyBox moneyBox3 = new MoneyBox("Laptop", 2500);
        user2.addMoneyBox(moneyBox3);

        moneyBox1.addMoney(new Allowance(100, LocalDateTime.now()));
        moneyBox3.addMoney(new Allowance(800, LocalDateTime.now()));


    }

    /**
     * @return DAO of every family
     */
    public abstract FamilyDAO getFamilyDAO();

    /**
     * @return DAO of every user
     */
    public abstract UserDAO getUserDAO();
}
