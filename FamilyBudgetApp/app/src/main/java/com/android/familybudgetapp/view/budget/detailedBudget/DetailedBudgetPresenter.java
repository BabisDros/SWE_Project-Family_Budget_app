package com.android.familybudgetapp.view.budget.detailedBudget;

import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.CashFlow;
import com.android.familybudgetapp.domain.Expense;
import com.android.familybudgetapp.domain.Income;
import com.android.familybudgetapp.domain.Repeating;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.utilities.Quadruples;
import com.android.familybudgetapp.view.budget.cashFlowManager.CashFlowManagerInterface;
import com.android.familybudgetapp.view.budget.showBudget.cashFlowType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailedBudgetPresenter {
    private DetailedBudgetView view;
    private UserDAO userDAO;
    private User currentUser;
    private CashFlowManagerInterface cashFlowManager;
    private cashFlowType type;


    /**
     * Sets the {@link DetailedBudgetView} for the presenter.
     *
     * @param view the {@link DetailedBudgetView} instance to be associated with this presenter
     */
    public void setView(DetailedBudgetView view)
    {
        this.view = view;
    }

    /**
     * Sets the {@link UserDAO} instance to be used by the presenter.
     *
     * @param userDAO the {@link UserDAO} instance to associate with this presenter
     */
    public void setUserDao(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Sets the {@link CashFlowManagerInterface} instance for handling {@link CashFlow} related operations.
     *
     * @param cashFlowManagerInterface the {@link CashFlowManagerInterface} instance to associate with this presenter
     */
    public void setCashFlowManager(CashFlowManagerInterface cashFlowManagerInterface) {
        this.cashFlowManager = cashFlowManagerInterface;
    }

    /**
     * Sets the current user for the presenter.
     *
     * @param user the {@link User} instance to be set as the current user
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * Sets the {@link CashFlow} type for this presenter.
     *
     * @param type the {@link CashFlow} type to be associated with this presenter, 
     *             which determines whether incomes, expenses, or both are handled.
     */
    public void setType(cashFlowType type){
        this.type = type;
    }

    /**
     * Retrieves the {@link DetailedBudgetView} associated with this presenter.
     *
     * @return the {@link DetailedBudgetView} instance currently set for this presenter.
     */
    public DetailedBudgetView getView(){
        return view;
    }

    /**
     * Retrieves the {@link UserDAO} instance associated with the presenter.
     *
     * @return the {@link UserDAO} instance currently set for this presenter.
     */
    public UserDAO getUserDAO() {
        return userDAO;
    }

    /**
     * Retrieves the {@link CashFlowManagerInterface} instance associated with this presenter.
     *
     * @return the {@link CashFlowManagerInterface} instance currently set for managing {@link CashFlow} operations.
     */
    public CashFlowManagerInterface getCashFlowManager(){
        return cashFlowManager;
    }

    /**
     * Retrieves the currently active user associated with this presenter.
     *
     * @return the {@link User} instance representing the current user.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Retrieves the {@link CashFlow} type associated with this presenter.
     *
     * @return the current {@link CashFlow} type, which determines whether incomes, expenses, or both are handled.
     */
    public cashFlowType getType(){
        return type;
    }

    /**
     * Retrieves the {@link CashFlow}s categorized by user IDs and their respective {@link CashFlow} lists.
     * The returned {@link CashFlow}s depend on the type of {@link CashFlow} selection ({@link cashFlowType#Income},
     * {@link cashFlowType#Expense}, or {@link cashFlowType#Both}).
     *
     * @return A mapping of user IDs to their corresponding lists of {@link CashFlow}s.
     *         If the type is {@link cashFlowType#Expense}, only expenses are returned.
     *         If the type is {@link cashFlowType#Income}, only incomes are returned.
     *         If the type is {@link cashFlowType#Both}, both expenses and incomes are returned.
     *         If no type matches, an empty map is returned.
     */
    public Map<Long, List<CashFlow>> getCashFlows(){
        switch (type){
            case Expense:
                return cashFlowManager.getExpense();
            case Income:
                return cashFlowManager.getIncome();
            case Both:
                return cashFlowManager.getExpenseAndIncome();
            default:
                return new HashMap<>();
        }
    }


    /**
     * <pre>
     * List of {@link Quadruples} which correspond to:
     * Category of CashFlow
     * Name of user who added it
     * Amount of CashFlow for the wanted range
     * DateStart and DateEnd (if it exists)</pre>
     * @return CashFlow and owner data in a format the Recycler can make use of
     */
    public List<Quadruples<String, String, Integer, List<LocalDateTime>>> getFormatedCashFlows()
    {
        Map<Long, List<CashFlow>> cashFlows = getCashFlows();
        List<Quadruples<String, String, Integer, List<LocalDateTime>>> myList = new ArrayList<>();
        boolean stateSurplus = view.getState().get("type").equals("Surplus");
        for (Map.Entry<Long, List<CashFlow>> item: cashFlows.entrySet())
        {
            int userSurplus = 0;
            for (CashFlow cashFlow: item.getValue())
            {
                if (stateSurplus)
                {
                    if (cashFlow.getCategory() instanceof Income)
                        userSurplus+=cashFlowManager.getAmount(cashFlow);
                    else if (cashFlow.getCategory() instanceof Expense)
                        userSurplus-=cashFlowManager.getAmount(cashFlow);
                }
                else
                {
                    String category = cashFlow.getCategory().getName();
                    String owner = userDAO.findByID(item.getKey()).getName();
                    Integer amount = cashFlowManager.getAmount(cashFlow);
                    List<LocalDateTime> dateTimeList = new ArrayList<>();
                    dateTimeList.add(cashFlow.getDateStart());
                    if (cashFlow instanceof Repeating)
                        dateTimeList.add(((Repeating)cashFlow).getDateEnd());
                    myList.add(new Quadruples<>(category, owner, amount, dateTimeList));
                }
            }
            if (stateSurplus) // reuse Quadruple recycler to show detailed surplus, this order places them in the same line
                myList.add(new Quadruples<>(userDAO.findByID(item.getKey()).getName(), "", userSurplus, new ArrayList<>()));
        }
        return myList;
    }
}
