package com.android.familybudgetapp.view.membersManagement.registerCreate;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.view.membersManagement.BaseUserManagementActivity;

public class RegisterCreateActivity extends BaseUserManagementActivity<RegisterCreateViewModel> implements RegisterCreateView
{
    public static final String MODE_EXTRA = "mode";
    public static final String ADD_MEMBER_EXTRA = "add_member";
    private AlertDialog.Builder addMemberDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        viewModel.getPresenter().setView(this);

        Intent intent = getIntent();
        String mode = intent.getStringExtra(MODE_EXTRA);

        if (mode != null && mode.equals(ADD_MEMBER_EXTRA))
        {
            viewModel.getPresenter().enableAddMemberMode();
        }
        else
        {
            btnCancel.setVisibility(View.GONE);
        }

        setupAddMemberDialog();
    }

    @Override
    protected RegisterCreateViewModel createViewModel()
    {
        return new ViewModelProvider(this).get(RegisterCreateViewModel.class);
    }

    //region $UI elements setup
    @Override
    protected void setupActionBtn()
    {
        btnAction.setText(R.string.register);
        btnAction.setOnClickListener(v -> registerClicked());
    }

    @Override
    public void showAddMemberMessage(String title, String message)
    {
        addMemberDialog.setTitle(title);
        addMemberDialog.setMessage(message);
        addMemberDialog.show();
    }

    @Override
    public void goToMemberManagement()
    {
        super.goToMemberManagementActivity();
    }

    /**
     * Caches the dialog for adding a new member and sets its onClick listener.

     */
    private void setupAddMemberDialog()
    {
        addMemberDialog = new AlertDialog.Builder(RegisterCreateActivity.this)
                .setCancelable(true)
                .setNegativeButton(R.string.no, (dialog, which) -> addMemberDialogNoClicked())
                .setPositiveButton(R.string.yes, (dialog, which) -> addMemberDialogYesClicked());
    }

    @Override
    public void clearFields()
    {
        usernameField.setText("");
        passwordField.setText("");
        displayNameField.setText("");
    }

    @Override
    public void setupUIToAddMemberMode()
    {
        btnCancel.setVisibility(View.VISIBLE);
        familyNameField.setEnabled(false);
        btnAction.setText(R.string.add_member);
        btnAction.setOnClickListener(v -> saveMemberClicked());
    }

    /**
     * Listener to the action Button click event.
     * Calls presenter's saveMember with the values of
     * username, password, displayName, and familyName as arguments.
     */
    private void saveMemberClicked()
    {
        viewModel.getPresenter().saveMember(getUsername(), getPassword(), getDisplayName(), getFamilyName());
    }
    //endregion

    //region $Local listeners that call presenter
    /**
     * Listener to the NegativeButton of AddMemberDialog click event.
     */
    private void addMemberDialogNoClicked()
    {
        viewModel.getPresenter().goToMemberManagement();
    }

    /**
     * Listener to the PositiveButton of AddMemberDialog click event.
     */
    private void addMemberDialogYesClicked()
    {
        viewModel.getPresenter().enableAddMemberMode();
    }

    /**
     * Listener to the btnAction Button.
     * Calls presenter's register method with the values of
     * username, password, displayName, and familyName as arguments.
     */
    private void registerClicked()
    {
        viewModel.getPresenter().register(getUsername(), getPassword(), getDisplayName(), getFamilyName());
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
    //endregion
}
