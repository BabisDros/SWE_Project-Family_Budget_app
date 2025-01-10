package com.android.familybudgetapp.view.membersManagement.overview;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.view.GenericRecyclerViewAdapter;
import com.android.familybudgetapp.view.homePage.HomePageActivity;
import com.android.familybudgetapp.view.membersManagement.edit.EditUserActivity;
import com.android.familybudgetapp.view.membersManagement.registerCreate.RegisterCreateActivity;
import com.android.familybudgetapp.view.base.BaseActivity;
import com.android.familybudgetapp.view.viewHolders.ViewHolderSingleTextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MembersOverviewActivity extends BaseActivity<MembersOverviewViewModel>
        implements MembersOverviewView
{
    public static final String USER_ID_EXTRA = "user_id";
    GenericRecyclerViewAdapter<User, ViewHolderSingleTextView> recyclerViewAdapter;
    AlertDialog.Builder deleteAccountDialog;
    AlertDialog.Builder optionsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_with_add_option);
        viewModel.getPresenter().setView(this);
        TextView listTitle = findViewById(R.id.list_title);
        listTitle.setText(R.string.family_members);

        setupHomepageBtn();

        viewModel.getPresenter().searchFamilyMembers();
        setupDeleteAccountDialog();

        optionsDialog = new AlertDialog.Builder(this);

        setupFloatBtnAddMember();
    }


    @Override
    protected MembersOverviewViewModel createViewModel()
    {
        return new ViewModelProvider(this).get(MembersOverviewViewModel.class);
    }

    //region $UI elements setup
    private void setupHomepageBtn()
    {
        Button btnHomepage = findViewById(R.id.btn_homepage);
        btnHomepage.setOnClickListener(v -> homepageClicked());
    }

    private void setupFloatBtnAddMember()
    {
        FloatingActionButton btnAddMember = findViewById(R.id.float_btn_add);
        btnAddMember.setOnClickListener(v -> addMemberClicked());
    }

    public void populateMembersRecyclerView(List<User> members)
    {
        RecyclerView recyclerViewMembers = findViewById(R.id.recyclerView_List);
        recyclerViewMembers.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewAdapter = new GenericRecyclerViewAdapter<>
                (
                        members,
                        (user, viewHolder) ->
                        {
                            viewHolder.txtItem.setText(user.getUsername());
                            viewHolder.txtItem.setOnClickListener(v -> selectItem(user));
                        },
                        (view) -> new ViewHolderSingleTextView(view, R.id.txt_item),
                        R.layout.list_item_single_textview
                );

        recyclerViewMembers.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void updateMembersRecyclerView(int removedIndex)
    {
        recyclerViewAdapter.updateList(removedIndex);
    }

    private void setupDeleteAccountDialog()
    {
        deleteAccountDialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes, (dialog, which) -> deleteDialogYesClicked());
    }
    //endregion

    //region $Local listeners that call the presenter
    private void addMemberClicked()
    {
        viewModel.getPresenter().navigateToRegister();
    }

    private void homepageClicked()
    {
        viewModel.getPresenter().navigateToHomepage();
    }

    private void deleteDialogYesClicked()
    {
        viewModel.getPresenter().delete();
    }
    //endregion

    @Override
    public void showDeleteAccountMessage(String title, String message)
    {
        deleteAccountDialog.setTitle(title);
        deleteAccountDialog.setMessage(message);
        deleteAccountDialog.show();
    }

    @Override
    public void exitApp()
    {
        finishAffinity();
    }


    public void selectItem(User user)
    {
        optionsDialog.setTitle(user.getName())
                .setNegativeButton("Cancel", null)
                .setItems(new String[]{"Edit", "Delete"}, (dialog, which) ->
                {
                    if (which == 0)//edit
                    {
                        Intent intent = new Intent(this, EditUserActivity.class);
                        intent.putExtra(USER_ID_EXTRA, user.getID());
                        startActivity(intent);
                        finish();
                    }
                    else//showVerification
                    {
                        viewModel.getPresenter().showVerification(user);
                    }
                })
                .show();
    }


    //region $Navigation to other Activities
    @Override
    public void goToHomepageActivity()
    {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void goToRegisterActivity()
    {
        Intent intent = new Intent(this, RegisterCreateActivity.class);
        intent.putExtra(RegisterCreateActivity.MODE_EXTRA, RegisterCreateActivity.ADD_MEMBER_EXTRA);
        startActivity(intent);
        finish();
    }

    //endregion
}
