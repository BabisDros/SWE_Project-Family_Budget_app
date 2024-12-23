package com.android.familybudgetapp.view.HomePage;

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
     * When clicked on FamilyBudgetActivity activity
     * user is moved there from Home page
     */
    void onFamilyBudget()
    {
        view.familyBudget();
    }
}
