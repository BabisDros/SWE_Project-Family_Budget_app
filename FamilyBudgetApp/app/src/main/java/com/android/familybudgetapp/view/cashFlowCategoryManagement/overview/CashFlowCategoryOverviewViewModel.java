package com.android.familybudgetapp.view.cashFlowCategoryManagement.overview;

import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.memorydao.UserDAOMemory;
import com.android.familybudgetapp.view.base.BaseViewModel;

public class CashFlowCategoryOverviewViewModel extends BaseViewModel<CashFlowCategoryOverviewPresenter>
{
    @Override
    protected CashFlowCategoryOverviewPresenter createPresenter()
    {
        presenter = new CashFlowCategoryOverviewPresenter() ;

        UserDAO userDAO = new UserDAOMemory();
        presenter.setUserDAO(userDAO);

        return presenter;
    }
}
