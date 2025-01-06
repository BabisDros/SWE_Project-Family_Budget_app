package com.android.familybudgetapp.domain;

import com.android.familybudgetapp.utilities.CommonStringValidations;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

public class Family
{
    private final long id;
    private static long nextId = 1;
    private String familyName;
    private long savings = 0;
    private long yearlySavings = 0;
    private MonthlySurplus currentSurplus;
    private Map<Long, User> members = new HashMap<>();
    private Map<String, CashFlowCategory> cashFlowCategories = new HashMap<>();
    private Map<YearMonth, MonthlySurplus> monthlySurpluses = new HashMap<>();


    public Family(String name)
    {
        setName(name);
        this.id = nextId++;
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

    public Map<Long, User> getMembers()
    {
        return new HashMap<>(members);
    }

    public Map<YearMonth, MonthlySurplus> getMonthlySurpluses()
    {
        return new HashMap<>(monthlySurpluses);
    }

    public MonthlySurplus getCurrentSurplus()
    {
        return currentSurplus;
    }

    public Map<String, CashFlowCategory> getCashFlowCategories()
    {
        return new HashMap<>(cashFlowCategories);
    }

    public void setName(String familyName)
    {
        if (familyName == null)
        {
            throw new IllegalArgumentException("Name shouldn't be null");
        }
        else if (!CommonStringValidations.isAlphanumericWithSpaces(familyName))
        {
            throw new IllegalArgumentException(CommonStringValidations.INVALID_ALPHANUMERICAL);
        }
        this.familyName = familyName;
    }

    public void addToSavings(int amount)
    {
        if (!canAddToSavings(amount))
        {
            throw new IllegalArgumentException("Amount should not be negative");
        }
        savings += amount;
        yearlySavings += amount;
    }

    public boolean canAddToSavings(int amount)
    {
        return amount >= 0;
    }

    public void removeFromSavings(int amount)
    {
        if (!canRemoveFromSavings(amount))
        {
            throw new IllegalArgumentException("Amount is invalid. Amount is negative or savings amount is insufficient");
        }
        savings -= amount;
        yearlySavings -= amount;
    }

    public boolean canRemoveFromSavings(int amount)
    {
        return amount >= 0 && savings - amount >= 0;
    }

    public void addMember(User user)
    {
        if (user == null)
        {
            throw new IllegalArgumentException("User shouldn't be null");
        }
        else if (!validateMember(user))
        {
            throw new IllegalArgumentException(String.format("User %s already exists.", user.getName()));
        }
        members.put(user.getID(), user);
    }

    public void removeMember(User user)
    {
        if (user == null)
        {
            throw new IllegalArgumentException("User shouldn't be null");
        }
        members.remove(user.getID());
    }

    public boolean validateMember(User user)
    {
        return !members.containsKey(user.getID());
    }

    public void addSurplus(MonthlySurplus surplus)
    {
        if (surplus == null)
        {
            throw new IllegalArgumentException("Surplus shouldn't be null");
        }
        else if (!validateSurplus(surplus))
        {
            throw new IllegalArgumentException("Surplus already exists");
        }
        currentSurplus = surplus;
        monthlySurpluses.put(YearMonth.of(surplus.getDate().getYear(), surplus.getDate().getMonth()), surplus);
    }


    public boolean validateSurplus(MonthlySurplus surplus)
    {
        return !monthlySurpluses.containsKey(YearMonth.of(surplus.getDate().getYear(), surplus.getDate().getMonth()));
    }

    public void addCashFlowCategory(CashFlowCategory category)
    {
        if (category == null)
        {
            throw new IllegalArgumentException("Category shouldn't be null");
        }
        else if (!validateCashFlowCategory(category))
        {
            throw new IllegalArgumentException("There is already a category with this name.");
        }
        cashFlowCategories.put(category.getName(), category);
    }

    /**
     * @param category cate
     */
    public boolean validateCashFlowCategory(CashFlowCategory category)
    {
        return !cashFlowCategories.containsKey(category.getName());
    }

    public void removeCashFlowCategory(CashFlowCategory category)
    {
        if (category == null)
        {
            throw new IllegalArgumentException("Category shouldn't be null");
        }
        cashFlowCategories.remove(category.getName(), category);
    }

    public void resetYearSavings()
    {
        yearlySavings = 0;
    }

    @Override
    public boolean equals(Object other)
    {
        if (this == other)
        {
            return true;
        }
        if (other == null || getClass() != other.getClass())
        {
            return false;
        }
        Family family = (Family) other;
        return id == family.id;
    }

    @Override
    public int hashCode()
    {
        return Long.hashCode(id);
    }
}
