package com.android.familybudgetapp.view.homePage;

import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.memorydao.UserDAOMemory;
import com.android.familybudgetapp.utilities.AmountConversion;

public class HomePagePresenter {
    private HomePageView view;
    private UserDAO userDAO;

    /**
     * Initialize Presenter
     * @param view Instance of view
     */
    public HomePagePresenter(HomePageView view)
    {
        this.view = view;
        userDAO = new UserDAOMemory();
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

    /**
     * Retrieves the savings of the current {@link com.android.familybudgetapp.domain.Family Family} in euros.
     *
     * @return A string representing the savings of the user's family, converted to euros.
     */
    public String getSavings() {
        return AmountConversion.toEuro(userDAO.findByID(Initializer.currentUserID).getFamily().getSavings());
    }
}
