package com.android.familybudgetapp.view.membersManagement.overview;

import com.android.familybudgetapp.dao.FamilyDAO;
import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.memorydao.FamilyDAOMemory;
import com.android.familybudgetapp.memorydao.MemoryInitializer;
import com.android.familybudgetapp.memorydao.UserDAOMemory;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class MembersOverviewPresenterTest
{
    private MembersOverviewPresenter presenter;
    private MembersOverviewViewStub viewStub;
    UserDAO userDAO;
    User currentUser;

    @Before
    @Test
    public void setUp()
    {
        new MemoryInitializer().prepareData();
        presenter = new MembersOverviewPresenter();
        viewStub = new MembersOverviewViewStub();

        userDAO = new UserDAOMemory();
        FamilyDAO familyDAO = new FamilyDAOMemory();

        presenter.setView(viewStub);
        presenter.setUserDAO(userDAO);
        presenter.setFamilyDAO(familyDAO);

        currentUser = userDAO.findByUsername(Initializer.protector1Username);
        Initializer.currentUserID = currentUser.getID();
    }

    @Test
    public void showDeleteVerificationForProtector()
    {
        presenter.showVerification(currentUser);
        assertEquals(MembersOverviewPresenter.CAUTION_TITLE, viewStub.getTitle());
        assertEquals(String.format(MembersOverviewPresenter.CAUTION_MESSAGE, currentUser.getName()), viewStub.getMsg());
    }

    @Test
    public void showDeleteVerificationForMember()
    {
        currentUser = userDAO.findByUsername(Initializer.member1Username);

        presenter.showVerification(currentUser);
        assertEquals(MembersOverviewPresenter.VERIFICATION_TITLE, viewStub.getTitle());
        assertEquals(String.format(MembersOverviewPresenter.VERIFICATION_MESSAGE, currentUser.getName()), viewStub.getMsg());
    }

    @Test
    public void searchMembers()
    {
        presenter.searchFamilyMembers();
        List<User> members = new ArrayList<>(currentUser.getFamily().getMembers().values());
        assertEquals(members, viewStub.getMembers());
    }

    @Test
    public void deleteProtector()
    {
        presenter.showVerification(currentUser);
        presenter.deleteBasedOnFamilyPosition();
        assertTrue(viewStub.appExited);
    }

    @Test
    public void showMembersDeleteMember()
    {
        currentUser = userDAO.findByUsername(Initializer.member1Username);

        presenter.searchFamilyMembers();
        int currentIndexToDelete = viewStub.getMembers().indexOf(currentUser);
        presenter.showVerification(currentUser);
        presenter.deleteBasedOnFamilyPosition();
        assertEquals(currentIndexToDelete, viewStub.getRemovedIndex());
    }

    @Test
    public void navigateToRegister()
    {
        presenter.navigateToRegister();
        presenter.navigateToRegister();
        assertEquals(2, viewStub.getRegisterActivityCounter());
    }

    @Test
    public void navigateToHomepage()
    {
        presenter.navigateToHomepage();
        assertEquals(1, viewStub.getHomepageActivityCounter());
    }
}
