package com.android.familybudgetapp.view.cashFlowCategoryManagement.overview;

import com.android.familybudgetapp.domain.CashFlowCategory;
import com.android.familybudgetapp.view.base.BaseView;

import java.util.List;

public interface CashFlowCategoryOverviewView extends BaseView
{
    void goToHomepageActivity();

    void populateCategoriesRecyclerView(List<CashFlowCategory> categories);

    void updateCategoriesRecyclerView(int i);

    void goToCreateCashFlowCategoryActivity();
}
