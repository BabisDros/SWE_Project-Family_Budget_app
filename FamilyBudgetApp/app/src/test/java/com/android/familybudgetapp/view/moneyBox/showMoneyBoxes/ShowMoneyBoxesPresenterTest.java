package com.android.familybudgetapp.view.moneyBox.showMoneyBoxes;

import static org.junit.Assert.*;

import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.MoneyBox;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.memorydao.MemoryInitializer;
import com.android.familybudgetapp.memorydao.UserDAOMemory;
import com.android.familybudgetapp.utilities.Quadruples;
import com.android.familybudgetapp.utilities.Tuples;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ShowMoneyBoxesPresenterTest {

    private Initializer dataHelper;
    private Set<Family> families;
    private ShowMoneyBoxesStub stub;


    @Before
    public void setUp(){
        dataHelper = new MemoryInitializer();
        dataHelper.prepareData();
        families = dataHelper.getFamilyDAO().findAll();
        stub = new ShowMoneyBoxesStub();
        stub.setPresenter(new ShowMoneyBoxesPresenter(stub, new UserDAOMemory()));
    }

    @Test
    public void getMoneyBoxes() {
        Family family = dataHelper.getUserDAO().findByID(Initializer.currentUserID).getFamily();
        List<Tuples<User,List<MoneyBox>>> data = new ArrayList<>();
        for (User member: family.getMembers().values())
        {
            data.add(new Tuples<>(member, new ArrayList<>(member.getMoneyBoxes().values())));
        }
        List<Quadruples<String, String, Integer, Integer>> formattedData = new ArrayList<>();
        for (Tuples<User, List<MoneyBox>> tupled: data)
        {
            for (MoneyBox moneyBox: tupled.getSecond())
            {
                formattedData.add(new Quadruples<>(moneyBox.getReason(), tupled.getFirst().getName(),
                        moneyBox.getTarget(), moneyBox.getCurrentAmount()));
            }
        }

        List<Quadruples<String, String, Integer, Integer>> result = stub.getPresenter().getMoneyBoxes();
        assertEquals(formattedData.size(), result.size());
        for (Quadruples<String, String, Integer, Integer> expected : formattedData) {
            assertTrue(result.contains(expected));
        }

    }
}