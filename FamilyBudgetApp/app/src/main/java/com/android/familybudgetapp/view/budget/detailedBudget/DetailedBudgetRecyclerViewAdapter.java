package com.android.familybudgetapp.view.budget.detailedBudget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.utilities.AmountConversion;
import com.android.familybudgetapp.utilities.Quadruples;

import java.time.LocalDateTime;
import java.util.List;

public class DetailedBudgetRecyclerViewAdapter extends RecyclerView.Adapter<DetailedBudgetRecyclerViewAdapter.ViewHolder> {
    private final List<Quadruples<String, String, Integer, List<LocalDateTime>>> mValues;

    /**
     * @param items The list of objects which are to displayed
     */
    public DetailedBudgetRecyclerViewAdapter(List<Quadruples<String, String, Integer, List<LocalDateTime>>> items) {
        mValues = items;
    }

    /**
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     */
    @Override
    public DetailedBudgetRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new DetailedBudgetRecyclerViewAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_5_values_details, parent, false));
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(DetailedBudgetRecyclerViewAdapter.ViewHolder holder, int position) {

        final Quadruples<String, String, Integer, List<LocalDateTime>> currentItem = mValues.get(position);
        holder.txtItemName.setText(currentItem.getFirst());
        holder.txtItemOwner.setText(currentItem.getSecond());
        holder.txtItemAmount.setText("Amount: " + AmountConversion.toEuro(currentItem.getThird()));
        if (!currentItem.getFourth().isEmpty())
            holder.txtStartDate.setText("Started: " + currentItem.getFourth().get(0));
        else
            holder.txtStartDate.setVisibility(View.INVISIBLE);
        if (currentItem.getFourth().size() > 1)
            holder.txtEndDate.setText("Ends: " + currentItem.getFourth().get(1));
        else
            holder.txtEndDate.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView txtItemName;
        public final TextView txtItemOwner;
        public final TextView txtItemAmount;
        public final TextView txtStartDate;
        public final TextView txtEndDate;

        /**
         * Constructor of ViewHolder
         * @param view current view
         * binds from view the placeholders of name, value, and button for details
         */
        public ViewHolder(View view) {
            super(view);
            txtItemName = view.findViewById(R.id.item_name);
            txtItemOwner = view.findViewById(R.id.item_owner);
            txtItemAmount = view.findViewById(R.id.item_attribute1);
            txtStartDate = view.findViewById(R.id.item_attribute2);
            txtEndDate = view.findViewById(R.id.item_attribute3);
        }

    }
}
