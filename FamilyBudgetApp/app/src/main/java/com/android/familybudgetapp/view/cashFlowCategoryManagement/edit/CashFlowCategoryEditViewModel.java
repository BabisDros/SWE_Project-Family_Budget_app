package com.android.familybudgetapp.view.cashFlowCategoryManagement.edit;

import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.memorydao.UserDAOMemory;
import com.android.familybudgetapp.view.base.BaseViewModel;

public class CashFlowCategoryEditViewModel extends BaseViewModel<CashFlowCategoryEditPresenter>
{
    @Override
    protected CashFlowCategoryEditPresenter createPresenter()
    {
        presenter = new CashFlowCategoryEditPresenter();

        UserDAO userDAO = new UserDAOMemory();
        presenter.setUserDAO(userDAO);

        return presenter;
    }
}
