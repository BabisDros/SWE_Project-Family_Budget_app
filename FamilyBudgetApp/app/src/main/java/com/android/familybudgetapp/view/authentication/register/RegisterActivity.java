package com.android.familybudgetapp.view.authentication.register;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.lifecycle.ViewModelProvider;
import com.android.familybudgetapp.R;
import com.android.familybudgetapp.view.HomePage.HomePageActivity;
import com.android.familybudgetapp.view.base.BaseActivity;


public class RegisterActivity extends BaseActivity<RegisterViewModel> implements RegisterView
{
    private EditText usernameField;
    private EditText passwordField;
    private EditText displayNameField;
    private EditText familyNameField;
    private Button btnRegister;

    AlertDialog.Builder addMemberDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        viewModel.getPresenter().setView(this);

        setupRegisterBtn();
        setEditTextsReferences();
        setEditTextsFocusListeners();
        setupAddMemberDialog();
    }

    public void setEditTextsFocusListeners()
    {
        usernameField.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus)
            {
                viewModel.getPresenter().validateUsername(getUsername());
            }
        });

        passwordField.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus)
            {
                viewModel.getPresenter().validatePassword(getPassword());
            }
        });

        displayNameField.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus)
            {
                viewModel.getPresenter().validateDisplayName(getDisplayName());
            }
        });
    }

    private void setEditTextsReferences()
    {
        usernameField = findViewById(R.id.username_field);
        passwordField = findViewById(R.id.password_field);
        displayNameField= findViewById(R.id.displayName_field);
        familyNameField= findViewById(R.id.familyName_field);
    }

    @Override
    protected RegisterViewModel createViewModel()
    {
        return new ViewModelProvider(this).get(RegisterViewModel.class);
    }

    private void setupRegisterBtn()
    {
        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(v -> register());
    }

    private void register()
    {
        viewModel.getPresenter().register(getUsername(), getPassword(),getDisplayName(),getFamilyName());
    }

    @Override
    public String getUsername()
    {
        return usernameField.getText().toString().trim();
    }

    @Override
    public String getPassword()
    {
        return passwordField.getText().toString().trim();
    }

    @Override
    public String getDisplayName()
    {
        return displayNameField.getText().toString().trim();
    }

    @Override
    public String getFamilyName()
    {
        return familyNameField.getText().toString().trim();
    }

    @Override
    public void addMemberMessage(String title, String message)
    {
        addMemberDialog.setTitle(title);
        addMemberDialog.setMessage(message);
        addMemberDialog.show();
    }


    private void setupAddMemberDialog()
    {
        addMemberDialog = new AlertDialog.Builder(RegisterActivity.this)
                .setCancelable(true)
                .setNegativeButton(R.string.no,(dialog, which)->onNoClicked())
                .setPositiveButton(R.string.yes,(dialog, which)-> onYesClicked());
    }

    private void onNoClicked()
    {
        goToHomepage();
    }

    private void onYesClicked()
    {
        familyNameField.setEnabled(false);
        btnRegister.setText("add member");
        clearFields();
    }

    private void clearFields()
    {
        usernameField.setText("");
        passwordField.setText("");;
        displayNameField.setText("");;
    }
    @Override
    public void goToHomepage()
    {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
        finish();
    }
}
