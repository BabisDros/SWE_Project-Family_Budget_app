package com.android.familybudgetapp.view.membersManagement;

import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.familybudgetapp.R;

import java.util.List;
import java.util.function.Function;

public class GenericRecyclerViewAdapter<T>
        extends RecyclerView.Adapter<GenericRecyclerViewAdapter<T>.ViewHolderSingleTextView>
{
    private final List<T> list;
    private final SelectionListener<T> listener;
    private final Function<T, String> getText;
    private final int itemLayout;


    public GenericRecyclerViewAdapter(List<T> list, SelectionListener<T> listener, Function<T, String> getText, int itemLayout)
    {
        this.list = list;
        this.listener = listener;
        this.getText = getText;
        this.itemLayout = itemLayout;
    }

    @NonNull
    @Override
    public ViewHolderSingleTextView onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ViewHolderSingleTextView(LayoutInflater.from(parent.getContext())
                .inflate(itemLayout, parent, false));
    }

    public void updateList(int removedIndex)
    {
        list.remove(removedIndex);
        notifyItemRemoved(removedIndex);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSingleTextView holder, int position)
    {
        final T currentObject = list.get(position);
        holder.txtItem.setText(getText.apply(currentObject));
        holder.txtItem.setOnClickListener((T) -> listener.selectItem(currentObject));
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolderSingleTextView extends RecyclerView.ViewHolder
    {
        public final TextView txtItem;

        /**
         * Constructor of ViewHolder
         *
         * @param view current view binds from view the TextView
         */
        public ViewHolderSingleTextView(View view)
        {
            super(view);
            txtItem = view.findViewById(R.id.txt_item);
        }
    }

    public interface SelectionListener<T>
    {
        void selectItem(T item);
    }
}
