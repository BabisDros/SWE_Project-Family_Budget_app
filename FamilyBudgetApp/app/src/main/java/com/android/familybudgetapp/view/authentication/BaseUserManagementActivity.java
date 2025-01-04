package com.android.familybudgetapp.view.authentication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.lifecycle.ViewModel;
import com.android.familybudgetapp.R;
import com.android.familybudgetapp.view.authentication.register.RegisterActivity;
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

    //region $UI setups
    protected void setEditTextsFocusListeners()
    {
        usernameField.setOnFocusChangeListener((v, hasFocus) ->
        {
            if (!hasFocus)
            {
                usernameEditTxtUnfocused();
            }
        });

        passwordField.setOnFocusChangeListener((v, hasFocus) ->
        {
            if (!hasFocus)
            {
                passwordEditTxtUnfocused();
            }
        });

        displayNameField.setOnFocusChangeListener((v, hasFocus) ->
        {
            if (!hasFocus)
            {
                displayNameEditTxtUnfocused();
            }
        });
    }

    /**
     * Sets UI elements references
     */
    protected void setUIReferences()
    {
        usernameField = findViewById(R.id.username_field);
        passwordField = findViewById(R.id.password_field);
        displayNameField = findViewById(R.id.displayName_field);
        familyNameField = findViewById(R.id.familyName_field);
        btnAction = findViewById(R.id.btn_register);
    }
    //endregion

    //region $Calls to presenter
    /**
     * Called when username {@link EditText} loses focus.
     */
    protected abstract void usernameEditTxtUnfocused();

    /**
     * Called when password {@link EditText} loses focus.
     */
    protected abstract void passwordEditTxtUnfocused();

    /**
     * Called when display name {@link EditText} loses focus.
     */
    protected abstract void displayNameEditTxtUnfocused();

    /**
     * Sets up an action button according to {@link RegisterActivity}
     * mode (e.g.,save in edit mode, addMember in register mode).
     */
    protected abstract void setupActionBtn();
    //endregion

    //region $Get values from UI elements
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
    //endregion
}
