package com.android.familybudgetapp.view.authentication.authOptions;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.memorydao.MemoryInitializer;
import com.android.familybudgetapp.view.authentication.register.RegisterActivity;
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

    private static boolean initialized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_options);
        viewModel.getPresenter().setView(this);

        loginButton = findViewById(R.id.btn_loginOption);
        registerButton = findViewById(R.id.btn_registerOption);

        loginButton.setOnClickListener(v -> viewModel.getPresenter().onLoginButtonClicked());
        registerButton.setOnClickListener(v ->viewModel.getPresenter().onRegisterButtonClicked());

        if (!initialized)
        {
            new MemoryInitializer().prepareData();
            initialized = true;
        }
    }

    @Override
    protected AuthOptionsViewModel createViewModel()
    {
        return new ViewModelProvider(this).get(AuthOptionsViewModel.class);
    }

    @Override
    public void goToLogin()
    {
        goToActivity(LoginActivity.class);
    }

    @Override
    public void goToRegister()
    {
        goToActivity(RegisterActivity.class);
    }

    /**
     * Helper method that finishes this activity and starts the provided activity.
     * @param cls The class of the activity.
     */
    private void goToActivity(Class<?> cls)
    {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        finish();
    }
}
