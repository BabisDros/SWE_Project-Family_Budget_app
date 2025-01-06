package com.android.familybudgetapp.view.HomePage;

/**
 * This is the page the user is moved to
 * after having successfully logged on
 */
public interface HomePageView {

    /**
     * When clicked on PersonalBudgetActivity activity
     * user is moved there from Home page
     */
    void personalBudget();

    /**
     * When clicked on ShowMoneyBoxesActivity activity
     * user is moved there from Home page
     */
    void moneyBoxes();

    /**
     * When clicked on GlobalStatisticsActivity activity
     * user is moved there from Home page
     */
    void stats();

    void goToMemberManagement();

    void goToCashFlowCategories();
}
