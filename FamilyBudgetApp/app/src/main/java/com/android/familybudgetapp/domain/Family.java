package com.android.familybudgetapp.domain;

import java.util.HashSet;
import java.util.Set;

public class Family
{
    private final long id;
    private static long  idCounter=0;
    private  String familyName;
    private long  yearlySavings;
    private long  savings;
    private Set<User> members = new HashSet<>();
    private Set<CashFlowCategory>  categories = new HashSet<>();
    private  Set<MonthlySurplus> monthlySurpluses = new HashSet<>();
    private int currentSurplus;


    public Family(String name)
    {
        setName(name);
        this.id = idCounter++;
    }

    public long getID()
    {
        return id;
    }

    public String getName()
    {
        return familyName;
    }
    public long getSavings()
    {
        return savings;
    }

    public long getYearlySavings()
    {
        return yearlySavings;
    }

    public  Set<User> getMembers()
    {
         return new HashSet<User>(members);
    }

    public Set<MonthlySurplus> getMonthlySurpluses()
    {
        return new HashSet<MonthlySurplus>(monthlySurpluses) ;
    }

    public long getCurrentSurplus()
    {
        return currentSurplus;
    }

    public Set<CashFlowCategory> getCategories()
    {
        return new HashSet<CashFlowCategory>(categories) ;
    }

    public void setName(String familyName)
    {
        if(familyName==null)
        {
            throw new IllegalArgumentException("Name shouldn't be null");
        }
        else if (!Utilities.isUsernameValid(familyName))
        {
            throw new IllegalArgumentException("Name should be consisted by:Numbers, letters and underscores");
        }
        this.familyName = familyName;
    }

    public void addToSavings(int amount)
    {
        if(amount<0)
        {
            throw new IllegalArgumentException("Amount should not be negative");
        }
        savings+=amount;
    }

    public  void addMember(User user)
    {
        if(user==null)
        {
            throw new IllegalArgumentException ("User shouldn't be null");
        }
        members.add(user);
    }

    public void addSurplus(MonthlySurplus surplus)
    {
        if(surplus==null)
        {
            throw new IllegalArgumentException ("Surplus shouldn't be null");
        }
        monthlySurpluses.add(surplus);
    }

    public void addCategory(CashFlowCategory category)
    {
        if(category==null)
        {
            throw new IllegalArgumentException ("Category shouldn't be null");

        }
        categories.add(category);
    }

    public void resetYearSavings()
    {
        yearlySavings=0;
    }
}
