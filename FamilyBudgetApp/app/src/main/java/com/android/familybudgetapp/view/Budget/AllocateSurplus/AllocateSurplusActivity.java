package com.android.familybudgetapp.view.Budget.AllocateSurplus;

import android.os.Bundle;
import android.util.Pair;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.domain.MoneyBox;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.utilities.AmountConversion;
import com.android.familybudgetapp.view.base.BaseActivity;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AllocateSurplusActivity extends BaseActivity<AllocateSurplusViewModel> implements AllocateSurplusView {
    private AllocateSurplusViewModel vm;

    @Override
    protected AllocateSurplusViewModel createViewModel() {
        return new ViewModelProvider(this).get(AllocateSurplusViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allocate_surplus);
        vm = createViewModel();
        vm.getPresenter().setView(this);

        findViewById(R.id.btn_add_to_moneybox).setOnClickListener(v -> addToMoneyBox());
        findViewById(R.id.btn_allocate_done).setOnClickListener(v -> allocateDone());

        displayPreviousMonthName();
        displaySurplusAmount(vm.getPresenter().getSurplusToAllocateAmount());
        populateMoneyBoxOptions();
    }

    public void displayPreviousMonthName() {
        TextView surplusAmountLabel = findViewById(R.id.tv_surplus_amount_label);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);

        String[] monthNames = new DateFormatSymbols().getMonths();
        String previousMonthName = monthNames[calendar.get(Calendar.MONTH)];

        String surplusText = "Surplus Amount for " + previousMonthName + ":";
        surplusAmountLabel.setText(surplusText);
    }

    @Override
    public void displaySurplusAmount(int amount) {
        String euroAmount = AmountConversion.toEuro(amount);
        ((TextView) findViewById(R.id.tv_surplus_amount)).setText(euroAmount);
    }
    public void populateMoneyBoxOptions() {
        ArrayList<Pair<User, MoneyBox>> userMoneyBoxes = vm.getPresenter().getUserMoneyBoxes();
        List<String> moneyBoxOptions = new ArrayList<>();

        for (Pair<User, MoneyBox> pair : userMoneyBoxes) {
            User user = pair.first;
            MoneyBox moneyBox = pair.second;

            String reason = moneyBox.getReason();
            String userName = user.getName();
            String currentAmount = AmountConversion.toEuro(moneyBox.getCurrentAmount());
            String target = AmountConversion.toEuro(moneyBox.getTarget());
            moneyBoxOptions.add(String.format("%s (%s) - %s/%s", reason, userName, currentAmount, target));
        }

        Spinner spinner = findViewById(R.id.spinner_moneybox);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                spinner.getContext(),
                android.R.layout.simple_spinner_item,
                moneyBoxOptions
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    public void addToMoneyBox() {
        Spinner spinnerMoneyBox = findViewById(R.id.spinner_moneybox);
        long selectedOptionIndex = spinnerMoneyBox.getSelectedItemId();
        MoneyBox selectedMoneyBox = vm.getPresenter().getUserMoneyBoxes().get((int) selectedOptionIndex).second;
        String amountInput = ((TextView) findViewById(R.id.allocate_amount)).getText().toString();
        vm.getPresenter().addToMoneyBox(amountInput, selectedMoneyBox);
        ((TextView) findViewById(R.id.allocate_amount)).setText("");
        refreshData();
    }

    public void refreshData() {
        populateMoneyBoxOptions();
        displaySurplusAmount(vm.getPresenter().getSurplusToAllocateAmount());
    }

    public void allocateDone() {
        finish();
    }
}
