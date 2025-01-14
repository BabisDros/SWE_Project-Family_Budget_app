package com.android.familybudgetapp.view.moneyBox.addMoneyBox;

public interface AddMoneyBoxView {

    /**
     * Retrieves the trimmed text entered in the "reason" input field.
     *
     * @return The trimmed string value of the text entered in the "reason" EditText view.
     */
    String getReason();

    /**
     * Retrieves the target value entered in the corresponding input field, processes the input,
     * and converts it to an integer value in cent units.
     *
     * @return The target value as an integer in cents. If the input is empty, it defaults to 0.
     */
    Integer getTarget();

    /**
     * Εμφανίζει ένα μήνυμα τύπου alert με
     * τίτλο title και μήνυμα message.
     * @param title Ο τίτλος του μηνύματος
     * @param message Το περιεχόμενο του μηνύματος
     */
    void showErrorMessage(String title, String message);

    /**
     * Inform {@link com.android.familybudgetapp.view.moneyBox.showMoneyBoxes.ShowMoneyBoxesActivity ShowMoneyBoxActivity}
     * that a new MoneyBox was added
     */
    void succesfullyFinish();
}
