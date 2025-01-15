package com.android.familybudgetapp.view.globalStatistics;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.utilities.Tuples;

import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class GlobalStatisticsMonthlyViewAdapter extends RecyclerView.Adapter<GlobalStatisticsMonthlyViewAdapter.ViewHolder> {
    private final List<Tuples<YearMonth, Double>> mValues;

    /**
     * @param items The list of objects which are to displayed
     */
    public GlobalStatisticsMonthlyViewAdapter(List<Tuples<YearMonth, Double>> items) {
        mValues = items;
    }

    /**
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     */
    @Override
    public GlobalStatisticsMonthlyViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new GlobalStatisticsMonthlyViewAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_2_values_stats, parent, false));
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(GlobalStatisticsMonthlyViewAdapter.ViewHolder holder, int position) {

        final Tuples<YearMonth, Double> currentItem = mValues.get(position);
        holder.txtYear.setText(String.valueOf(currentItem.getFirst().getYear()));
        holder.txtMonth.setText(currentItem.getFirst().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        String result = String.format("%.2f", currentItem.getSecond());
        holder.txtStat.setText(result + "%");
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView txtYear;
        public final TextView txtMonth;
        public final TextView txtStat;

        /**
         * Constructor of ViewHolder
         * @param view current view
         * binds from view the placeholders of name, value, and button for details
         */
        public ViewHolder(View view) {
            super(view);
            txtYear = view.findViewById(R.id.item_year);
            txtMonth = view.findViewById(R.id.item_month);
            txtStat = view.findViewById(R.id.item_value);
        }

    }
}
