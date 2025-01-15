package com.android.familybudgetapp.view.budget.showBudget;


import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.CashFlow;
import com.android.familybudgetapp.domain.CashFlowCategory;
import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.MonthlySurplus;
import com.android.familybudgetapp.domain.OneOff;
import com.android.familybudgetapp.domain.Repeating;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.domain.recurPeriod;
import com.android.familybudgetapp.utilities.CommonStringValidations;
import com.android.familybudgetapp.utilities.Tuples;
import com.android.familybudgetapp.view.budget.cashFlowManager.CashFlowManagerInterface;
import com.android.familybudgetapp.view.budget.cashFlowManager.FamilyUserStrategy;
import com.android.familybudgetapp.view.budget.cashFlowManager.MonthlyManager;
import com.android.familybudgetapp.view.base.BasePresenter;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

public class BudgetPresenter extends BasePresenter<BudgetView> {
    private BudgetView view;
    private UserDAO userDAO;
    private CashFlowManagerInterface cashFlowManager;
    private MonthlyManager familyMonthlySurplusManager;
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

    public void setFamilyMonthlySurplusManager(MonthlyManager manager) {
        this.familyMonthlySurplusManager = manager;
    }

    public CashFlowManagerInterface getFamilyMonthlySurplusManager() {
        return familyMonthlySurplusManager;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Retrieves a list of expense categories and their corresponding amounts
     * @return A list of Tuples representing expense categories and their respective amounts.
     */
    public List<Tuples<String, Integer>> getExpensePerCategory()
    {
        return getCashFlowManager().CalculateAmountPerCategory(cashFlowType.Expense);
    }

    /**
     * Retrieves a list of income categories and their corresponding amounts
     * @return A list of Tuples representing income categories and their respective amounts.
     */
    public List<Tuples<String, Integer>> getIncomePerCategory()
    {
        return getCashFlowManager().CalculateAmountPerCategory(cashFlowType.Income);
    }

    /**
     * Calculates the surplus (income - expenses) for the current user or family.
     *
     * @return the surplus amount as an integer.
     */
    public int calculateSurplus()
    {
        return cashFlowManager.CalculateSurplus();
    }

    /**
     * Updates the family's monthly surplus for the current month.
     *
     * @param surplus the surplus amount to update.
     */
    public void updateFamilySurplus(int surplus)
    {
        FamilyUserStrategy strategy = (FamilyUserStrategy) familyMonthlySurplusManager.getUserRetrievalStrategy();
        Family family = strategy.getFamily();

        // Update or create a new MonthlySurplus for the family object
        YearMonth currentMonth = YearMonth.now();
        MonthlySurplus monthlySurplusObj = family.getMonthlySurpluses().putIfAbsent(currentMonth, new MonthlySurplus(currentMonth, surplus));
        if (monthlySurplusObj != null) {
            monthlySurplusObj.setSurplus(surplus);
        }
    }

    /**
     * Adds a new cash flow (income or expense) for the current user.
     *
     * @param categoryName       the name of the category for the cash flow.
     * @param cashFlowValue      the amount of the cash flow in euros as a string.
     * @param isRecurring        boolean, whether the cash flow is recurring.
     * @param dateStart          the start date of the cash flow.
     * @param dateEnd            the end date of the cash flow, ignored if not recurring
     * @param recurrencePeriodIdx the {@link recurPeriod} enum index of the recurrence period.
     */
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
        if (CommonStringValidations.isAmountInvalid(cashFlowValue)) {
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
        updateFamilySurplus(familyMonthlySurplusManager.CalculateSurplus());
        userDAO.save(currentUser);
    }

    /**
     * Retrieves the surplus left from the previous month for the family.
     *
     * @return the surplus amount in cents as an integer.
     * Returns 0 if no surplus exists for the previous month.
     */
    public int getPreviousSurplusLeft() {
        Family family = getCurrentUser().getFamily();
        YearMonth previousMonth = YearMonth.now().minusMonths(1);
        if (!family.getMonthlySurpluses().containsKey(previousMonth)) {
            return 0;
        }
        return family.getMonthlySurpluses().get(previousMonth).getSurplus();
    }

    /**
     * Moves any unallocated surplus to the family's savings.
     */
    public void moveUnallocatedSurplusToSavings() {
        familyMonthlySurplusManager.moveUnallocatedSurplusToSavings(getCurrentUser().getFamily());
    }
}
