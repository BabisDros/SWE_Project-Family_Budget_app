package com.android.familybudgetapp.view.budget.allocateSurplus;

import android.util.Pair;

import com.android.familybudgetapp.domain.Allowance;
import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.MoneyBox;
import com.android.familybudgetapp.domain.MonthlySurplus;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.utilities.CommonStringValidations;
import com.android.familybudgetapp.view.base.BasePresenter;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;

public class AllocateSurplusPresenter extends BasePresenter<AllocateSurplusView> {
    private Family currentFamily;

    public Family getFamily() {
        return currentFamily;
    }

    public void setFamily(Family family) {
        this.currentFamily = family;
    }

    public void setView(AllocateSurplusView view) {
        super.setView(view);
    }

    public MonthlySurplus getPreviousSurplusObj() {
        YearMonth previousMonth = YearMonth.now().minusMonths(1);
        return getFamily().getMonthlySurpluses().get(previousMonth);
    }

    public int getSurplusToAllocateAmount() {
        return this.getPreviousSurplusObj().getSurplus();
    }

    public void addToMoneyBox(String amountInput, MoneyBox moneyBox){
        if (CommonStringValidations.isAmountInvalid(amountInput)) {
            view.showErrorMessage("Error", "Please enter a valid amount");
            return;
        }
        double euroValue = Double.parseDouble(amountInput);
        int centsValue = (int) (euroValue * 100);

        if (centsValue > getSurplusToAllocateAmount()) {
            view.showErrorMessage("Error", "The amount exceeds the available surplus");
            return;
        }
        if ((moneyBox.getCurrentAmount() + centsValue) > moneyBox.getTarget()) {
            view.showErrorMessage("Error", "Adding this amount will exceed the target amount for the MoneyBox");
            return;
        }
        allocateSurplus(centsValue, moneyBox);
    }

    public void allocateSurplus(int amount, MoneyBox moneyBox) {

        MonthlySurplus surplusToAllocate = getPreviousSurplusObj();

        // Create new allowance and add to moneybox
        Allowance allowance= new Allowance(amount, LocalDateTime.now());
        moneyBox.addMoney(allowance);
        surplusToAllocate.setSurplus(surplusToAllocate.getSurplus() - amount);
        surplusToAllocate.addAllowanceMoneyBoxPair(allowance, moneyBox.getReason());
    }

    public ArrayList<Pair<User, MoneyBox>> getUserMoneyBoxes() {
        ArrayList<Pair<User, MoneyBox>> userMoneyBoxes = new ArrayList<>();
        for (User user: getFamily().getMembers().values()) {
            for (MoneyBox moneyBox: user.getMoneyBoxes().values()) {
                userMoneyBoxes.add(new Pair<>(user, moneyBox));
            }
        }
        return userMoneyBoxes;
    }
}
