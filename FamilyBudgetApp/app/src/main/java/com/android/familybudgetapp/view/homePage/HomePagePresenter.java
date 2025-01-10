package com.android.familybudgetapp.view.homePage;

public class HomePagePresenter {
    private HomePageView view;

    /**
     * Initialize Presenter
     * @param view Instance of view
     */
    public HomePagePresenter(HomePageView view)
    {
        this.view = view;
    }

    /**
     * When clicked on PersonalBudgetActivity activity
     * user is moved there from Home page
     */
    void onPersonalBudget()
    {
        view.personalBudget();
    }

    /**
     * When clicked on ShowMoneyBoxesActivity activity
     * user is moved there from Home page
     */
    public void onMoneyBoxes() {
        view.moneyBoxes();
    }

    /**
     * When clicked on GlobalStatisticsActivity activity
     * user is moved there from Home page
     */
    public void onStats(){
        view.stats();
    }
}
