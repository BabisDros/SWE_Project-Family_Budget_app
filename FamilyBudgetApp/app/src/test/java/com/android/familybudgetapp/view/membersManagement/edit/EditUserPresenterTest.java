package com.android.familybudgetapp.view.membersManagement.edit;

import com.android.familybudgetapp.dao.FamilyDAO;
import com.android.familybudgetapp.dao.Initializer;
import com.android.familybudgetapp.dao.UserDAO;
import com.android.familybudgetapp.domain.User;
import com.android.familybudgetapp.memorydao.FamilyDAOMemory;
import com.android.familybudgetapp.memorydao.MemoryInitializer;
import com.android.familybudgetapp.memorydao.UserDAOMemory;
import com.android.familybudgetapp.utilities.PBKDF2Hashing;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EditUserPresenterTest
{
    private EditUserPresenter presenter;
    private EditUserViewStub viewStub;
    UserDAO userDAO;
    User currentUser;

    @Before
    @Test
    public void setUp()
    {
        new MemoryInitializer().prepareData();
        presenter = new EditUserPresenter();
        viewStub = new EditUserViewStub();

        userDAO = new UserDAOMemory();
        FamilyDAO familyDAO = new FamilyDAOMemory();

        presenter.setView(viewStub);
        presenter.setUserDAO(userDAO);
        presenter.setFamilyDAO(familyDAO);

        currentUser = userDAO.findByUsername(Initializer.protector1Username);
        Initializer.currentUserID = currentUser.getID();
    }

    @Test
    public void setUserProtectorData()
    {
        presenter.setUserData(currentUser.getID());
        assertEquals(currentUser.getUsername(), viewStub.getUsername());
        assertEquals(currentUser.getName(), viewStub.getDisplayName());
        assertEquals(currentUser.getFamily().getName(), viewStub.getFamilyName());
    }

    @Test
    public void setMemberProtectorData()
    {
        currentUser = userDAO.findByUsername(Initializer.member1Username);
        Initializer.currentUserID = currentUser.getID();

        presenter.setUserData(currentUser.getID());
        assertEquals(currentUser.getUsername(), viewStub.getUsername());
        assertEquals(currentUser.getName(), viewStub.getDisplayName());
        assertEquals(currentUser.getFamily().getName(), viewStub.getFamilyName());

        assertTrue(viewStub.isFamilyFieldDisabled());
    }

    @Test
    public void saveValidDataUniqueName() throws Exception
    {
        presenter.setUserData(currentUser.getID());

        String username = "JaneTest";
        String password = "123456";
        String displayName = "Jane";
        String familyName = "Jane family";
        presenter.save(username, password, displayName, familyName);

        assertEquals(username, currentUser.getUsername());
        assertTrue(PBKDF2Hashing.verifyPassword(password, currentUser.getPassword()));
        assertEquals(displayName, currentUser.getName());
        assertEquals(familyName, currentUser.getFamily().getName());
        assertEquals(1, viewStub.getGoToMemberManagementActivityCounter());
    }

    @Test
    public void saveValidDataUniqueNameSamePassword() throws Exception
    {
        presenter.setUserData(currentUser.getID());

        String username = "JaneTest";
        String password = "";
        String displayName = "Jane";
        String familyName = "Jane family";
        presenter.save(username, password, displayName, familyName);

        assertEquals(username, currentUser.getUsername());
        assertTrue(PBKDF2Hashing.verifyPassword(Initializer.protector1Password, currentUser.getPassword()));
        assertEquals(displayName, currentUser.getName());
        assertEquals(familyName, currentUser.getFamily().getName());
        assertEquals(1, viewStub.getGoToMemberManagementActivityCounter());
    }

    @Test
    public void saveInvalidData()
    {
        presenter.setUserData(currentUser.getID());

        String username = "";
        String password = "123456";
        String displayName = "Jane";
        String familyName = "Jane family";
        presenter.save(username, password, displayName, familyName);
        assertNotEquals(username, currentUser.getUsername());
    }

    @Test
    public void validateExistingName()
    {
        presenter.setUserData(currentUser.getID());
        assertFalse(presenter.validateUsernameUniqueness(Initializer.member1Username));
    }

    @Test
    public void validateNameOfUser()
    {
        presenter.setUserData(currentUser.getID());
        assertTrue(presenter.validateUsernameUniqueness(Initializer.protector1Username));
    }
}
