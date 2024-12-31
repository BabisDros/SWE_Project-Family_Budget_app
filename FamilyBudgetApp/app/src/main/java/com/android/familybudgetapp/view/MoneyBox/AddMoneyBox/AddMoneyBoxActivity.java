package com.android.familybudgetapp.view.MoneyBox.AddMoneyBox;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.memorydao.UserDAOMemory;

public class AddMoneyBoxActivity extends AppCompatActivity implements AddMoneyBoxView {
    private AddMoneyBoxPresenter presenter;

    protected void onCreate(Bundle savedInstanceSate)
    {
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.activity_add_moneybox);
        presenter = new AddMoneyBoxPresenter(this, new UserDAOMemory());
        findViewById(R.id.btn_back).setOnClickListener(v -> goBack());
        findViewById(R.id.btn_save).setOnClickListener(v -> save());

    }

    /**
     * Εμφανίζει ένα μήνυμα τύπου alert με
     * τίτλο title και μήνυμα message.
     * @param title Ο τίτλος του μηνύματος
     * @param message Το περιεχόμενο του μηνύματος
     */
    public void showErrorMessage(String title, String message)
    {
        new AlertDialog.Builder(AddMoneyBoxActivity.this)
                .setCancelable(true)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null).create().show();
    }

    private void goBack() {
        this.finish();
    }

    private void save()
    {
        presenter.validateSave();
    }

    protected void onPause()
    {
        super.onPause();
    }

    public String getReason(){
        return ((EditText)findViewById(R.id.edit_text_reason)).getText().toString().trim();
    }

    public Integer getTarget(){
        Double inDecimal = Double.parseDouble(((EditText)findViewById(R.id.edit_text_target)).getText().toString());
        return (int)(inDecimal * 100);
    }

    /**
     * Inform {@link com.android.familybudgetapp.view.MoneyBox.ShowMoneyBoxes.ShowMoneyBoxesActivity ShowMoneyBoxActivity}
     * that a new MoneyBox was added
     */
    @Override
    public void succesfullyFinish() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        this.finish();
    }
}
