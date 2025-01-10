package com.android.familybudgetapp.view.budget.detailedBudget;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.utilities.Quadruples;

import java.time.LocalDateTime;
import java.util.List;

public class DetailedBudgetActivity extends AppCompatActivity implements DetailedBudgetView{
    private DetailedBudgetViewModel vm;
    private RecyclerView recyclerView;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detailed_budget);
        findViewById(R.id.btn_back).setOnClickListener(v -> goBack());

        vm = new ViewModelProvider(this).get(DetailedBudgetViewModel.class);
        vm.getPresenter().setView(this);

        Intent intent = getIntent();
        String title = "Detailed ";
        if (intent != null) {
            if (intent.hasExtra("viewGroup")) {
                String group = intent.getStringExtra("viewGroup");
                title += group + " ";
            }
            if (intent.hasExtra("dateRange")) {
                String dateRange = intent.getStringExtra("dateRange");
                title += dateRange + " ";
            }
            if (intent.hasExtra("type")) {
                String type = intent.getStringExtra("type");
                title += type;
            }
        }

        ((TextView)findViewById(R.id.text_title)).setText(title);

        List<Quadruples<String, String, Integer, List<LocalDateTime>>> cashFlows = getCashFlows();
        recyclerView = findViewById(R.id.recyclerView_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new DetailedBudgetRecyclerViewAdapter(cashFlows));
    }

    private void goBack() {
        this.finish();
    }

    private List<Quadruples<String, String, Integer, List<LocalDateTime>>> getCashFlows()
    {
        return vm.getPresenter().getFormatedCashFlows();
    }

    @Override
    public SavedStateHandle getState() {
        return vm.getState();
    }
}
