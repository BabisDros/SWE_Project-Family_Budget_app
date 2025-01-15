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

    /**
     * Tests if the setUserData method in the EditUserPresenter class
     * correctly sets the user data by asserting that the username, display name,
     * and family name are properly passed in the view.
     */
    @Test
    public void setUserProtectorData()
    {
        presenter.setUserData(currentUser.getID());
        assertEquals(currentUser.getUsername(), viewStub.getUsername());
        assertEquals(currentUser.getName(), viewStub.getDisplayName());
        assertEquals(currentUser.getFamily().getName(), viewStub.getFamilyName());
    }

    /**
     * Tests if the setUserData method in the EditUserPresenter class
     * correctly sets the user data for a member by asserting that the username, display name,
     * family name are properly displayed, and the family field is disabled in the view to prevent editing.
     */
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

    /**
     * Tests if the save method in the EditUserPresenter class correctly saves valid user data
     * with a unique username by asserting that the username, password, display name, and family name
     * are properly updated and correctly calls goToMemberManagementActivity in the view stub by counting the number of calls.
     */
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

    /**
     * Tests if the save method in the EditUserPresenter class correctly saves valid user data
     * with a unique username and the same password by asserting that the username, display name,
     * and family name are properly updated but the password remains the same.
     * Asserts it correctly calls goToMemberManagementActivity in the view stub by counting the number of calls.
     */
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

    /**
     * Tests if the save method in the EditUserPresenter class
     * correctly does not update the username when an invalid username is provided.
     */
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

    /**
     * Tests if the validateUsernameUniqueness method in the EditUserPresenter class
     * correctly validates that an existing username is not unique by asserting that
     * the method returns false for an already existing username.
     */
    @Test
    public void validateExistingName()
    {
        presenter.setUserData(currentUser.getID());
        assertFalse(presenter.validateUsernameUniqueness(Initializer.member1Username));
    }

    /**
     * Tests if the validateUsernameUniqueness method in the EditUserPresenter class
     * correctly returns true if a user with the same username exists and is the user currently being edited.
     */
    @Test
    public void validateNameOfUser()
    {
        presenter.setUserData(currentUser.getID());
        assertTrue(presenter.validateUsernameUniqueness(Initializer.protector1Username));
    }
}
