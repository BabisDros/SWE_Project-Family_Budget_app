package com.android.familybudgetapp.view.membersManagement;

import com.android.familybudgetapp.dao.FamilyDAO;
import com.android.familybudgetapp.dao.Initializer;
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
    User userToDelete;

    //cache membersList at every session, because MAP does not guaranty order
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
        this.userToDelete = user;
        if (user.getFamilyPosition().equals(FamPos.Protector))
        {
            view.showDeleteAccountMessage("Caution", "Deleting Protector User:"
                    + user.getName() + " will delete the account and wipe all the data." +
                    "\n\nDo you want to delete it?");
        }
        else
        {
            userDAO.delete(user);
            view.updateMembersRecyclerView(members.indexOf(user));
        }
    }

    public void searchFamilyMembers()
    {
        User currentUser = userDAO.findByID(Initializer.currentUserID);
        Family currentFamily = familyDAO.findByID(currentUser.getFamily().getID());

        if (currentFamily != null)
        {
            members = new ArrayList<>(currentFamily.getMembers().values());
            if (!members.isEmpty())
            {
                view.populateMembersRecyclerView(members);
            }
        }
        else
        {
            view.showErrorMessage("An error occurred", "Try again later");
        }
    }

    public void deleteAccount()
    {
        if (userToDelete != null)
        {
            Family family = userToDelete.getFamily();
            for (User member : family.getMembers().values())
            {
                userDAO.delete(member);
            }
            familyDAO.delete(family);

            view.exitApp();
        }
    }

    public void navigateToRegister()
    {
        view.goToRegisterActivity();
    }

    public void navigateToHomepage()
    {
        view.goToHomepageActivity();
    }
}
