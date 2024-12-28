package com.android.familybudgetapp.view.MoneyBox.ShowMoneyBoxes;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.familybudgetapp.R;
import com.android.familybudgetapp.memorydao.UserDAOMemory;
import com.android.familybudgetapp.utilities.Quadruples;
import com.android.familybudgetapp.view.HomePage.HomePageActivity;

import java.util.List;

public class ShowMoneyBoxesActivity extends AppCompatActivity implements ShowMoneyBoxesView {
    private ShowMoneyBoxesPresenter presenter;

    protected void onCreate(Bundle savedInstanceSate)
    {
        super.onCreate(savedInstanceSate);
        setContentView(R.layout.activity_show_moneyboxes);
        presenter = new ShowMoneyBoxesPresenter(this, new UserDAOMemory());
        findViewById(R.id.btn_back).setOnClickListener(v    -> goBack());

        // recycler
        List<Quadruples<String, String, Integer, Integer>> moneyBoxesList = getMoneyBoxes();
        RecyclerView recyclerViewExpense = findViewById(R.id.recyclerView_items);
        recyclerViewExpense.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewExpense.setAdapter(new ShowMoneyBoxesRecyclerViewAdapter(moneyBoxesList));

    }

    private void goBack() {
        this.finish();
    }

    protected void onPause()
    {
        super.onPause();
    }


    /**
     * Default action for onClick listener
     */
    public void DefaultAction_ReturnToHome()
    {
        Intent intent = new Intent(ShowMoneyBoxesActivity.this, HomePageActivity.class);
        startActivity(intent);
    }


    public List<Quadruples<String, String, Integer, Integer>> getMoneyBoxes()
    {
        return presenter.getMoneyBoxes();
    }
}