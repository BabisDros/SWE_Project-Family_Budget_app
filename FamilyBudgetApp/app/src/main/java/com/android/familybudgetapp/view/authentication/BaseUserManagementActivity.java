package com.android.familybudgetapp.view.authentication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.lifecycle.ViewModel;
import com.android.familybudgetapp.R;
import com.android.familybudgetapp.view.base.BaseActivity;

public abstract class BaseUserManagementActivity<V extends ViewModel> extends BaseActivity<V>
{
    protected EditText usernameField;
    protected EditText passwordField;
    protected EditText displayNameField;
    protected EditText familyNameField;
    protected Button btnAction;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        setUIReferences();
        setEditTextsFocusListeners();
        setupActionBtn();
    }

    protected void setEditTextsFocusListeners()
    {
        usernameField.setOnFocusChangeListener((v, hasFocus) ->
        {
            if (!hasFocus)
            {
                validateUsername();
            }
        });

        passwordField.setOnFocusChangeListener((v, hasFocus) ->
        {
            if (!hasFocus)
            {
                validatePassword();
            }
        });

        displayNameField.setOnFocusChangeListener((v, hasFocus) ->
        {
            if (!hasFocus)
            {
                validateDisplayName();
            }
        });
    }

    protected abstract void validateUsername();
    protected abstract void validatePassword();
    protected abstract void validateDisplayName();
    protected abstract void setupActionBtn();

    protected void setUIReferences()
    {
        usernameField = findViewById(R.id.username_field);
        passwordField = findViewById(R.id.password_field);
        displayNameField = findViewById(R.id.displayName_field);
        familyNameField = findViewById(R.id.familyName_field);
        btnAction = findViewById(R.id.btn_register);
    }

    public String getUsername()
    {
        return usernameField.getText().toString().trim();
    }

    public String getPassword()
    {
        return passwordField.getText().toString().trim();
    }

    public String getDisplayName()
    {
        return displayNameField.getText().toString().trim();
    }

    public String getFamilyName()
    {
        return familyNameField.getText().toString().trim();
    }
}
