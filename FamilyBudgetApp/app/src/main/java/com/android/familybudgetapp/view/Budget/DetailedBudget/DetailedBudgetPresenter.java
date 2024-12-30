package com.android.familybudgetapp.view.Budget.DetailedBudget;

import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.CashFlow;
import com.android.familybudgetapp.domain.Expense;
import com.android.familybudgetapp.domain.Income;
import com.android.familybudgetapp.domain.Repeating;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.utilities.Quadruples;
import com.android.familybudgetapp.view.Budget.CashFlowManager.CashFlowManagerInterface;
import com.android.familybudgetapp.view.Budget.ShowBudget.cashFlowType;

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


    public void setView(DetailedBudgetView view)
    {
        this.view = view;
    }

    public void setUserDao(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setCashFlowManager(CashFlowManagerInterface cashFlowManagerInterface) {
        this.cashFlowManager = cashFlowManagerInterface;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void setType(cashFlowType type){
        this.type = type;
    }

    public CashFlowManagerInterface getCashFlowManager(){
        return cashFlowManager;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public cashFlowType getType(){
        return type;
    }

    public Map<Long, List<CashFlow>> getCashFlows(){
        switch (type){
            case Expense:
                return cashFlowManager.getExpense();
            case Income:
                return cashFlowManager.getIncome();
            case Both:
                return cashFlowManager.getExpenseAndIncome();

        }
        return new HashMap<>();
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
            if (stateSurplus) // reuse Quadruple recycler to show detailed surplus
                myList.add(new Quadruples<>("", userDAO.findByID(item.getKey()).getName(), userSurplus, new ArrayList<>()));
        }
        return myList;
    }
}
