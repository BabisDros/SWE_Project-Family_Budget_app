package com.android.familybudgetapp.view.MoneyBox.AddMoneyBox;

import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.MoneyBox;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.utilities.CommonStringValidations;

public class AddMoneyBoxPresenter {
    private AddMoneyBoxView view;
    private User user;

    public AddMoneyBoxPresenter(AddMoneyBoxView view, UserDAO users)
    {
        this.view = view;
        user = users.findByID(Initializer.currentUserID);
    }

    /**
     * Check user input before making a new moneybox
     * Terminate the activity afterwards
     */
    public void validateSave() {
        String reason = view.getReason();
        Integer target = view.getTarget();

        if (!CommonStringValidations.isAlphanumericWithSpaces(reason))
            view.showErrorMessage("Error", "Reason must consist of at least 3 characters" +
                    " and can only be alphanumerical");
        else if (!MoneyBox.validateTarget(target))
            view.showErrorMessage("Error", "Target should be a positive number");
        else if (!user.validateMoneyBox(new MoneyBox(reason, target)))
        {
            view.showErrorMessage("Error", "You already have a moneyBox for that reason");
        }
        else
        {
            user.addMoneyBox(new MoneyBox(reason, target));
            view.succesfullyFinish();
        }


    }
}
