package com.android.familybudgetapp.view.membersManagement.overview;

import com.android.familybudgetapp.dao.FamilyDAO;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.memorydao.FamilyDAOMemory;
import com.android.familybudgetapp.memorydao.UserDAOMemory;
import com.android.familybudgetapp.view.base.BaseViewModel;

public class MembersOverviewViewModel extends BaseViewModel<MembersOverviewPresenter>
{
    @Override
    protected MembersOverviewPresenter createPresenter()
    {
        presenter = new MembersOverviewPresenter();
        UserDAO userDAO = new UserDAOMemory();
        FamilyDAO familyDAO = new FamilyDAOMemory();
        presenter.setUserDAO(userDAO);
        presenter.setFamilyDAO(familyDAO);

        return presenter;
    }
}