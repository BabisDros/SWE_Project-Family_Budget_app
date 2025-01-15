package com.android.familybudgetapp.view.membersManagement.overview;

import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.view.base.BaseView;

import java.util.List;

public interface MembersOverviewView extends BaseView
{

    /**
     * Populates the RecyclerView with the list of members.
     *
     * @param members The list of members to display.
     */
    void populateMembersRecyclerView(List<User> members);

    /**
     * Updates the RecyclerView by deleting the member at the specified index.
     *
     * @param removedIndex The index of the member to be deleted.
     */
    void updateMembersRecyclerView(int removedIndex);

    /**
     * Shows a confirmation message for deletion of a member.
     *
     * @param title   The title of the deletion message.
     * @param message The message of the deletion message.
     */
    void showDeleteAccountMessage(String title, String message);


    void exitApp();

    /**
     * Changes Activity to Homepage Activity.
     */
    void goToHomepageActivity();

    /**
     * Changes Activity to RegisterCreateActivity.
     */
    void goToRegisterActivity();
}
