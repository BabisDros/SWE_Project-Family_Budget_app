package com.android.familybudgetapp.view.cashFlowCategoryManagement.overview;

import com.android.familybudgetapp.domain.CashFlowCategory;
import com.android.familybudgetapp.view.ViewStub;

import java.util.ArrayList;
import java.util.List;

public class CashFlowCategoryOverviewViewStub extends ViewStub implements CashFlowCategoryOverviewView
{
    private List<CashFlowCategory> cashFlowCategories = new ArrayList<>();
    private int indexToDelete;

    private int cashFlowActivityCounter;
    private int homepageActivityCounter;

    private String title;
    private String msg;

    @Override
    public void goToHomepageActivity()
    {
        homepageActivityCounter++;
    }

    @Override
    public void populateCategoriesRecyclerView(List<CashFlowCategory> categories)
    {
        this.cashFlowCategories = categories;
    }

    @Override
    public void updateCategoriesRecyclerView(int indexToDelete)
    {
        this.indexToDelete = indexToDelete;
    }

    @Override
    public void goToCreateCashFlowCategoryActivity()
    {
        cashFlowActivityCounter++;
    }

    @Override
    public void showDeleteCategory(String title, String message)
    {
        this.title = title;
        this.msg = message;
    }

    public int getCashFlowActivityCounter()
    {
        return cashFlowActivityCounter;
    }

    public int getHomepageActivityCounter()
    {
        return homepageActivityCounter;
    }

    public int getIndexToDelete()
    {
        return indexToDelete;
    }

    public String getTitle()
    {
        return title;
    }

    public String getMsg()
    {
        return msg;
    }

    public List<CashFlowCategory> getCashFlowCategories()
    {
        return cashFlowCategories;
    }
}
