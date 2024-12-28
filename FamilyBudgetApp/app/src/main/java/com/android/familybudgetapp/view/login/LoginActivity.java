package com.android.familybudgetapp.view.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.android.familybudgetapp.R;


public class LoginActivity extends AppCompatActivity implements LoginView
{
    private LoginViewModel viewModel;
    private EditText usernameField;
    private EditText passwordField;
    private Button btnAction;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        LoginViewModel viewModel= new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.getPresenter().setView(this);

        setActionButton();

        usernameField=findViewById(R.id.username_field);
        passwordField=findViewById(R.id.password_field);
        btnAction =findViewById(R.id.btn_action);

        btnAction.setOnClickListener(v -> login());
    }

    private  void setActionButton()
    {
        String mode = getIntent().getStringExtra("MODE");

        Button actionButton = findViewById(R.id.btn_action);

        if ("LOGIN".equals(mode))
        {
            actionButton.setText("Login");
            actionButton.setOnClickListener(v -> login());
        }
        else if ("REGISTER".equals(mode))
        {
            actionButton.setText("Register");
            actionButton.setOnClickListener(v -> register());
        }
    }
    private void login()
    {
        String username=usernameField.getText().toString();
        String password=passwordField.getText().toString();

        viewModel.getPresenter().login(username, password);
    }

    private void register()
    {
        String username=usernameField.getText().toString();
        String password=passwordField.getText().toString();

        viewModel.getPresenter().register(username, password);
    }
}
