package com.android.familybudgetapp.view.homePage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class HomePagePresenterTest {

    private HomePagePresenter presenter;
    private HomePageView view;

    @Before
    public void setUp(){
        view = new HomePageStub();
        presenter = new HomePagePresenter(view);
    }

    /**
     * Test whether intent for {@link com.android.familybudgetapp.view.budget.showBudget.BudgetActivity BudgetActivity}
     * is successfully called from view;
     */
    @Test
    public void onPersonalBudget() {
        assertFalse(((HomePageStub) view).success);
        presenter.onPersonalBudget();
        assertTrue(((HomePageStub) view).success);
    }

    /**
     * Test whether intent for {@link com.android.familybudgetapp.view.moneyBox.showMoneyBoxes.ShowMoneyBoxesActivity ShowMoneyBoxesActivity}
     * is successfully called from view;
     */
    @Test
    public void onMoneyBoxes() {
        assertFalse(((HomePageStub) view).success);
        presenter.onMoneyBoxes();
        assertTrue(((HomePageStub) view).success);
    }

    /**
     * Test whether intent for {@link com.android.familybudgetapp.view.globalStatistics.GlobalStatisticsActivity GlobalStatisticsActivity} is successfully called from view;
     */
    @Test
    public void onStats() {
        assertFalse(((HomePageStub) view).success);
        presenter.onStats();
        assertTrue(((HomePageStub) view).success);
    }
}