package com.android.familybudgetapp.view.MoneyBox.ShowMoneyBoxes;

import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.MoneyBox;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.utilities.Quadruples;

import java.util.ArrayList;
import java.util.List;

public class ShowMoneyBoxesPresenter {

    private ShowMoneyBoxesView view;
    private Family family;

    public ShowMoneyBoxesPresenter(ShowMoneyBoxesView view, UserDAO user)
    {
        this.view = view;
        family = user.find(Initializer.currentUserID).getFamily();

    }

    /**
     * Find all the moneyBoxes of the logged in family
     * @return list of moneyboxes is {@link com.android.familybudgetapp.utilities.Quadruples} format
     */
    public List<Quadruples<String, String, Integer, Integer>> getMoneyBoxes()
    {
        List<Quadruples<String, String, Integer, Integer>> myList = new ArrayList<>();
        for (User user: family.getMembers().values())
        {
            for (MoneyBox moneyBox: user.getMoneyBoxes().values())
            {
                myList.add(new Quadruples<>(moneyBox.getReason(), user.getName(), moneyBox.getTarget(), moneyBox.getCurrentAmount()));
            }
        }
        return myList;
    }
}
