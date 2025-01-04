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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        viewModel.getPresenter().setView(this);
        setupAddMemberDialog();
    }

    @Override
    protected RegisterViewModel createViewModel()
    {
        return new ViewModelProvider(this).get(RegisterViewModel.class);
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
        btnAction.setOnClickListener(v -> register());
    }

    private void register()
    {
        viewModel.getPresenter().register(getUsername(), getPassword(), getDisplayName(), getFamilyName());
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
                .setNegativeButton(R.string.no, (dialog, which) -> viewModel.getPresenter().onNoClicked())
                .setPositiveButton(R.string.yes, (dialog, which) -> onYesClicked());
    }

    private void onYesClicked()
    {
        familyNameField.setEnabled(false);
        btnAction.setText(R.string.add_member);
        clearFields();
    }

    private void clearFields()
    {
        usernameField.setText("");
        passwordField.setText("");
        displayNameField.setText("");
    }

    @Override
    public void goToMemberManagement(long familyId)
    {
        Intent intent = new Intent(this, MembersManagementActivity.class);
        intent.putExtra(MembersManagementActivity.FAMILY_ID_EXTRA, familyId);
        startActivity(intent);
        finish();
    }
}
