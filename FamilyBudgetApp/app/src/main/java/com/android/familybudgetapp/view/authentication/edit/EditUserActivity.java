package com.android.familybudgetapp.view.authentication.edit;

import static com.android.familybudgetapp.view.membersManagement.MembersManagementActivity.USER_ID_EXTRA;
import android.content.Intent;
import android.os.Bundle;
import com.android.familybudgetapp.R;
import com.android.familybudgetapp.view.authentication.BaseUserManagementActivity;
import com.android.familybudgetapp.view.membersManagement.MembersManagementActivity;

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

    //region $UI Setups
    @Override
    protected void setupActionBtn()
    {
        btnAction.setText(R.string.save);
        btnAction.setOnClickListener(v ->  saveClicked());
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
    public void setFamilyNameField(String familyName)
    {
        familyNameField.setText(familyName);
    }
    //endregion

    @Override
    public void disableFamilyField()
    {
        familyNameField.setEnabled(false);
    }

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

    private void saveClicked()
    {
        viewModel.getPresenter().save(getUsername(),getPassword(),getUsername(),getFamilyName());
    }
    //endregion

    //region $Navigation
    @Override
    public void goToMemberManagementActivity()
    {
        Intent intent = new Intent(this, MembersManagementActivity.class);
        startActivity(intent);
        finish();
    }
    //endregion
}
