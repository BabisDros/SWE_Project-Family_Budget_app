package com.android.familybudgetapp.view.cashFlowCategoryManagement.overview;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.domain.CashFlowCategory;
import com.android.familybudgetapp.domain.Expense;
import com.android.familybudgetapp.view.GenericRecyclerViewAdapter;
import com.android.familybudgetapp.view.homePage.HomePageActivity;
import com.android.familybudgetapp.view.base.BaseActivity;
import com.android.familybudgetapp.view.cashFlowCategoryManagement.create.CashFlowCategoryCreateActivity;
import com.android.familybudgetapp.view.cashFlowCategoryManagement.edit.CashFlowCategoryEditActivity;
import com.android.familybudgetapp.view.viewHolders.ViewHolderSingleTextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Locale;

public class CashFlowCategoryOverviewActivity extends BaseActivity<CashFlowCategoryOverviewViewModel>
        implements CashFlowCategoryOverviewView
{
    public static final String CASHFLOW_CATEGORY_NAME_EXTRA = "CashFlow Category Name";

    private GenericRecyclerViewAdapter<CashFlowCategory, ViewHolderSingleTextView> recyclerViewAdapter;
    private AlertDialog.Builder optionsDialog;
    private Locale currentLocale;
    private AlertDialog.Builder deleteCategoryDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_with_add_option);

        TextView listTitle = findViewById(R.id.list_title);
        listTitle.setText(R.string.cashFlow_categories);
        currentLocale = getResources().getConfiguration().getLocales().get(0);

        viewModel.getPresenter().setView(this);
        optionsDialog = new AlertDialog.Builder(this);
        viewModel.getPresenter().searchCashFlowCategories();

        setupHomepageBtn();
        setupFloatBtnAdd();
        setupDeleteAccountDialog();
    }

    @Override
    protected CashFlowCategoryOverviewViewModel createViewModel()
    {
        return new ViewModelProvider(this).get(CashFlowCategoryOverviewViewModel.class);
    }

    /**
     * Sets up the btnHomepage button and its onClick listener.
     */
    private void setupHomepageBtn()
    {
        Button btnHomepage = findViewById(R.id.btn_homepage);
        btnHomepage.setOnClickListener(v -> homepageClicked());
    }

    /**
     * Sets up the btnAddMember FloatingActionButton and its onClick listener.
     */
    private void setupFloatBtnAdd()
    {
        FloatingActionButton btnAddMember = findViewById(R.id.float_btn_add);
        btnAddMember.setOnClickListener(v -> addCategoryClicked());
    }

    /**
     * Sets up the dialog for deleting a category
     * and the appropriate listeners and names to NegativeButton and PositiveButton.
     */
    private void setupDeleteAccountDialog()
    {
        deleteCategoryDialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes, (dialog, which) -> deleteDialogYesClicked());
    }

    /**
     * Listener to the btnAddMember FloatingActionButton click event of Delete Dialog.
     * Calls the presenter to navigate to the CreateCashFlowCategory.
     */
    private void addCategoryClicked()
    {
        viewModel.getPresenter().navigateToCreateCashFlowCategory();
    }

    /**
     * Listener to the PositiveButton click event of Delete Dialog.
     * Calls the presenter to handle the delete.
     */
    private void deleteDialogYesClicked()
    {
        viewModel.getPresenter().deleteCategory();
    }

    /**
     * Listener to the NegativeButton click event of Category Dialog.
     * Calls the presenter to navigate to the HomepageActivity.
     */
    private void homepageClicked()
    {
        viewModel.getPresenter().navigateToHomepage();
    }

    @Override
    public void populateCategoriesRecyclerView(List<CashFlowCategory> categories)
    {
        RecyclerView recyclerViewCategories = findViewById(R.id.recyclerView_List);
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewAdapter = new GenericRecyclerViewAdapter<>
                (
                        categories,
                        (cashFlowCategory, viewHolder) ->
                        {
                            viewHolder.txtItem.setText(createItemName(cashFlowCategory));
                            viewHolder.txtItem.setOnClickListener(v -> selectItem(cashFlowCategory));
                        },
                        (view) -> new ViewHolderSingleTextView(view, R.id.txt_item),
                        R.layout.list_item_single_textview
                );

        recyclerViewCategories.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void updateCategoriesRecyclerView(int indexToDelete)
    {
        recyclerViewAdapter.updateList(indexToDelete);
    }

    /**
     * Displays a dialog with options to edit or delete the selected CashFlowCategory.
     * Sets the listeners to the options.
     * @param cashFlowCategory The CashFlowCategory that was selected.
     */
    private void selectItem(CashFlowCategory cashFlowCategory)
    {
        optionsDialog.setTitle(cashFlowCategory.getName())
                .setNegativeButton("Cancel", null)
                .setItems(new String[]{"Edit", "Delete"}, (dialog, which) ->
                {
                    if (which == 0)//Edit
                    {
                        goToCashFlowCategoryEdit(cashFlowCategory.getName());
                    }
                    else//Delete. Show Verification
                    {
                        viewModel.getPresenter().showVerification(cashFlowCategory);
                    }
                })
                .show();
    }

    /**
     * Creates a formatted name for the CashFlowCategory.     *
     *
     * @param category The CashFlowCategory object .
     * @return A formatted string with the type (Expense, Income), name and limit (if it is Expense).
     */
    private String createItemName(CashFlowCategory category)
    {
        Object currentClass = category.getClass();
        if (currentClass == Expense.class)
        {
            return String.format(currentLocale, "Expense: %s. Limit: %d ", category.getName(), ((Expense) category).getLimit());
        }
        else
            return String.format("Income: %s ", category.getName());
    }

    /**
     * Changes the Activity to CashFlowCategoryEditActivity.
     */
    private void goToCashFlowCategoryEdit(String cashFlowCategoryName)
    {
        Intent intent = new Intent(this, CashFlowCategoryEditActivity.class);
        intent.putExtra(CASHFLOW_CATEGORY_NAME_EXTRA, cashFlowCategoryName);
        startActivity(intent);
        finish();
    }

    @Override
    public void goToHomepageActivity()
    {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void goToCreateCashFlowCategoryActivity()
    {
        Intent intent = new Intent(this, CashFlowCategoryCreateActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showDeleteCategory(String title, String message)
    {
        deleteCategoryDialog.setTitle(title);
        deleteCategoryDialog.setMessage(message);
        deleteCategoryDialog.show();
    }
}
