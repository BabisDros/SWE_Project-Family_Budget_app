package com.android.familybudgetapp.view.Budget.ShowBudget;


import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.CashFlow;
import com.android.familybudgetapp.domain.CashFlowCategory;
import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.MonthlySurplus;
import com.android.familybudgetapp.domain.OneOff;
import com.android.familybudgetapp.domain.Repeating;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.domain.recurPeriod;
import com.android.familybudgetapp.utilities.Tuples;
import com.android.familybudgetapp.view.Budget.CashFlowManager.CashFlowManagerInterface;
import com.android.familybudgetapp.view.Budget.CashFlowManager.FamilyUserStrategy;
import com.android.familybudgetapp.view.Budget.CashFlowManager.UserRetrievalStrategy;
import com.android.familybudgetapp.view.Budget.CashFlowManager.monthlyManager;
import com.android.familybudgetapp.view.base.BasePresenter;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

public class BudgetPresenter extends BasePresenter<BudgetView> {
    private BudgetView view;
    private UserDAO userDAO;
    private CashFlowManagerInterface cashFlowManager;
    private User currentUser;


    public void setView(BudgetView view)
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

    public CashFlowManagerInterface getCashFlowManager(){
        return cashFlowManager;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public List<Tuples<String, Integer>> getExpensePerCategory()
    {
        return getCashFlowManager().CalculateAmountPerCategory(cashFlowType.Expense);
    }
    public List<Tuples<String, Integer>> getIncomePerCategory()
    {
        return getCashFlowManager().CalculateAmountPerCategory(cashFlowType.Income);
    }

    public int calculateSurplus()
    {
        return cashFlowManager.CalculateSurplus();
    }

    public void updateFamilySurplus(int surplus) {
        UserRetrievalStrategy strategy = cashFlowManager.getUserRetrievalStrategy();
        if (cashFlowManager instanceof monthlyManager && strategy instanceof FamilyUserStrategy) {
            Family family = ((FamilyUserStrategy) strategy).getFamily();

            // Update or create a new MonthlySurplus for the family object
            YearMonth currentMonth = YearMonth.now();
            MonthlySurplus monthlySurplusObj = family.getMonthlySurpluses().putIfAbsent(currentMonth, new MonthlySurplus(currentMonth, surplus));
            if (monthlySurplusObj != null) {
                monthlySurplusObj.setSurplus(surplus);
            }
        }
    }

    public void addCashFlow(String categoryName, String cashFlowValue,
                              boolean isRecurring, LocalDateTime dateStart, LocalDateTime dateEnd,
                              int recurrencePeriodIdx) {

        // category
        if (categoryName.isEmpty()) {
            view.showErrorMessage("Error", "Please select a category.");
            return;
        }
        CashFlowCategory category = getCurrentUser().getFamily().getCashFlowCategories().get(categoryName);

        // amount
        if (cashFlowValue.isEmpty() || cashFlowValue.startsWith(".") ||
                cashFlowValue.endsWith(".") || cashFlowValue.equals("0")) {
            view.showErrorMessage("Error", "Please enter a valid amount.");
            return;
        }
        double euroValue = Double.parseDouble(cashFlowValue);
        int centsValue = (int) (euroValue * 100);

        // date start
        if (!CashFlow.validateDateStart(dateStart)) {
            view.showErrorMessage("Error", "Please enter a valid start date.");
            return;
        }

        // recurrence period
        recurPeriod period = recurPeriod.values()[recurrencePeriodIdx];

        CashFlow newCashFlow;

        if (isRecurring) {
            // date end
            if (!Repeating.validateDateEnd(dateStart, dateEnd)) {
                view.showErrorMessage("Error", "Please enter a valid end date.");
                return;
            }
            newCashFlow = new Repeating(centsValue, category, dateStart, dateEnd, period);
        } else {
            newCashFlow = new OneOff(centsValue, category, dateStart);
        }
        currentUser.addCashFlow(newCashFlow);
    }
}
