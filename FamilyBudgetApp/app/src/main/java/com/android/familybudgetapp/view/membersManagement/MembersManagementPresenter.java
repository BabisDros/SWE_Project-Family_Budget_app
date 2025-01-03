package com.android.familybudgetapp.view.membersManagement;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.dao.FamilyDAO;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.FamPos;
import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.view.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

public class MembersManagementPresenter extends BasePresenter<MembersManagementView>
{
    FamilyDAO familyDAO;
    UserDAO userDAO;

    List<User> members;
    /**
     * Sets the Family DAO.
     *
     * @param familyDAO the {@link FamilyDAO} instance.
     */
    public void setFamilyDAO(FamilyDAO familyDAO)
    {
        this.familyDAO = familyDAO;
    }

    /**
     * Sets the User DAO.
     *
     * @param userDAO the {@link UserDAO} instance.
     */
    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }
    public void deleteMember(User user)
    {
        if (user.getFamilyPosition().equals(FamPos.Protector))
        {
            Family family = user.getFamily();
            for (User member : family.getMembers().values())
            {
                userDAO.delete(member);
            }
            familyDAO.delete(family);
        }
        else
        {
            userDAO.delete(user);
            view.updateMembersRecyclerView(members.indexOf(user));
        }
    }

    public void searchFamilyMembers(long familyId)
    {
        if(familyId!=-1)
        {
            members = new ArrayList<>(familyDAO.findByID(familyId).getMembers().values());
            if(!members.isEmpty())
            {
                view.populateMembersRecyclerView(members);
            }
        }
        else
        {
            view.showErrorMessage("An error occurred", "Try again later");
        }
    }
}
