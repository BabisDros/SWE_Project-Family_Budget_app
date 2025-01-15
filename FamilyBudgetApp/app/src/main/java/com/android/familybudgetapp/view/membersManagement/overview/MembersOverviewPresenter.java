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
    public static final String CAUTION_TITLE = "Caution";
    public static final String CAUTION_MESSAGE = "Deleting Protector User: %s will delete the account, " +
            "all member accounts and wipe all the data." + "\n\nDo you want to delete it?";
    public static final String VERIFICATION_TITLE = "Delete Verification";
    public static final String VERIFICATION_MESSAGE = "User: %s\n\nDo you want to delete it?";
    private FamilyDAO familyDAO;
    private UserDAO userDAO;
    private User userToDelete;
    private Family currentFamily;

    //cache membersList at every session, because MAP does not guaranty order
    private List<User> members;

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

    /**
     * Shows a verification message before deleting a user.
     * If the user is a family protector, a caution message is shown,
     * else, a verification message is displayed.
     *
     * @param user The user to delete.
     */
    public void showVerification(User user)
    {
        this.userToDelete = user;
        if (user.getFamilyPosition().equals(FamPos.Protector))
        {
            view.showDeleteAccountMessage
                    (
                            CAUTION_TITLE,
                            String.format(CAUTION_MESSAGE, user.getUsername())
                    );
        }
        else
        {
            view.showDeleteAccountMessage
                    (
                            VERIFICATION_TITLE,
                            String.format(VERIFICATION_MESSAGE, user.getUsername())
                    );
        }
    }

    /**
     * Retrieves the family members from the current user's family and populates the UI with the members list.
     */
    public void searchFamilyMembers()
    {
        User currentUser = userDAO.findByID(Initializer.currentUserID);
        currentFamily = currentUser.getFamily();

        members = new ArrayList<>(currentFamily.getMembers().values());
        view.populateMembersRecyclerView(members);
    }

    /**
     * Deletes a user based on their family position.
     * If the user is the family protector, the account is deleted.
     * Else, the user is removed as a family member.
     */
    public void deleteBasedOnFamilyPosition()
    {
        if (userToDelete.getFamilyPosition() == FamPos.Protector)
        {
            deleteAccount();
        }

        else deleteMember();
    }

    /**
     * Deletes a member and saves the updated family.
     * Updates the UI without the removed user.
     */
    private void deleteMember()
    {
        userDAO.delete(userToDelete);
        familyDAO.save(currentFamily);
        view.updateMembersRecyclerView(members.indexOf(userToDelete));
    }

    /**
     * Deletes the account of Protector and all members of their family from the DAO.
     * Deletes the family from the DAO.
     * Exits the application.
     */
    private void deleteAccount()
    {
        Family family = userToDelete.getFamily();
        for (User member : family.getMembers().values())
        {
            userDAO.delete(member);
        }
        familyDAO.delete(family);

        view.exitApp();
    }

    /**
     * Called when the btnAddMember FloatingActionButton is clicked.
     */
    public void navigateToRegister()
    {
        view.goToRegisterActivity();
    }
    /**
     * Called when the Homepage button is clicked.
     */
    public void navigateToHomepage()
    {
        view.goToHomepageActivity();
    }
}
