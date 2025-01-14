package com.android.familybudgetapp.view.moneyBox.addMoneyBox;

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

    /**
     * Retrieves the trimmed text entered in the "reason" input field.
     *
     * @return The trimmed string value of the text entered in the "reason" EditText view.
     */
    public String getReason(){
        return ((EditText)findViewById(R.id.edit_text_reason)).getText().toString().trim();
    }

    /**
     * Retrieves the target value entered in the corresponding input field, processes the input,
     * and converts it to an integer value in cent units.
     *
     * @return The target value as an integer in cents. If the input is empty, it defaults to 0.
     */
    public Integer getTarget(){
        String input = ((EditText)findViewById(R.id.edit_text_target)).getText().toString();
        Double inDecimal = Double.parseDouble((input.isEmpty()) ? "0": input);
        return (int)(inDecimal * 100);
    }

    /**
     * Inform {@link com.android.familybudgetapp.view.moneyBox.showMoneyBoxes.ShowMoneyBoxesActivity ShowMoneyBoxActivity}
     * that a new MoneyBox was added
     */
    @Override
    public void succesfullyFinish() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        this.finish();
    }
}
