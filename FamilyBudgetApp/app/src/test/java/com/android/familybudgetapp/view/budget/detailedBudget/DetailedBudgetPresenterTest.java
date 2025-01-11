package com.android.familybudgetapp.view.budget.detailedBudget;

import static org.junit.Assert.*;

import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.CashFlow;
import com.android.familybudgetapp.domain.CashFlowCategory;
import com.android.familybudgetapp.domain.Expense;
import com.android.familybudgetapp.domain.FamPos;
import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.Income;
import com.android.familybudgetapp.domain.OneOff;
import com.android.familybudgetapp.domain.Repeating;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.domain.recurPeriod;
import com.android.familybudgetapp.memorydao.UserDAOMemory;
import com.android.familybudgetapp.utilities.Quadruples;
import com.android.familybudgetapp.view.budget.cashFlowManager.CashFlowManagerInterface;
import com.android.familybudgetapp.view.budget.cashFlowManager.PersonalUserStrategy;
import com.android.familybudgetapp.view.budget.cashFlowManager.MonthlyManager;
import com.android.familybudgetapp.view.budget.showBudget.cashFlowType;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DetailedBudgetPresenterTest {

    private DetailedBudgetPresenter presenter;

    @Before
    public void setUp(){
        presenter = new DetailedBudgetPresenter();
    }

    @Test
    public void setGetView() {
        assertNull(presenter.getView());
        DetailedBudgetStub view = new DetailedBudgetStub();
        presenter.setView(view);
        assertEquals(view, presenter.getView());
    }

    @Test
    public void setGetUserDao() {
        assertNull(presenter.getUserDAO());
        UserDAO dao = new UserDAOMemory();
        presenter.setUserDao(dao);
        assertEquals(dao, presenter.getUserDAO());
    }

    @Test
    public void setGetCashFlowManager() {
        assertNull(presenter.getCashFlowManager());
        CashFlowManagerInterface manager = new MonthlyManager();
        presenter.setCashFlowManager(manager);
        assertEquals(manager, presenter.getCashFlowManager());

    }

    @Test
    public void setGetCurrentUser() {
        assertNull(presenter.getCurrentUser());
        User user = new User("Test", "Test", "Test", FamPos.Protector, new Family("Test"));
        presenter.setCurrentUser(user);
        assertEquals(user, presenter.getCurrentUser());
    }

    @Test
    public void setGetType() {
        assertNull(presenter.getType());
        presenter.setType(cashFlowType.Expense);
        assertEquals(cashFlowType.Expense, presenter.getType());
    }

    @Test
    public void getCashFlows() {
        CashFlowManagerInterface manager = new MonthlyManager();
        User user = new User("Test", "Test", "Test", FamPos.Protector, new Family("Test"));
        manager.setUserRetrievalStrategy(new PersonalUserStrategy(user));
        presenter.setCashFlowManager(manager);

        CashFlowCategory income = new Income("Job");
        CashFlowCategory expense = new Expense("Food", 100);
        user.addCashFlow(new OneOff(50, income, LocalDateTime.now()));
        user.addCashFlow(new OneOff(80, expense, LocalDateTime.now().plusMinutes(2)));

        presenter.setType(cashFlowType.Income);
        assertEquals(manager.getIncome(), presenter.getCashFlows());
        presenter.setType(cashFlowType.Expense);
        assertEquals(manager.getExpense(), presenter.getCashFlows());
        presenter.setType(cashFlowType.Both);
        assertEquals(manager.getExpenseAndIncome(), presenter.getCashFlows());
    }

    @Test
    public void TestFormattedCashFlowChangesToCode(){
        CashFlowManagerInterface manager = new MonthlyManager();
        Family family = new Family("Test");
        User user = new User("Test", "Test", "Test", FamPos.Protector, family);
        manager.setUserRetrievalStrategy(new PersonalUserStrategy(user));
        presenter.setCashFlowManager(manager);
        presenter.setView(new DetailedBudgetStub());

        CashFlowCategory income = new Income("Job");
        CashFlowCategory expense = new Expense("Food", 100);
        user.addCashFlow(new OneOff(50, income, LocalDateTime.now()));
        user.addCashFlow(new Repeating(80, expense, LocalDateTime.now(), LocalDateTime.now().plusMonths(1), recurPeriod.Monthly));
        UserDAO userDAO = new UserDAOMemory();
        family.addMember(user);
        userDAO.save(user);
        presenter.setUserDao(userDAO);

        presenter.setType(cashFlowType.Income);
        ((DetailedBudgetStub) presenter.getView()).setType("Surplus");
        boolean stateSurplus = ((DetailedBudgetStub) presenter.getView()).getType().equals("Surplus");
        List<Quadruples<String, String, Integer, List<LocalDateTime>>> myList;
        myList = formatCashFlow(stateSurplus);
        List<Quadruples<String, String, Integer, List<LocalDateTime>>> result = presenter.getFormatedCashFlows();
        assertEquals(myList.size(), result.size());
        for (var obj: result)
            assertTrue(myList.contains(obj));

        ((DetailedBudgetStub) presenter.getView()).setType("Income");
        stateSurplus = ((DetailedBudgetStub) presenter.getView()).getType().equals("Surplus");
        myList = formatCashFlow(stateSurplus);
        result = presenter.getFormatedCashFlows();
        assertEquals(myList.size(), result.size());
        for (var obj: result)
            assertTrue(myList.contains(obj));

        presenter.setType(cashFlowType.Expense);
        ((DetailedBudgetStub) presenter.getView()).setType("Surplus");
        stateSurplus = ((DetailedBudgetStub) presenter.getView()).getType().equals("Surplus");
        myList = formatCashFlow(stateSurplus);
        result = presenter.getFormatedCashFlows();
        assertEquals(myList.size(), result.size());
        for (var obj: result)
            assertTrue(myList.contains(obj));

        ((DetailedBudgetStub) presenter.getView()).setType("Expense");
        stateSurplus = ((DetailedBudgetStub) presenter.getView()).getType().equals("Surplus");
        myList = formatCashFlow(stateSurplus);
        result = presenter.getFormatedCashFlows();
        assertEquals(myList.size(), result.size());
        for (var obj: result)
            assertTrue(myList.contains(obj));

    }

    public List<Quadruples<String, String, Integer, List<LocalDateTime>>> formatCashFlow(boolean stateSurplus){
        Map<Long, List<CashFlow>> cashFlows = presenter.getCashFlows();
        List<Quadruples<String, String, Integer, List<LocalDateTime>>> myList = new ArrayList<>();
        for (Map.Entry<Long, List<CashFlow>> item: cashFlows.entrySet())
        {
            int userSurplus = 0;
            for (CashFlow cashFlow: item.getValue())
            {
                if (stateSurplus)
                {
                    if (cashFlow.getCategory() instanceof Income)
                        userSurplus+=presenter.getCashFlowManager().getAmount(cashFlow);
                    else if (cashFlow.getCategory() instanceof Expense)
                        userSurplus-=presenter.getCashFlowManager().getAmount(cashFlow);
                }
                else
                {
                    String category = cashFlow.getCategory().getName();
                    String owner = presenter.getUserDAO().findByID(item.getKey()).getName();
                    Integer amount = presenter.getCashFlowManager().getAmount(cashFlow);
                    List<LocalDateTime> dateTimeList = new ArrayList<>();
                    dateTimeList.add(cashFlow.getDateStart());
                    if (cashFlow instanceof Repeating)
                        dateTimeList.add(((Repeating)cashFlow).getDateEnd());
                    myList.add(new Quadruples<>(category, owner, amount, dateTimeList));
                }
            }
            if (stateSurplus) // reuse Quadruple recycler to show detailed surplus, this order places them in the same line
                myList.add(new Quadruples<>(presenter.getUserDAO().findByID(item.getKey()).getName(), "", userSurplus, new ArrayList<>()));
        }
        return myList;
    }

}