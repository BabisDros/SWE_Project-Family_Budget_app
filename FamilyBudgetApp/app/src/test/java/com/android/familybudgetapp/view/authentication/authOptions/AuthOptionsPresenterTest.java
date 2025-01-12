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

    @Test
    public void clearView()
    {
        presenter.clearView();
        assertThrows(NullPointerException.class, () ->
        {
            presenter.navigateToLogin();
        });
    }
    @Test
    public void goToNavigation()
    {
        presenter.navigateToLogin();
        assertEquals(1, viewStub.getLoginNavigationCounter());
    }

    @Test
    public void goToRegister()
    {
        presenter.navigateToRegister();
        assertEquals(1, viewStub.getRegisterNavigationCounter());
    }
}
