package com.android.familybudgetapp.view.membersManagement;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.domain.Income;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.view.authentication.edit.EditUserActivity;
import com.android.familybudgetapp.view.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class MembersManagementActivity extends BaseActivity<MembersManagementViewModel>
        implements MembersManagementView, MemberRecyclerViewAdapter.MemberSelectionListener
{

    public static final String FAMILY_ID_EXTRA = "family_id";
    public static final String USER_ID_EXTRA = "user_id";

    MemberRecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_member_management);
        viewModel.getPresenter().setView(this);

        Intent intent = getIntent();
        long familyId = intent.getLongExtra(FAMILY_ID_EXTRA, -1);

        viewModel.getPresenter().searchFamilyMembers(familyId);
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
                        viewModel.getPresenter().deleteMember(user);
                    }
                })
                .show();

    }
}
