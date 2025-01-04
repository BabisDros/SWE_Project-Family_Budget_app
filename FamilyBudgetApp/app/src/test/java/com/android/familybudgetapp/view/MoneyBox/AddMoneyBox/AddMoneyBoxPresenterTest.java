package com.android.familybudgetapp.view.MoneyBox.AddMoneyBox;

import static org.junit.Assert.*;

import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.memorydao.MemoryInitializer;
import com.android.familybudgetapp.memorydao.UserDAOMemory;

import org.junit.Before;
import org.junit.Test;

public class AddMoneyBoxPresenterTest {

    Initializer dataHelper;
    User user;
    AddMoneyBoxStub stub;

    AddMoneyBoxPresenter presenter;

    @Before
    public void setUp(){
        dataHelper = new MemoryInitializer();
        dataHelper.prepareData();
        user = dataHelper.getUserDAO().findByID(Initializer.currentUserID);
        stub = new AddMoneyBoxStub();
        presenter = new AddMoneyBoxPresenter(stub, new UserDAOMemory());
    }

    @Test
    public void validateSave(){
        stub.setReason("1");
        stub.setTarget(100);
        presenter.validateSave();
        assertEquals("Error", stub.getErrorTitle());
        assertEquals("Reason must consist of at least 3 characters" +
                " and can only be alphanumerical", stub.getErrorMessage());
        stub.resetErrorMessage();

        stub.setReason("Hat");
        stub.setTarget(-150);
        presenter.validateSave();
        assertEquals("Error", stub.getErrorTitle());
        assertEquals("Target should be a positive number", stub.getErrorMessage());
        stub.resetErrorMessage();

        stub.setReason("Hat");
        stub.setTarget(2500);
        presenter.validateSave();
        assertEquals("", stub.getErrorTitle());
        assertEquals("", stub.getErrorMessage());
        stub.resetErrorMessage();

        stub.setReason("Hat");
        stub.setTarget(6500);
        presenter.validateSave();
        assertEquals("Error", stub.getErrorTitle());
        assertEquals("You already have a moneyBox for that reason", stub.getErrorMessage());
        stub.resetErrorMessage();
    }
}
