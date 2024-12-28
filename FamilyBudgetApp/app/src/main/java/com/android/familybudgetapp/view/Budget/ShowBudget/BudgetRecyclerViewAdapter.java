package com.android.familybudgetapp.view.Budget.ShowBudget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.utilities.Tuples;

import java.util.List;

public class BudgetRecyclerViewAdapter extends RecyclerView.Adapter<BudgetRecyclerViewAdapter.ViewHolder> {

    private final List<Tuples<String, Integer>> mValues;

    /**
     * @param items The list of objects which are to displayed
     */
    public BudgetRecyclerViewAdapter(List<Tuples<String, Integer>> items) {
        mValues = items;
    }

    /**
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_2_values_details, parent, false));
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Tuples<String, Integer> currentItem = mValues.get(position);
        holder.txtItemName.setText(currentItem.getFirst());
        holder.txtItemAmount.setText(String.valueOf(currentItem.getSecond()));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView txtItemName;
        public final TextView txtItemAmount;

        /**
         * Constructor of ViewHolder
         * @param view current view
         * binds from view the placeholders of name, value, and button for details
         */
        public ViewHolder(View view) {
            super(view);
            txtItemName = view.findViewById(R.id.item_name);
            txtItemAmount = view.findViewById(R.id.item_value);
        }

    }

}
