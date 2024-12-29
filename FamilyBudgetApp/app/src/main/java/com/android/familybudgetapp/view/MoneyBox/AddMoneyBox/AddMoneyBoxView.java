package com.android.familybudgetapp.view.MoneyBox.AddMoneyBox;

public interface AddMoneyBoxView {

    String getReason();

    Integer getTarget();

    void showErrorMessage(String title, String message);

    void succesfullyFinish();
}
