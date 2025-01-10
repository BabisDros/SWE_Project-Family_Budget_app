package com.android.familybudgetapp.view.membersManagement.overview;

import com.android.familybudgetapp.dao.FamilyDAO;
import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.FamPos;
import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.view.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

public class MembersOverviewPresenter extends BasePresenter<MembersOverviewView>
{
    FamilyDAO familyDAO;
    UserDAO userDAO;
    User userToDelete;
    Family currentFamily;

    //cache membersList at every session, because MAP does not guaranty order
    List<User> members;

    //region $DAO setup

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
    //endregion

    public void showVerification(User user)
    {
        this.userToDelete = user;
        if (user.getFamilyPosition().equals(FamPos.Protector))
        {
            view.showDeleteAccountMessage("Caution", "Deleting Protector User:"
                    + user.getName() + " will delete the account, all member accounts and wipe all the data." +
                    "\n\nDo you want to delete it?");
        }
        else
        {
            view.showDeleteAccountMessage("Delete Verification", "User: "
                    + user.getName() +
                    "\n\nDo you want to delete it?");
        }
    }

    public void searchFamilyMembers()
    {
        User currentUser = userDAO.findByID(Initializer.currentUserID);
        currentFamily = currentUser.getFamily();

        members = new ArrayList<>(currentFamily.getMembers().values());
        view.populateMembersRecyclerView(members);
    }

    void delete()
    {
        if (userToDelete.getFamilyPosition() == FamPos.Protector)
        {
            deleteAccount();
        }

        else deleteMember();
    }

    public void deleteMember()
    {
        userDAO.delete(userToDelete);
        familyDAO.save(currentFamily);
        view.updateMembersRecyclerView(members.indexOf(userToDelete));

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
