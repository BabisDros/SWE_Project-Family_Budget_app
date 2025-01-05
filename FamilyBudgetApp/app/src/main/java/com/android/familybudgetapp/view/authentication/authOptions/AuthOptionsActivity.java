package com.android.familybudgetapp.view.authentication.authOptions;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.memorydao.MemoryInitializer;
import com.android.familybudgetapp.view.authentication.registerCreate.RegisterCreateActivity;
import com.android.familybudgetapp.view.base.BaseActivity;
import com.android.familybudgetapp.view.authentication.login.LoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.lifecycle.ViewModelProvider;

public class AuthOptionsActivity extends BaseActivity<AuthOptionsViewModel> implements AuthOptionsView
{
    Button loginButton;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_options);
        viewModel.getPresenter().setView(this);

        loginButton = findViewById(R.id.btn_loginOption);
        registerButton = findViewById(R.id.btn_registerOption);

        loginButton.setOnClickListener(v -> loginClicked());
        registerButton.setOnClickListener(v -> registerClicked());

        if (savedInstanceState == null)
        {
            new MemoryInitializer().prepareData();
        }
    }

    @Override
    protected AuthOptionsViewModel createViewModel()
    {
        return new ViewModelProvider(this).get(AuthOptionsViewModel.class);
    }

    //region $Local methods that call presenter
    private void loginClicked()
    {
        viewModel.getPresenter().navigateToLogin();
    }

    private void registerClicked()
    {
        viewModel.getPresenter().navigateToRegister();
    }
    //endregion

    //region $Navigation
    @Override
    public void goToLogin()
    {
        goToActivity(LoginActivity.class);
    }

    @Override
    public void goToRegister()
    {
        goToActivity(RegisterCreateActivity.class);
    }

    /**
     * Helper method that starts the provided activity.
     *
     * @param cls The class of the activity.
     */
    private void goToActivity(Class<?> cls)
    {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
    //endregion
}
