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

/**
 * Presenter for the AllocateSurplusActivity.
 * This class handles the logic for allocating the previous month's surplus to various money boxes.
 * It interacts with the view to display error messages
 * and updates the necessary monthlySurplus and MoneyBox objects accordingly.
 */
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

    /**
     * Retrieves the {@link MonthlySurplus} object for the previous month.
     * @return the {@link MonthlySurplus} for the previous month.
     */
    public MonthlySurplus getPreviousSurplusObj() {
        YearMonth previousMonth = YearMonth.now().minusMonths(1);
        return getFamily().getMonthlySurpluses().get(previousMonth);
    }

    /**
     * Gets the total surplus amount available for allocation in cents.
     * @return an integer representing the surplus amount in cents.
     */
    public int getSurplusToAllocateAmount() {
        return this.getPreviousSurplusObj().getSurplus();
    }

    /**
     * Adds a specified amount to a given MoneyBox after validating the input and surplus conditions.
     * @param amountInput the amount to add as a string (in euros and cents).
     * @param moneyBox the {@link MoneyBox} to which the amount is added.
     */
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

    /**
     * Allocates a specified amount of surplus to a given MoneyBox.
     * Updates the MoneyBox and the MonthlySurplus accordingly.
     * @param amount the amount to allocate in cents.
     * @param moneyBox the {@link MoneyBox} to which the surplus is allocated.
     */
    public void allocateSurplus(int amount, MoneyBox moneyBox) {

        MonthlySurplus surplusToAllocate = getPreviousSurplusObj();

        // Create new allowance and add to moneybox
        Allowance allowance= new Allowance(amount, LocalDateTime.now());
        moneyBox.addMoney(allowance);
        surplusToAllocate.setSurplus(surplusToAllocate.getSurplus() - amount);
        surplusToAllocate.addAllowanceMoneyBoxPair(allowance, moneyBox.getReason());
    }

    /**
     * Retrieves a list of all MoneyBoxes associated with users in the current family.
     * Each pair contains a {@link User} and their corresponding {@link MoneyBox}.
     * @return an {@link ArrayList} of {@link Pair} objects, where each pair contains a user and a MoneyBox.
     */
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
