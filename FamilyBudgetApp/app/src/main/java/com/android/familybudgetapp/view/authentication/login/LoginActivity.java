package com.android.familybudgetapp.view.authentication.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.lifecycle.ViewModelProvider;
import com.android.familybudgetapp.R;
import com.android.familybudgetapp.view.HomePage.HomePageActivity;
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
     * Sets up the login button and its onClick listener.
     */
    private void setupLoginBtn()
    {
        Button actionButton = findViewById(R.id.btn_register);
        actionButton.setOnClickListener(v -> login());
    }

    /**
     * Calls presenter's login method.
     */
    private void login()
    {
        viewModel.getPresenter().login(getUsername(), getPassword());
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
    public void goToHomepage()
    {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
        finish();
    }
}
