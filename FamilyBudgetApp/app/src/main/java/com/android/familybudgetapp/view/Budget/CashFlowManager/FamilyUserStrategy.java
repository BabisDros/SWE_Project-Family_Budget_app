package com.android.familybudgetapp.view.Budget.CashFlowManager;

import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.domain.User;

import java.util.ArrayList;
import java.util.List;

public class FamilyUserStrategy implements UserRetrievalStrategy {
    private final Family family;

    public FamilyUserStrategy(Family family) {
        this.family = family;
    }

    public Family getFamily() {
        return family;
    }
    @Override
    public List<User> getUsers() {
        return new ArrayList<>(family.getMembers().values());
    }
}
