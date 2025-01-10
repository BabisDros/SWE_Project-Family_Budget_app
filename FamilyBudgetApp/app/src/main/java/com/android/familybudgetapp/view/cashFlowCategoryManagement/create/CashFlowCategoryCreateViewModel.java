package com.android.familybudgetapp.view.cashFlowCategoryManagement.create;


import com.android.familybudgetapp.dao.FamilyDAO;
import com.android.familybudgetapp.dao.UserDAO;

import com.android.familybudgetapp.memorydao.FamilyDAOMemory;
import com.android.familybudgetapp.memorydao.UserDAOMemory;
import com.android.familybudgetapp.view.base.BaseViewModel;

public class CashFlowCategoryCreateViewModel extends BaseViewModel<CashFlowCategoryCreatePresenter>
{
    @Override
    protected CashFlowCategoryCreatePresenter createPresenter()
    {
        presenter = new CashFlowCategoryCreatePresenter();

        UserDAO userDAO = new UserDAOMemory();
        FamilyDAO familyDAO = new FamilyDAOMemory();

        presenter.setUserDAO(userDAO);
        presenter.setFamilyDAO(familyDAO);

        return presenter;
    }
}
