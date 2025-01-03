package com.android.familybudgetapp.view.membersManagement;

import com.android.familybudgetapp.dao.FamilyDAO;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.memorydao.FamilyDAOMemory;
import com.android.familybudgetapp.memorydao.UserDAOMemory;
import com.android.familybudgetapp.view.base.BaseViewModel;

public class MembersManagementViewModel extends BaseViewModel<MembersManagementPresenter>
{
    @Override
    protected MembersManagementPresenter createPresenter()
    {
        presenter = new MembersManagementPresenter();
        UserDAO userDAO = new UserDAOMemory();
        FamilyDAO familyDAO = new FamilyDAOMemory();
        presenter.setUserDAO(userDAO);
        presenter.setFamilyDAO(familyDAO);

        return presenter;
    }
}
