package com.android.familybudgetapp.view.authentication.edit;

import static com.android.familybudgetapp.view.membersManagement.MembersManagementActivity.USER_ID_EXTRA;
import android.content.Intent;
import android.os.Bundle;
import com.android.familybudgetapp.R;
import com.android.familybudgetapp.view.authentication.BaseUserManagementActivity;

public class EditUserActivity extends BaseUserManagementActivity<EditUserViewModel> implements EditUserView
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        viewModel.getPresenter().setView(this);

        Intent intent = getIntent();
        long userId = intent.getLongExtra(USER_ID_EXTRA, -1);
        viewModel.getPresenter().setUserData(userId);
    }

    @Override
    protected EditUserViewModel createViewModel()
    {
        return new EditUserViewModel();
    }

    @Override
    protected void validateUsername()
    {
        viewModel.getPresenter().validateUsername(getUsername());
    }

    @Override
    protected void validatePassword()
    {
        viewModel.getPresenter().validatePassword(getPassword());
    }

    @Override
    protected void validateDisplayName()
    {
        viewModel.getPresenter().validateDisplayName(getDisplayName());
    }

    @Override
    protected void setupActionBtn()
    {
        btnAction.setText(R.string.save);
        btnAction.setOnClickListener(v -> save());
    }

    private void save()
    {
        viewModel.getPresenter().save();
    }

    @Override
    public void setUsernameField(String username)
    {
        usernameField.setText(username);
    }
    @Override
    public void setPasswordField(String password)
    {
        passwordField.setText(password);
    }
    @Override
    public void setDisplayNameField(String displayName)
    {
        displayNameField.setText(displayName);
    }
    @Override
    public void setfamilyNameField(String familyName)
    {
        familyNameField.setText(familyName);
    }
}
