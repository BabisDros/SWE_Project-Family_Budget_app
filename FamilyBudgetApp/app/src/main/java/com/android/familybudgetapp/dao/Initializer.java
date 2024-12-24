package com.android.familybudgetapp.dao;

import com.android.familybudgetapp.domain.Expense;
import com.android.familybudgetapp.domain.FamPos;
import com.android.familybudgetapp.domain.Family;
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

        FamilyDAO familyDAO = getFamilyDAO();
        Family family = new Family("Family surname test");
        familyDAO.save(family);
        Expense category1 = new Expense("Food", 200);
        family.addCashFlowCategory(category1);
        Expense category2 = new Expense("Clothes", 500);
        family.addCashFlowCategory(category2);

        UserDAO userDAO = getUserDAO();
        User user1 = new User("Name test", "usernameTest", "passwordTest", FamPos.Protector, family);
        User user2 = new User("Name test2", "usernameTest2", "passwordTest2", FamPos.Member, family);
        userDAO.save(user1);
        userDAO.save(user2);

        currentUserID = user1.getID();

        user1.addCashFlow(new Repeating(150, category1, LocalDateTime.of(2024, 12, 20, 0, 0),
                LocalDateTime.of(2026, 12, 20, 0, 0), recurPeriod.Monthly));
        user1.addCashFlow(new Repeating(20, category1, LocalDateTime.of(2024, 12, 20, 0, 0),
                LocalDateTime.of(2026, 12, 20, 0, 0), recurPeriod.Monthly));
        user1.addCashFlow(new Repeating(80, category2, LocalDateTime.of(2024, 12, 20, 0, 0),
                LocalDateTime.of(2026, 12, 20, 0, 0), recurPeriod.Weekly));
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
