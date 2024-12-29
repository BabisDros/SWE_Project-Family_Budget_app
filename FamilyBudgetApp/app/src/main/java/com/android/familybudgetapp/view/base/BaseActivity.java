package com.android.familybudgetapp.view.base;
import com.android.familybudgetapp.R;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;


public abstract class BaseActivity<V extends BaseViewModel<? extends BasePresenter<? extends BaseView>>> extends AppCompatActivity implements BaseView
{
    protected V viewModel;

    /**
     * Creates and initializes the activity.
     *
     * @param savedInstanceState The activity's saved instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        viewModel =  createViewModel();
    }

    /**
     * Displays an error message.
     *
     * @param title The title of the error.
     * @param message The message of the error.
     */
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
