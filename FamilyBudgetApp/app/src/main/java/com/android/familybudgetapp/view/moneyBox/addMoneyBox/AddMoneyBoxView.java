package com.android.familybudgetapp.view.moneyBox.addMoneyBox;

public interface AddMoneyBoxView {

    String getReason();

    Integer getTarget();

    void showErrorMessage(String title, String message);

    void succesfullyFinish();
}
