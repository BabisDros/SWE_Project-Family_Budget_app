package com.android.familybudgetapp.view.membersManagement;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.view.HomePage.HomePageActivity;
import com.android.familybudgetapp.view.authentication.edit.EditUserActivity;
import com.android.familybudgetapp.view.base.BaseActivity;

import java.util.List;

public class MembersManagementActivity extends BaseActivity<MembersManagementViewModel>
        implements MembersManagementView, MemberRecyclerViewAdapter.MemberSelectionListener
{
    public static final String FAMILY_ID_EXTRA = "family_id";
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
        Intent intent = getIntent();
        long familyId = intent.getLongExtra(FAMILY_ID_EXTRA, -1);

        viewModel.getPresenter().searchFamilyMembers(familyId);
        setupDeleteAccountDialog();
    }

    private void setupHomepageBtn()
    {
        Button btnHomepage = findViewById(R.id.btn_homepage);
        btnHomepage.setOnClickListener(v-> goToHomepage());
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

    public void goToHomepage()
    {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
        finish();
    }
}
