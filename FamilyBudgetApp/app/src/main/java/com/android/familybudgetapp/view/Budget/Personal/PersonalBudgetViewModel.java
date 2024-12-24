package com.android.familybudgetapp.view.Budget.Personal;

import androidx.lifecycle.ViewModel;

import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.memorydao.UserDAOMemory;

public class PersonalBudgetViewModel extends ViewModel {
    private PersonalBudgetPresenter presenter;

    public PersonalBudgetViewModel()
    {
        presenter = new PersonalBudgetPresenter();
        UserDAO userDAO = new UserDAOMemory();
        presenter.setUserDao(userDAO);
        presenter.setCurrentUser(userDAO.find(Initializer.currentUserID));
    }

    public PersonalBudgetPresenter getPresenter() {
        return presenter;
    }

    protected void onCleared()
    {
        super.onCleared();
    }
}
