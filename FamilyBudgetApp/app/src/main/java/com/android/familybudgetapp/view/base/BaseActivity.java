package com.android.familybudgetapp.view.base;
import com.android.familybudgetapp.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import android.os.Bundle;
import android.app.AlertDialog;


public abstract class BaseActivity<V extends ViewModel> extends AppCompatActivity implements BaseView
{
    protected V viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        viewModel =  createViewModel();
    }

    @Override
    public void showErrorMessage(String title, String message)
    {
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .create()
                .show();
    }

    /**
     * Creates and returns the viewModel associated with this activity.
     *
     * @return The viewModel.
     */
    protected abstract V createViewModel();
}
