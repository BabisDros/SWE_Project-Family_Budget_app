package com.android.familybudgetapp.view.globalStatistics;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.utilities.Quadruples;

import java.time.Year;
import java.time.YearMonth;
import java.util.List;

public class GlobalStatisticsYearlyViewAdapter extends RecyclerView.Adapter<GlobalStatisticsYearlyViewAdapter.ViewHolder> {
    private final List<Quadruples<YearMonth, Double, Double, Double>> mValues;

    /**
     * @param items The list of objects which are to displayed
     */
    public GlobalStatisticsYearlyViewAdapter(List<Quadruples<YearMonth, Double, Double, Double>> items) {
        mValues = items;
    }

    /**
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     */
    @Override
    public GlobalStatisticsYearlyViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new GlobalStatisticsYearlyViewAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_7_values_stats, parent, false));
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(GlobalStatisticsYearlyViewAdapter.ViewHolder holder, int position) {

        final Quadruples<YearMonth, Double, Double, Double> currentItem = mValues.get(position);
        holder.txtDate.setText(Year.from(currentItem.getFirst()).toString());
        holder.txtCat1.setText("Have at least 1000€ in savings: ");
        holder.txtCat2.setText("Have at least 5000€ in savings: ");
        holder.txtCat3.setText("Have at least 10000€ in savings: ");
        String result = String.format("%.2f", currentItem.getSecond());
        holder.txtValue1.setText(result + "%");
        result = String.format("%.2f", currentItem.getThird());
        holder.txtValue2.setText(result + "%");
        result = String.format("%.2f", currentItem.getFourth());
        holder.txtValue3.setText(result + "%");
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView txtDate;
        public final TextView txtCat1;
        public final TextView txtCat2;
        public final TextView txtCat3;

        public final TextView txtValue1;
        public final TextView txtValue2;
        public final TextView txtValue3;

        /**
         * Constructor of ViewHolder
         * @param view current view
         * binds from view the placeholders of name, value, and button for details
         */
        public ViewHolder(View view) {
            super(view);
            txtDate = view.findViewById(R.id.text_title_date);
            txtCat1 = view.findViewById(R.id.text_category1);
            txtCat2 = view.findViewById(R.id.text_category2);
            txtCat3 = view.findViewById(R.id.text_category3);
            txtValue1 = view.findViewById(R.id.text_value1);
            txtValue2 = view.findViewById(R.id.text_value2);
            txtValue3 = view.findViewById(R.id.text_value3);
        }

    }
}
