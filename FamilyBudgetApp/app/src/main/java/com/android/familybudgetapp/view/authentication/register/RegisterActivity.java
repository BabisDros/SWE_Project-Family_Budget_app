package com.android.familybudgetapp.view.authentication.register;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.view.authentication.BaseUserManagementActivity;
import com.android.familybudgetapp.view.membersManagement.MembersManagementActivity;

public class RegisterActivity extends BaseUserManagementActivity<RegisterViewModel> implements RegisterView
{
    AlertDialog.Builder addMemberDialog;
    public static final String MODE_EXTRA = "mode";
    public static final String ADD_MEMBER_EXTRA = "add_member";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String mode = intent.getStringExtra(MODE_EXTRA);

        if (mode != null && mode.equals(ADD_MEMBER_EXTRA))
        {
            setupToAddMemberMode();
        }
        viewModel.getPresenter().setView(this);
        setupAddMemberDialog();
    }

    @Override
    protected RegisterViewModel createViewModel()
    {
        return new ViewModelProvider(this).get(RegisterViewModel.class);
    }

    //region $UI elements setup
    @Override
    protected void setupActionBtn()
    {
        btnAction.setOnClickListener(v -> registerClicked());
    }

    @Override
    public void showAddMemberMessage(String title, String message)
    {
        addMemberDialog.setTitle(title);
        addMemberDialog.setMessage(message);
        addMemberDialog.show();
    }

    private void setupAddMemberDialog()
    {
        addMemberDialog = new AlertDialog.Builder(RegisterActivity.this)
                .setCancelable(true)
                .setNegativeButton(R.string.no, (dialog, which) -> addMemberDialogNoClicked())
                .setPositiveButton(R.string.yes, (dialog, which) -> addMemberDialogYesClicked());
    }

    private void clearFields()
    {
        usernameField.setText("");
        passwordField.setText("");
        displayNameField.setText("");
    }

    @Override
    public void setupToAddMemberMode()
    {
        familyNameField.setEnabled(false);
        btnAction.setText(R.string.add_member);
        clearFields();
    }
    //region

    //region $Local listeners that call presenter
    private void addMemberDialogNoClicked()
    {
        viewModel.getPresenter().goToMemberManagement();
    }

    private void addMemberDialogYesClicked()
    {
        viewModel.getPresenter().enableAddMemberMode();
    }

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

    private void registerClicked()
    {
        viewModel.getPresenter().register(getUsername(), getPassword(), getDisplayName(), getFamilyName());
    }
    //endregion

    //region $Navigation to other activities
    @Override
    public void goToMemberManagement()
    {
        Intent intent = new Intent(this, MembersManagementActivity.class);
        startActivity(intent);
        finish();
    }
    //endregion
}
