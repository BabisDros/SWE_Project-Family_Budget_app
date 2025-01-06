package com.android.familybudgetapp.view.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.android.familybudgetapp.R;

public class ViewHolderSingleTextView extends RecyclerView.ViewHolder
{
    public final TextView txtItem;
    public ViewHolderSingleTextView(View view)
    {
        super(view);
        txtItem = view.findViewById(R.id.txt_item);
    }
}
