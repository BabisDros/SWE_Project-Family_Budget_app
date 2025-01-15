package com.android.familybudgetapp.view.moneyBox.showMoneyBoxes;

import com.android.familybudgetapp.utilities.Quadruples;

import java.util.List;

public class ShowMoneyBoxesStub implements ShowMoneyBoxesView {
    private ShowMoneyBoxesPresenter presenter;
    private List<Quadruples<String, String, Integer, Integer>> dataList;

    public void setPresenter(ShowMoneyBoxesPresenter presenter) {
        this.presenter = presenter;
    }

    public ShowMoneyBoxesPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void loadData() {
        dataList = presenter.getMoneyBoxes();
    }

    public List<Quadruples<String, String, Integer, Integer>> getData()
    {
        return dataList;
    }
}
