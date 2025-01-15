package com.android.familybudgetapp.view.membersManagement.edit;

import static com.android.familybudgetapp.view.membersManagement.overview.MembersOverviewActivity.USER_ID_EXTRA;

import android.content.Intent;
import android.os.Bundle;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.view.membersManagement.BaseUserManagementActivity;


public class EditUserActivity extends BaseUserManagementActivity<EditUserViewModel> implements EditUserView
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        viewModel.getPresenter().setView(this);
        setPasswordHint();
        if (savedInstanceState == null)
        {
            Intent intent = getIntent();
            long userId = intent.getLongExtra(USER_ID_EXTRA, -1);
            viewModel.getPresenter().setUserData(userId);
        }
    }

    @Override
    protected EditUserViewModel createViewModel()
    {
        return new EditUserViewModel();
    }

    //region $UI Setups
    @Override
    protected void setupActionBtn()
    {
        btnAction.setText(R.string.save);
        btnAction.setOnClickListener(v -> saveClicked());
    }

    @Override
    public void setUsernameField(String username)
    {
        usernameField.setText(username);
    }


    /**
     * Sets the hint for the password field.
     * Updates the hint text to inform the user that leaving the
     * password field empty will keep the current password unchanged.
     */
    private void setPasswordHint()
    {
        passwordField.setHint("Password: Leave empty to keep current");
        passwordField.setEms(16);
    }

    @Override
    public void setDisplayNameField(String displayName)
    {
        displayNameField.setText(displayName);
    }

    @Override
    public void disableFamilyField()
    {
        familyNameField.setEnabled(false);
    }
    //endregion

    //region Calls to presenter
    @Override
    protected void usernameEditTxtUnfocused()
    {
        viewModel.getPresenter().validateUsername(getUsername());
    }

    @Override
    protected void passwordEditTxtUnfocused()
    {
        viewModel.getPresenter().validatePassword(getPassword());
    }

    @Override
    protected void displayNameEditTxtUnfocused()
    {
        viewModel.getPresenter().validateDisplayName(getDisplayName());
    }

    /**
     * Listener to the action Button click event.
     * Calls presenter's save method with username, password, displayName and familyName as arguments.
     */
    private void saveClicked()
    {
        viewModel.getPresenter().save(getUsername(), getPassword(), getDisplayName(), getFamilyName());
    }
    //endregion
}
