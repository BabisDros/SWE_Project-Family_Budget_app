package com.android.familybudgetapp.memorydao;

import com.android.familybudgetapp.dao.FamilyDAO;
import com.android.familybudgetapp.domain.Family;

import java.util.HashSet;
import java.util.Set;

public class FamilyDAOMemory implements FamilyDAO
{
    protected static Set<Family> families = new HashSet<>();

    @Override
    public void delete(Family entity)
    {
        families.remove(entity);
    }

    @Override
    public Set<Family> findAll()
    {
        return new HashSet<>(families);
    }

    @Override
    public void save(Family entity)
    {
        families.add(entity);
    }

    @Override
    public Family findByID(long familyID)
    {
        for (Family family : families)
        {
            if (family.getID() == familyID)
            {
                return family;
            }
        }
        return null;
    }
}
