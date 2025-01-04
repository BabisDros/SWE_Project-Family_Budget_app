package com.android.familybudgetapp.view.membersManagement;

import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.domain.User;

import java.util.List;

public class MemberRecyclerViewAdapter extends RecyclerView.Adapter<MemberRecyclerViewAdapter.ViewHolder>
{
    private final List<User> members;
    private final MemberSelectionListener listener;

    public MemberRecyclerViewAdapter(List<User> members, MemberSelectionListener listener)
    {
        this.members = members;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.member_list_item, parent, false));
    }

    public void updateMembers(int removedIndex)
    {
        members.remove(removedIndex);
        notifyItemRemoved(removedIndex);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberRecyclerViewAdapter.ViewHolder holder, int position)
    {
        final User currentUser = members.get(position);
        holder.txtMember.setText(currentUser.getUsername());
        holder.txtMember.setOnClickListener((User) -> listener.selectMember(currentUser));
    }

    @Override
    public int getItemCount()
    {
        return members.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public final TextView txtMember;
        /**
         * Constructor of ViewHolder
         *
         * @param view current view
         *             binds from view the placeholders of name, value, and button for details
         */
        public ViewHolder(View view)
        {
            super(view);
            txtMember = view.findViewById(R.id.txt_member);
        }
    }

    public interface MemberSelectionListener
    {
        void selectMember(User b);
    }
}
