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

    @Test
    public void registerInvalidUsername()
    {
        String username = "3#@43f";
        String password = "123456";
        String displayName = "Jane";
        String familyName = "Jane family";
        presenter.register(username, password, displayName, familyName);

        assertEquals(RegisterCreatePresenter.WRONG_USERNAME_TITLE, viewStub.getErrorTitle());
    }

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

    @Test
    public void memberModeNotEnabled()
    {
        String familyName = "mark Family";
        presenter.register("mark", "mark", "mark", familyName);
        presenter.enableAddMemberMode();


        assertEquals(familyName, viewStub.getFamilyName());
        assertTrue(viewStub.isFieldsCleared());
    }

    @Test
    public void memberModeEnabled()
    {
        //enable member mode
        String familyName = "mark Family";
        presenter.register("mark", "mark", "mark", familyName);
        presenter.enableAddMemberMode();
        assertTrue(viewStub.isUISetup());

        //call to enable again
        presenter.enableAddMemberMode();

        assertEquals(familyName, viewStub.getFamilyName());
        assertTrue(viewStub.isFieldsCleared());
    }

    @Test
    public void goToMemberManagement()
    {
        presenter.goToMemberManagement();
        assertEquals(1, viewStub.getGoToMemberCounter());
    }
}
