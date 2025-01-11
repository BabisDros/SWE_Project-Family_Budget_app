package com.android.familybudgetapp.view.budget.allocateSurplus;

import androidx.lifecycle.ViewModel;

import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.memorydao.UserDAOMemory;


import java.util.Objects;

public class AllocateSurplusViewModel extends ViewModel {
    private AllocateSurplusPresenter presenter;
    public AllocateSurplusViewModel()
    {
        presenter = new AllocateSurplusPresenter();
        UserDAO userDAO = new UserDAOMemory();
        presenter.setUserDao(userDAO);
        presenter.setFamily(userDAO.findByID(Initializer.currentUserID).getFamily());
    }

    public AllocateSurplusPresenter getPresenter() {
        return presenter;
    }

}
