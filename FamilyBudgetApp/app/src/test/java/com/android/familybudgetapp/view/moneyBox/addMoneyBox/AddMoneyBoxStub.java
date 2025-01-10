package com.android.familybudgetapp.view.moneyBox.addMoneyBox;

public class AddMoneyBoxStub implements AddMoneyBoxView {

    private String reason;
    private int target;
    private AddMoneyBoxPresenter presenter;
    private String errorTitle;
    private String errorMessage;

    public void setPresenter(AddMoneyBoxPresenter presenter) {
        this.presenter = presenter;
    }

    public AddMoneyBoxPresenter getPresenter() {
        return presenter;
    }

    @Override
    public String getReason(){
        return reason;
    }

    @Override
    public Integer getTarget(){
        return target;
    }

    @Override
    public void showErrorMessage(String title, String message)
    {
        errorTitle = title;
        errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorTitle() {
        return errorTitle;
    }

    @Override
    public void succesfullyFinish() {

    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public void resetErrorMessage(){
        errorTitle = "";
        errorMessage = "";
    }
}
