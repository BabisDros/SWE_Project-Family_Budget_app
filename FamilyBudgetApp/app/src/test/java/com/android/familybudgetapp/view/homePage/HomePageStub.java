package com.android.familybudgetapp.view.homePage;

public class HomePageStub implements HomePageView {

    public boolean success;

    public HomePageStub(){
        success = false;
    }

    @Override
    public void personalBudget() {
        success = true;
    }

    @Override
    public void moneyBoxes() {
        success = true;
    }

    @Override
    public void stats() {
        success = true;
    }

    @Override
    public void goToMemberManagement() {
        success = true;
    }

    @Override
    public void goToCashFlowCategories() {
        success = true;
    }
}
