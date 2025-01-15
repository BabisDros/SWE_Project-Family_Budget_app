package com.android.familybudgetapp.view.budget.allocateSurplus;

import androidx.lifecycle.ViewModel;

import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.memorydao.UserDAOMemory;


import java.util.Objects;

public class AllocateSurplusViewModel extends ViewModel {
    public AllocateSurplusViewModel()
    {
        presenter = new AllocateSurplusPresenter();
        UserDAO userDAO = new UserDAOMemory();
        presenter.setFamily(userDAO.findByID(Initializer.currentUserID).getFamily());
    }
    private AllocateSurplusPresenter presenter;
    public AllocateSurplusPresenter getPresenter() {
        return presenter;
    }
}
