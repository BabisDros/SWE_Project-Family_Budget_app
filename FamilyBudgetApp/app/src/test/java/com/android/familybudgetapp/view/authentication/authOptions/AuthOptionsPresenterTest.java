package com.android.familybudgetapp.view.authentication.authOptions;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AuthOptionsPresenterTest
{
    private AuthOptionsPresenter presenter;
    private AuthOptionsViewStub viewStub;

    @Before
    @Test
    public void setUp()
    {
        viewStub = new AuthOptionsViewStub();
        presenter = new AuthOptionsPresenter();
        presenter.setView(viewStub);
    }

    /**
     * Tests if the navigateToLogin method in the AuthOptionsPresenter class
     * throws a NullPointerException after the view has been cleared.
     */
    @Test
    public void clearView()
    {
        presenter.clearView();
        assertThrows(NullPointerException.class, () ->
        {
            presenter.navigateToLogin();
        });
    }

    /**
     * Tests if the navigateToLogin method in the AuthOptionsPresenter class
     * correctly calls the navigateToLogin in the view stub by counting the numbers of calls.
     */
    @Test
    public void goToNavigation()
    {
        presenter.navigateToLogin();
        assertEquals(1, viewStub.getLoginNavigationCounter());
    }

    /**
     * Tests if the navigateToRegister method in the AuthOptionsPresenter class
     * correctly calls the navigateToRegister in the view stub by counting the numbers of calls.
     */
    @Test
    public void goToRegister()
    {
        presenter.navigateToRegister();
        assertEquals(1, viewStub.getRegisterNavigationCounter());
    }
}
