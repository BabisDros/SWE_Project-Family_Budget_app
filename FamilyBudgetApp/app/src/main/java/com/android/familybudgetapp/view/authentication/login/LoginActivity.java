package com.android.familybudgetapp.view.authentication.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.ViewModelProvider;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.view.homePage.HomePageActivity;
import com.android.familybudgetapp.view.base.BaseActivity;


public class LoginActivity extends BaseActivity<LoginViewModel> implements LoginView
{
    private EditText usernameField;
    private EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewModel.getPresenter().setView(this);

        setupLoginBtn();

        usernameField = findViewById(R.id.username_field);
        passwordField = findViewById(R.id.password_field);
    }

    @Override
    protected LoginViewModel createViewModel()
    {
        return new ViewModelProvider(this).get(LoginViewModel.class);
    }

    /**
     * Sets up the loginClicked button and its onClick listener.
     */
    private void setupLoginBtn()
    {
        Button actionButton = findViewById(R.id.btn_login);
        actionButton.setOnClickListener(v -> loginClicked());
    }

    /**
     * Calls presenter's login method when .
     */
    private void loginClicked()
    {
        viewModel.getPresenter().login(getUsername(), getPassword());
    }

    //region $Get values from UI elements
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
    //endregion

    //region $Navigation
    @Override
    public void goToHomepage(String famPos)
    {
        Intent intent = new Intent(this, HomePageActivity.class);
        intent.putExtra(HomePageActivity.MODE_EXTRA, famPos);

        startActivity(intent);
        finish();
    }
    //endregion
}
