package com.android.familybudgetapp.view.moneyBox.showMoneyBoxes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.memorydao.UserDAOMemory;
import com.android.familybudgetapp.utilities.Quadruples;
import com.android.familybudgetapp.view.moneyBox.addMoneyBox.AddMoneyBoxActivity;

import java.util.List;

public class ShowMoneyBoxesActivity extends AppCompatActivity implements ShowMoneyBoxesView {
    private ShowMoneyBoxesPresenter presenter;
    private RecyclerView recyclerView;
    private ActivityResultLauncher<Intent> addMoneyBoxLauncher;

    protected void onCreate(Bundle savedInstanceSate)
    {
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.activity_show_moneyboxes);
        presenter = new ShowMoneyBoxesPresenter(this, new UserDAOMemory());
        findViewById(R.id.btn_back).setOnClickListener(v -> goBack());
        findViewById(R.id.btn_add).setOnClickListener(v -> addMoneyBox());

        // recycler
        recyclerView = findViewById(R.id.recyclerView_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadData();

        // update data if moneyBox is successfully added
        addMoneyBoxLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        loadData();
                    }
                }
        );
    }

    private void goBack() {
        this.finish();
    }

    protected void onPause()
    {
        super.onPause();
    }

    public List<Quadruples<String, String, Integer, Integer>> getMoneyBoxes()
    {
        return presenter.getMoneyBoxes();
    }

    /**
     * Go to AddMoneyBoxActivity
     */
    public void addMoneyBox()
    {
        Intent intent = new Intent(ShowMoneyBoxesActivity.this, AddMoneyBoxActivity.class);
        addMoneyBoxLauncher.launch(intent);
    }

    /**
     * Load moneyboxes to recycler View
     */
    @Override
    public void loadData() {
        List<Quadruples<String, String, Integer, Integer>> moneyBoxesList = getMoneyBoxes();
        recyclerView.setAdapter(new ShowMoneyBoxesRecyclerViewAdapter(moneyBoxesList));
    }

}