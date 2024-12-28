package com.android.familybudgetapp.view.authOptions;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.view.login.LoginActivity;


public class AuthOptionsActivity extends AppCompatActivity
{
    Button loginButton;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_options);
        AuthOptionsViewModel viewModel= new ViewModelProvider(this).get(AuthOptionsViewModel.class);
        viewModel.getPresenter().setView(this);

        loginButton = findViewById(R.id.btn_login);
        registerButton = findViewById(R.id.btn_register);

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("MODE", "LOGIN");
            startActivity(intent);
        });

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("MODE", "REGISTER");
            startActivity(intent);
        });
    }
}
