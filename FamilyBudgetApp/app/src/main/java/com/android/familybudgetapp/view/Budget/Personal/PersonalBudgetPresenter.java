package com.android.familybudgetapp.view.Budget.Personal;


import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.CashFlow;
import com.android.familybudgetapp.domain.Income;
import com.android.familybudgetapp.domain.OneOff;
import com.android.familybudgetapp.domain.Repeating;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.utilities.Tuples;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonalBudgetPresenter{
    private PersonalBudgetView view;
    private UserDAO userDAO;

    private User currentUser;


    public void setView(PersonalBudgetView view)
    {
        this.view = view;
    }

    public void setUserDao(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public List<CashFlow> getExpensesOfCurrentUser()
    {
        List<CashFlow> newList = currentUser.getCashFlows();
        for(CashFlow item: newList)
        {
            if (item.getCategory().getClass() == Income.class)
            {
                newList.remove(item);
                continue;
            }
            if (item.getClass() == OneOff.class)
            {
                if (item.getDateStart().toLocalDate().isBefore(LocalDate.now()))
                {
                    newList.remove(item);
                }
            } else if (item.getClass() == Repeating.class)
            {
                if (((Repeating)item).getDateEnd().toLocalDate().isBefore(LocalDate.now()))
                {
                    newList.remove(item);
                }
            }
        }
        return newList;
    }


    public List<Tuples<String, Integer>> getExpensePerCategory(List<CashFlow> cashFlowList)
    {
        Map<String, Integer> dict = new HashMap<>();
        List<Tuples<String, Integer>> tuplesList = new ArrayList<>();
        for(CashFlow item: cashFlowList)
        {
            String name = item.getCategory().getName();
            dict.put(name, dict.getOrDefault(name, 0) + item.getMonthlyAmount());
        }
        for(Map.Entry<String, Integer> entry: dict.entrySet())
        {
            tuplesList.add(new Tuples<>(entry.getKey(), entry.getValue()));
        }
        return tuplesList;
    }
}
