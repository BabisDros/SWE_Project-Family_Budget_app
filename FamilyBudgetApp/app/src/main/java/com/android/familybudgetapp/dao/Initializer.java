package com.android.familybudgetapp.dao;

import com.android.familybudgetapp.domain.Allowance;
import com.android.familybudgetapp.domain.CashFlow;
import com.android.familybudgetapp.domain.Expense;
import com.android.familybudgetapp.domain.FamPos;
import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.Income;
import com.android.familybudgetapp.domain.MoneyBox;
import com.android.familybudgetapp.domain.MonthlySurplus;
import com.android.familybudgetapp.domain.OneOff;
import com.android.familybudgetapp.domain.Repeating;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.domain.recurPeriod;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public abstract class Initializer {

    public static String protector1Username ="usernameTest";
    public static String protector1Password ="passwordTest";

    public static String member1Username ="usernameTest2";

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

        Family family2 = new Family("Family surname test2");
        familyDAO.save(family2);

        Family family3EmptyCategories = new Family("Family surname test3");
        familyDAO.save(family3EmptyCategories);

        //categories
        Expense categoryExpense1 = new Expense("Food", 20000);
        family.addCashFlowCategory(categoryExpense1);
        Expense categoryExpense2 = new Expense("Clothes", 5000);
        family.addCashFlowCategory(categoryExpense2);
        Expense categoryExpense3 = new Expense("Gift", 80000);
        family.addCashFlowCategory(categoryExpense3);
        Income categoryIncome1 = new Income("Job");
        family.addCashFlowCategory(categoryIncome1);
        Income categoryIncome2 = new Income("Casino");
        family.addCashFlowCategory(categoryIncome2);

        Income categoryIncome2_1 = new Income("Job");
        family2.addCashFlowCategory(categoryIncome2_1);

        //users
        UserDAO userDAO = getUserDAO();
        User protector1 = new User("name1", protector1Username, protector1Password, FamPos.Protector, family);
        User member1 = new User("name2", member1Username, "passwordTest2", FamPos.Member, family);
        family.addMember(protector1);
        family.addMember(member1);
        userDAO.save(protector1);
        userDAO.save(member1);

        User protector2 = new User("displayNameTest2 1", "Test", "passwordTest", FamPos.Protector, family);
        family2.addMember(protector2);
        userDAO.save(protector2);

        User protector3= new User("displayNameTest4", "Test4", "passwordTest", FamPos.Protector, family3EmptyCategories);
        family3EmptyCategories.addMember(protector3);
        userDAO.save(protector3);

        currentUserID = protector1.getID();

        // Requires DebugSet, since we are re-adding old cashFlows
        Repeating repeating;
        OneOff oneOff;

        //expenses
        repeating = new Repeating(15000, categoryExpense1, LocalDateTime.now(), LocalDateTime.now().plusMonths(1), recurPeriod.Monthly);
        repeating.DebugSetDateStart(LocalDateTime.of(2024, 12, 20, 0, 0));
        repeating.DebugSetDateEnd(LocalDateTime.of(2026, 12, 20, 0, 0));
        protector1.addCashFlow(repeating);

        repeating = new Repeating(2000, categoryExpense1, LocalDateTime.now(), LocalDateTime.now().plusMonths(1), recurPeriod.Monthly);
        repeating.DebugSetDateStart(LocalDateTime.of(2025, 12, 20, 0, 0));
        repeating.DebugSetDateEnd(LocalDateTime.of(2026, 12, 20, 0, 0));
        protector1.addCashFlow(repeating);

        repeating = new Repeating(8000, categoryExpense2, LocalDateTime.now(), LocalDateTime.now().plusMonths(1), recurPeriod.Weekly);
        repeating.DebugSetDateStart(LocalDateTime.of(2024, 12, 20, 0, 0));
        repeating.DebugSetDateEnd(LocalDateTime.of(2026, 12, 20, 0, 0));
        protector1.addCashFlow(repeating);

        repeating = new Repeating(11000, categoryExpense3, LocalDateTime.now(), LocalDateTime.now().plusMonths(1), recurPeriod.Monthly);
        repeating.DebugSetDateStart(LocalDateTime.of(2024, 5, 20, 0, 0));
        repeating.DebugSetDateEnd(LocalDateTime.of(2024, 11, 20, 0, 0));
        protector1.addCashFlow(repeating);

        repeating = new Repeating(5000, categoryExpense2, LocalDateTime.now(), LocalDateTime.now().plusMonths(1), recurPeriod.Monthly);
        repeating.DebugSetDateStart(LocalDateTime.of(2024, 12, 20, 0, 0));
        repeating.DebugSetDateEnd(LocalDateTime.of(2026, 12, 20, 0, 0));
        member1.addCashFlow(repeating);

        //income
        repeating = new Repeating(60000, categoryIncome1, LocalDateTime.now(), LocalDateTime.now().plusMonths(1), recurPeriod.Monthly);
        repeating.DebugSetDateStart(LocalDateTime.of(2024, 12, 20, 0, 0));
        repeating.DebugSetDateEnd(LocalDateTime.of(2026, 12, 20, 0, 0));
        protector1.addCashFlow(repeating);

        oneOff = new OneOff(10000, categoryIncome2, LocalDateTime.now());
        oneOff.DebugSetDateStart(LocalDateTime.of(2024, 12, 20, 0, 0));
        protector1.addCashFlow(oneOff);

        oneOff = new OneOff(3000, categoryIncome2, LocalDateTime.now());
        oneOff.DebugSetDateStart(LocalDateTime.of(2025, 1, 20, 0, 0));
        protector1.addCashFlow(oneOff);

        oneOff = new OneOff(10000, categoryIncome2, LocalDateTime.now());
        oneOff.DebugSetDateStart(LocalDateTime.of(2024, 5, 20, 0, 0));
        protector1.addCashFlow(oneOff);

        oneOff = new OneOff(600000, categoryIncome2, LocalDateTime.now());
        oneOff.DebugSetDateStart(LocalDateTime.of(2023, 5, 20, 0, 0));
        protector1.addCashFlow(oneOff);

        repeating = new Repeating(100000, categoryIncome1, LocalDateTime.now(), LocalDateTime.now().plusMonths(1), recurPeriod.Monthly);
        repeating.DebugSetDateStart(LocalDateTime.of(2023, 10, 20, 0, 0));
        repeating.DebugSetDateEnd(LocalDateTime.of(2026, 12, 20, 0, 0));
        protector2.addCashFlow(repeating);

        //moneyboxes
        MoneyBox moneyBox1 = new MoneyBox("Laptop", 50000);
        MoneyBox moneyBox2 = new MoneyBox("Drawing board", 2000);
        protector1.addMoneyBox(moneyBox1);
        protector1.addMoneyBox(moneyBox2);

        MoneyBox moneyBox3 = new MoneyBox("Laptop", 150000);
        member1.addMoneyBox(moneyBox3);

        moneyBox1.addMoney(new Allowance(10000, LocalDateTime.now()));
        moneyBox3.addMoney(new Allowance(80000, LocalDateTime.now()));

        calculateMonthlySurpluses();

    }

    /**
     * @return DAO of every family
     */
    public abstract FamilyDAO getFamilyDAO();

    /**
     * @return DAO of every user
     */
    public abstract UserDAO getUserDAO();

    /**
     * Set the MonthlySurplus objects for each family
     * Assume that all the surplus goes to their saving
     */
    private void calculateMonthlySurpluses(){
        Optional<YearMonth> minDate;
        // get earliest Date of a surplus of any family
        for (Family family: getFamilyDAO().findAll())
        {
            List<CashFlow> cashFlows = new ArrayList<>();
            for (User user: family.getMembers().values())
                cashFlows.addAll(user.getCashFlows());

            Optional<LocalDateTime> temp = cashFlows.stream().map(CashFlow::getDateStart).min(Comparator.naturalOrder());

            if (temp.isEmpty()) continue;
            YearMonth currentDate = YearMonth.from(temp.get());
            while (!currentDate.isAfter(YearMonth.now())) // make every monthly surplus till this month
            {
                if (currentDate.getMonth().equals(Month.JANUARY))
                    family.resetYearSavings();
                MonthlySurplus monthlySurplus = new MonthlySurplus(currentDate);
                for(CashFlow cashFlow: cashFlows)
                {
                    if (cashFlow.getCategory() instanceof Income)
                        monthlySurplus.addCashFlowToSurplus(cashFlow);
                    else if (cashFlow.getCategory() instanceof Expense)
                        monthlySurplus.removeCashFlowFromSurplus(cashFlow);
                }
                family.addSurplus(monthlySurplus);
                // do not add to savings for current month or previous month, so there's surplus left to allocate
                if (currentDate.isBefore(YearMonth.now().minusMonths(1))) // do not add to savings for current month οr previous month
                {
                    if (monthlySurplus.getSurplus() >= 0)
                        family.addToSavings(monthlySurplus.getSurplus());
                    else
                        family.removeFromSavings(Math.abs(monthlySurplus.getSurplus()));
                }
                currentDate = currentDate.plusMonths(1);
            }

        }
    }
}
