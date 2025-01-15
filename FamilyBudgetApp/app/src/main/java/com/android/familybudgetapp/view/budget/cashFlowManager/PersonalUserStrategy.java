package com.android.familybudgetapp.view.budget.cashFlowManager;

import com.android.familybudgetapp.domain.User;

import java.util.Collections;
import java.util.List;

public class PersonalUserStrategy implements UserRetrievalStrategy {
    private final User currentUser;

    public PersonalUserStrategy(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public List<User> getUsers() {
        return Collections.singletonList(currentUser);
    }
}
