package com.android.familybudgetapp.view.cashFlowCategoryManagement.overview;

import com.android.familybudgetapp.domain.CashFlowCategory;
import com.android.familybudgetapp.view.base.BaseView;

import java.util.List;

public interface CashFlowCategoryOverviewView extends BaseView
{
    /**
     * Changes Activity to Homepage.
     */
    void goToHomepageActivity();

    /**
     * Populates the RecyclerView with the list of CashFlowCategory.
     *
     * @param categories The list of CashFlowCategory to display.
     */
    void populateCategoriesRecyclerView(List<CashFlowCategory> categories);

    /**
     * Updates the RecyclerView by deleting the category at the specified index.
     *
     * @param indexToDelete The index of the category to be deleted.
     */
    void updateCategoriesRecyclerView(int indexToDelete);

    /**
     * Changes Activity to CreateCashFlowCategory.
     */
    void goToCreateCashFlowCategoryActivity();

    /**
     * Shows a confirmation message for deletion of a cash flow category.
     *
     * @param title   The title of the deletion message.
     * @param message The message of the deletion message.
     */
    void showDeleteCategory(String title, String message);
}
