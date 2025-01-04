package com.android.familybudgetapp.view.membersManagement;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.view.HomePage.HomePageActivity;
import com.android.familybudgetapp.view.authentication.edit.EditUserActivity;
import com.android.familybudgetapp.view.authentication.register.RegisterActivity;
import com.android.familybudgetapp.view.base.BaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MembersManagementActivity extends BaseActivity<MembersManagementViewModel>
        implements MembersManagementView, MemberRecyclerViewAdapter.MemberSelectionListener
{
    public static final String USER_ID_EXTRA = "user_id";
    MemberRecyclerViewAdapter recyclerViewAdapter;
    AlertDialog.Builder deleteAccountDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_member_management);
        viewModel.getPresenter().setView(this);

        setupHomepageBtn();

        viewModel.getPresenter().searchFamilyMembers();
        setupDeleteAccountDialog();
        setupFloatBtnAddMember();
    }

    private void setupHomepageBtn()
    {
        Button btnHomepage = findViewById(R.id.btn_homepage);
        btnHomepage.setOnClickListener(v-> goToHomepage());
    }

    private void setupFloatBtnAddMember()
    {
        FloatingActionButton btnAddMember = findViewById(R.id.float_btn_addMember);
        btnAddMember.setOnClickListener(v-> goToAddMember());
    }

    @Override
    public void populateMembersRecyclerView(List<User> members)
    {
        RecyclerView recyclerViewMembers = findViewById(R.id.recyclerView_members);
        recyclerViewMembers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new MemberRecyclerViewAdapter(members, this);
        recyclerViewMembers.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void updateMembersRecyclerView(int removedIndex)
    {
        recyclerViewAdapter.updateMembers(removedIndex);
    }

    @Override
    protected MembersManagementViewModel createViewModel()
    {
        return new ViewModelProvider(this).get(MembersManagementViewModel.class);
    }


    private void setupDeleteAccountDialog()
    {
        deleteAccountDialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes, (dialog, which) -> viewModel.getPresenter().onDeleteAccount());
    }

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
        finish();
    }

    @Override
    public void selectMember(User user)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(user.getName())
                .setNegativeButton("Cancel", null)
                .setItems(new String[]{"Edit", "Delete"}, (dialog, which) ->
                {
                    if (which == 0)
                    {
                        Intent intent = new Intent(this, EditUserActivity.class);
                        intent.putExtra(USER_ID_EXTRA, user.getID());
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        viewModel.getPresenter().onDeleteMember(user);
                    }
                })
                .show();
    }

    private void goToHomepage()
    {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToAddMember()
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra(RegisterActivity.MODE_EXTRA,RegisterActivity.ADD_MEMBER_EXTRA);
        startActivity(intent);
        finish();
    }
}
