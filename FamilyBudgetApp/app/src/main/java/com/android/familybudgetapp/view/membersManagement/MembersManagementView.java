package com.android.familybudgetapp.view.membersManagement;

import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.view.base.BaseView;

import java.util.List;

public interface MembersManagementView extends BaseView
{

    void populateMembersRecyclerView(List<User> members);

    void updateMembersRecyclerView(int removedIndex);
    void showDeleteAccountMessage(String title, String message);

    void exitApp();

    void goToHomepageActivity();

    void goToRegisterActivity();
}
