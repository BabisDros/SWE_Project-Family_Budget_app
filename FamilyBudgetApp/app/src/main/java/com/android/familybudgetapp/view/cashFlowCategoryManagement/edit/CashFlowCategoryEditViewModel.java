package com.android.familybudgetapp.view.cashFlowCategoryManagement.edit;

import com.android.familybudgetapp.dao.FamilyDAO;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.memorydao.FamilyDAOMemory;
import com.android.familybudgetapp.memorydao.UserDAOMemory;
import com.android.familybudgetapp.view.base.BaseViewModel;

public class CashFlowCategoryEditViewModel extends BaseViewModel<CashFlowCategoryEditPresenter>
{
    @Override
    protected CashFlowCategoryEditPresenter createPresenter()
    {
        presenter = new CashFlowCategoryEditPresenter();

        UserDAO userDAO = new UserDAOMemory();
        FamilyDAO familyDAO = new FamilyDAOMemory();

        presenter.setUserDAO(userDAO);
        presenter.setFamilyDAO(familyDAO);

        return presenter;
    }
}
