package com.android.familybudgetapp.view.authentication.login;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import android.util.Log;

import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.memorydao.MemoryInitializer;
import com.android.familybudgetapp.memorydao.UserDAOMemory;

public class LoginPresenterTest
{
    private LoginPresenter presenter;
    private LoginViewStub viewStub;
    private UserDAO userDAO;

    @Before
    @Test
    public void setUp()
    {
        viewStub = new LoginViewStub();
        presenter = new LoginPresenter();
        userDAO = new UserDAOMemory();
        new MemoryInitializer().prepareData();

        presenter.setView(viewStub);
        presenter.setUserDAO(userDAO);
    }

    @Test
    public void correctSetOfUserDao()
    {
        assertEquals(presenter.userDAO, userDAO);
    }

    @Test
    public void loginInvalidUsername()
    {
        presenter.login("invalid", "1234");
        assertEquals(LoginPresenter.WRONG_CREDENTIALS_TITLE, viewStub.getErrorTitle());
        assertEquals(LoginPresenter.WRONG_CREDENTIALS_MSG, viewStub.getErrorMsg());
    }

    @Test
    public void loginValidUsernameNullPassword()
    {
        presenter.login("Test", null);
        assertEquals(LoginPresenter.WRONG_CREDENTIALS_TITLE, viewStub.getErrorTitle());
        assertEquals(LoginPresenter.WRONG_CREDENTIALS_MSG, viewStub.getErrorMsg());
    }

    @Test
    public void loginValidUsernameInvalidPassword()
    {
        presenter.login("Test", "1234");
        assertEquals(LoginPresenter.WRONG_CREDENTIALS_TITLE, viewStub.getErrorTitle());
        assertEquals(LoginPresenter.WRONG_CREDENTIALS_MSG, viewStub.getErrorMsg());
    }

    @Test
    public void loginValidUsernameValidPassword()
    {
        String username = "Test";
        presenter.login(username, "passwordTest");

        assertEquals(userDAO.findByUsername(username).getID(), Initializer.currentUserID);
        assertEquals(1, viewStub.getHomepageCounter());
    }
}