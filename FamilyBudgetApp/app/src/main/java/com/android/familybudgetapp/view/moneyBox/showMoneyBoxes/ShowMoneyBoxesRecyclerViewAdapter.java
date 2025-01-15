package com.android.familybudgetapp.view.moneyBox.showMoneyBoxes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.utilities.AmountConversion;
import com.android.familybudgetapp.utilities.Quadruples;

import java.util.List;

public class ShowMoneyBoxesRecyclerViewAdapter  extends RecyclerView.Adapter<ShowMoneyBoxesRecyclerViewAdapter.ViewHolder> {

    private final List<Quadruples<String, String, Integer, Integer>> mValues;

    /**
     * @param items The list of objects which are to displayed
     */
    public ShowMoneyBoxesRecyclerViewAdapter(List<Quadruples<String, String, Integer, Integer>> items) {
        mValues = items;
    }

    /**
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     */
    @Override
    public ShowMoneyBoxesRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ShowMoneyBoxesRecyclerViewAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_5_values_details, parent, false));
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ShowMoneyBoxesRecyclerViewAdapter.ViewHolder holder, int position) {

        final Quadruples<String, String, Integer, Integer> currentItem = mValues.get(position);
        holder.txtItemName.setText(currentItem.getFirst());
        holder.txtItemOwner.setText(currentItem.getSecond());
        holder.txtTarget.setText("Goal: " + AmountConversion.toEuro(currentItem.getThird()));
        holder.txtCurrent.setText("Current: " + AmountConversion.toEuro(currentItem.getFourth()));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView txtItemName;
        public final TextView txtItemOwner;
        public final TextView txtTarget;
        public final TextView txtCurrent;

        /**
         * Constructor of ViewHolder
         * @param view current view
         * binds from view the placeholders of name, value, and button for details
         */
        public ViewHolder(View view) {
            super(view);
            txtItemName = view.findViewById(R.id.item_name);
            txtItemOwner = view.findViewById(R.id.item_owner);
            txtTarget = view.findViewById(R.id.item_attribute1);
            txtCurrent = view.findViewById(R.id.item_attribute2);
        }

    }
}
