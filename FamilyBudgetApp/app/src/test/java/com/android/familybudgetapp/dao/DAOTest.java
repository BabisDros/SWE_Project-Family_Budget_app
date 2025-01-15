package com.android.familybudgetapp.dao;
import static org.junit.Assert.*;

import com.android.familybudgetapp.domain.Family;
import com.android.familybudgetapp.memorydao.FamilyDAOMemory;
import com.android.familybudgetapp.memorydao.MemoryInitializer;
import com.android.familybudgetapp.memorydao.UserDAOMemory;

import org.junit.Before;
import org.junit.Test;

public class DAOTest
{

    @Before
    public void setUp(){
        Initializer memory = new MemoryInitializer();
        memory.prepareData();
    }

    /**
     * Test search of non-existing userId
     */
    @Test
    public void NonExistingUser(){

        assertNull(new UserDAOMemory().findByID(-1));
    }

    /**
     * Test search of non-existing familyId
     */
    @Test
    public void NonExistingFamily(){

        assertNull(new FamilyDAOMemory().findByID(-1));
    }

    /**
     * Test search of non-existing userId
     */
    @Test
    public void FamilySearch(){
        Family fam = new UserDAOMemory().findByID(Initializer.currentUserID).getFamily();
        assertEquals(fam, new FamilyDAOMemory().findByID(fam.getID()));
    }
}
