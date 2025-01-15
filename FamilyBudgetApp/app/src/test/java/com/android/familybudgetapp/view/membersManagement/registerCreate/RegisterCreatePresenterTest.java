package com.android.familybudgetapp.view.membersManagement.registerCreate;

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

public class RegisterCreatePresenterTest
{
    private RegisterCreatePresenter presenter;
    private RegisterCreateViewStub viewStub;
    UserDAO userDAO;

    @Before
    @Test
    public void setUp()
    {
        new MemoryInitializer().prepareData();
        presenter = new RegisterCreatePresenter();
        viewStub = new RegisterCreateViewStub();

        userDAO = new UserDAOMemory();
        FamilyDAO familyDAO = new FamilyDAOMemory();

        presenter.setView(viewStub);
        presenter.setUserDAO(userDAO);
        presenter.setFamilyDAO(familyDAO);
    }

    /**
     * Tests if the register method in the RegisterCreatePresenter class
     * correctly registers a new member with valid data and unique username
     * by asserting that the member's details are correctly saved and the success message is displayed.
     */
    @Test
    public void registerValidDataUniqueName() throws Exception
    {
        String username = "JaneTest";
        String password = "123456";
        String displayName = "Jane";
        String familyName = "Jane family";
        presenter.register(username, password, displayName, familyName);

        User member = userDAO.findByID(Initializer.currentUserID);

        assertEquals(username, member.getUsername());
        assertTrue(PBKDF2Hashing.verifyPassword(password, member.getPassword()));
        assertEquals(displayName, member.getName());
        assertEquals(familyName, member.getFamily().getName());
        assertEquals(String.format(RegisterCreatePresenter.SUCCESSFUL_ADD_MEMBER_TITLE, username), viewStub.getAddMemberTitle());
    }

    /**
     * Tests if the register method in the RegisterCreatePresenter class
     * correctly shows an error message when a user tries to register with an existing username.
     */
    @Test
    public void registerExistingUsername()
    {
        String username = Initializer.member1Username;
        String password = "123456";
        String displayName = "Jane";
        String familyName = "Jane family";
        presenter.register(username, password, displayName, familyName);

        assertEquals(RegisterCreatePresenter.USER_EXISTS_TITLE, viewStub.getErrorTitle());
    }

    /**
     * Tests if the register method in the RegisterCreatePresenter class
     * correctly does not create a user and shows an error message when a user tries to register with an invalid username.
     */
    @Test
    public void registerInvalidUsername()
    {
        String username = "3#@43f";
        String password = "123456";
        String displayName = "Jane";
        String familyName = "Jane family";
        presenter.register(username, password, displayName, familyName);

        User member = userDAO.findByUsername(username);
        assertNull(member);
        assertEquals(RegisterCreatePresenter.WRONG_USERNAME_TITLE, viewStub.getErrorTitle());
    }

    /**
     * Tests if the register method in the RegisterCreatePresenter class
     * correctly does not create a user and shows an error message when a user tries to register with an invalid password.
     */
    @Test
    public void registerInvalidPassword()
    {
        String username = "JaneTest";
        String password = "";
        String displayName = "Jane";
        String familyName = "Jane family";
        presenter.register(username, password, displayName, familyName);

        User member = userDAO.findByUsername(username);
        assertNull(member);
        assertEquals(RegisterCreatePresenter.WRONG_PASSWORD_TITLE, viewStub.getErrorTitle());
    }

    /**
     * Tests if the register method in the RegisterCreatePresenter class
     * correctly does not create a user and shows an error message when a user tries to register with an invalid displayName.
     */
    @Test
    public void registerInvalidDisplayName()
    {
        String username = "JaneTest";
        String password = "1234";
        String displayName = "3d#%";
        String familyName = "Jane family";
        presenter.register(username, password, displayName, familyName);

        User member = userDAO.findByUsername(username);
        assertNull(member);
        assertEquals(RegisterCreatePresenter.WRONG_DISPLAY_NAME_TITLE, viewStub.getErrorTitle());
    }

    /**
     * Tests if the register method in the RegisterCreatePresenter class
     * correctly does not create a user and shows an error message when a user tries to register with an invalid familyName.
     */
    @Test
    public void registerInvalidFamilyName()
    {
        String username = "JaneTest";
        String password = "1234";
        String displayName = "TEST";
        String familyName = "Jane's family";
        presenter.register(username, password, displayName, familyName);

        User member = userDAO.findByUsername(username);
        assertNull(member);
        assertEquals(RegisterCreatePresenter.WRONG_FAMILY_NAME_TITLE, viewStub.getErrorTitle());
    }

    /**
     * Tests if the register and saveMember methods in the RegisterCreatePresenter class
     * correctly register a new protector and then save a new member with valid data by asserting
     * that the member's details are correctly saved, and the success message is displayed.
     */
    @Test
    public void registerAndSaveMemberValidData() throws Exception
    {
        String familyName = "mark Family";
        presenter.register("mark", "mark", "mark", familyName);

        String username = "JaneTest";
        String password = "123456";
        String displayName = "Jane";

        presenter.saveMember(username, password, displayName, familyName);

        User protector = userDAO.findByUsername("mark");
        User member = protector.getFamily().getMembers().get(protector.getID() + 1);

        assertEquals(username, member.getUsername());
        assertTrue(PBKDF2Hashing.verifyPassword(password, member.getPassword()));
        assertEquals(displayName, member.getName());
        assertEquals(familyName, member.getFamily().getName());
        assertEquals(String.format(RegisterCreatePresenter.SUCCESSFUL_ADD_MEMBER_TITLE, username), viewStub.getAddMemberTitle());
    }

    /**
     * Tests if the register and saveMember methods in the RegisterCreatePresenter class
     * correctly register a new protector but does not save a new member with wrong username by asserting
     * that the member is not added to family's members, and the correct error message is displayed.
     */
    @Test
    public void registerAndSaveMemberWrongUsername() throws Exception
    {
        String familyName = "mark Family";
        presenter.register("mark", "mark", "mark", familyName);

        String username = "";
        String password = "123456";
        String displayName = "Jane";

        presenter.saveMember(username, password, displayName, familyName);

        User protector = userDAO.findByUsername("mark");
        User member = protector.getFamily().getMembers().get(protector.getID() + 1);
        assertNull(member);
        assertEquals(RegisterCreatePresenter.WRONG_USERNAME_TITLE, viewStub.getErrorTitle());
    }

    /**
     * Tests if the enableAddMemberMode method correctly sets up the view for adding a new member.
     * If the enableAddMemberMode is called first time, it calls setupUIToAddMemberMode method in the view.
     * Checks if the family name is displayed and the input fields are cleared.
     */
    @Test
    public void memberModeNotEnabled()
    {
        String familyName = "mark Family";
        presenter.register("mark", "mark", "mark", familyName);
        presenter.enableAddMemberMode();
        assertEquals(1,viewStub.uiSetupCounter());

        assertEquals(familyName, viewStub.getFamilyName());
        assertTrue(viewStub.isFieldsCleared());
    }

    /**
     * Tests if the enableAddMemberMode method correctly sets up the view for adding a new member.
     * If the enableAddMemberMode is called first time, it calls setupUIToAddMemberMode method in the view.
     * If the enableAddMemberMode is called again, it does not call setupUIToAddMemberMode again.
     * Checks if the family name is displayed and the input fields are cleared.
     */
    @Test
    public void memberModeEnabled()
    {
        //enable member mode
        String familyName = "mark Family";
        presenter.register("mark", "mark", "mark", familyName);
        presenter.enableAddMemberMode();
        assertEquals(1,viewStub.uiSetupCounter());

        //call to enable again, will not call setupUIToAddMemberMode
        presenter.enableAddMemberMode();
        assertNotEquals(2,viewStub.uiSetupCounter());
        assertEquals(familyName, viewStub.getFamilyName());
        assertTrue(viewStub.isFieldsCleared());
    }

    /**
     * Tests if the goToMemberManagement method in the RegisterCreatePresenter class
     * correctly calls the goToMemberManagement in the view stub by counting the numbers of calls.
     */
    @Test
    public void goToMemberManagement()
    {
        presenter.goToMemberManagement();
        assertEquals(1, viewStub.getGoToMemberCounter());
    }
}
