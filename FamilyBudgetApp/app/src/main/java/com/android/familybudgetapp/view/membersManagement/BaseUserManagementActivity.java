package com.android.familybudgetapp.view.membersManagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.ViewModel;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.view.membersManagement.overview.MembersOverviewActivity;
import com.android.familybudgetapp.view.membersManagement.registerCreate.RegisterCreateActivity;
import com.android.familybudgetapp.view.base.BaseActivity;

public abstract class BaseUserManagementActivity<V extends ViewModel> extends BaseActivity<V>
{
    protected EditText usernameField;
    protected EditText passwordField;
    protected EditText displayNameField;
    protected EditText familyNameField;
    protected Button btnAction;
    protected Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_member_management);
        setUIReferences();
        setEditTextsFocusListeners();
        setupActionBtn();
        setUpCancelBtn();
    }

    //region $UI setups

    /**
     * Sets focus change listeners for the username, password, and display name fields.
     * When the focus is lost from any of these fields, the proper method is called.
     */
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
     * Sets up the cancel button and its onClick listener..
     */
    protected void setUpCancelBtn()
    {
        btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(v->goToMemberManagementActivity());
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
        btnAction = findViewById(R.id.btn_action);
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
     * Sets up an action button according to {@link RegisterCreateActivity}
     * mode (e.g.,save in edit mode, saveMember in register mode).
     */
    protected abstract void setupActionBtn();

    /**
     * Sets the familyName field with the provided name.
     *
     * @param familyName The name to be displayed.
     */
    public void setFamilyNameField(String familyName)
    {
        familyNameField.setText(familyName);
    }
    //endregion

    //region $Get values from UI elements
    /**
     * Retrieves the username entered by the user.
     *
     * @return A trimmed string of the text in the username field.
     */
    public String getUsername()
    {
        return usernameField.getText().toString().trim();
    }

    /**
     * Retrieves the password entered by the user.
     *
     * @return A trimmed string of the text in the password field.
     */
    public String getPassword()
    {
        return passwordField.getText().toString().trim();
    }

    /**
     * Retrieves the displayName entered by the user.
     *
     * @return A trimmed string of the text in the displayName field.
     */
    public String getDisplayName()
    {
        return displayNameField.getText().toString().trim();
    }

    /**
     * Retrieves the familyName entered by the user.
     *
     * @return A trimmed string of the text in the familyName field.
     */
    public String getFamilyName()
    {
        return familyNameField.getText().toString().trim();
    }
    //endregion

    /**
     * Changes Activity to MembersOverviewActivity
     */
    public void goToMemberManagementActivity()
    {
        Intent intent = new Intent(this, MembersOverviewActivity.class);
        startActivity(intent);
        finish();
    }
}
