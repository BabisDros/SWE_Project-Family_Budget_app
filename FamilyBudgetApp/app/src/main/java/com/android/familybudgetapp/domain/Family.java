package com.android.familybudgetapp.domain;

import com.android.familybudgetapp.utilities.CommonStringValidations;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing a family with savings, members, and cash flow categories and monthly Surpluses.
 */
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


    /**
     * Creates a new family object with the provided name.
     *
     * @param name The name of the family
     */
    public Family(String name)
    {
        setName(name);
        this.id = nextId++;
    }

    /**
     * Returns the ID of the family.
     *
     * @return The ID of the family
     */
    public long getID()
    {
        return id;
    }

    /**
     * Returns the name of the family.
     *
     * @return The name of the family
     */
    public String getName()
    {
        return familyName;
    }

    /**
     * Returns the savings of the family.
     *
     * @return The savings
     */
    public long getSavings()
    {
        return savings;
    }

    /**
     * Returns the yearly savings of the family.
     *
     * @return The yearly savings
     */
    public long getYearlySavings()
    {
        return yearlySavings;
    }

    /**
     * Returns a map of family members with their IDs as keys.
     *
     * @return A map of members
     */
    public Map<Long, User> getMembers()
    {
        return new HashMap<>(members);
    }

    /**
     * Returns a map of monthly surpluses for the family with YearMonth as keys.
     *
     * @return A map of monthly surpluses
     */
    public Map<YearMonth, MonthlySurplus> getMonthlySurpluses()
    {
        return new HashMap<>(monthlySurpluses);
    }

    /**
     * Returns the current MonthlySurplus.
     *
     * @return The current surplus object.
     */
    public MonthlySurplus getCurrentSurplus()
    {
        return currentSurplus;
    }

    /**
     * Returns a map of cash flow categories with cashflow category name as keys.
     *
     * @return A map of cash flow categories
     */
    public Map<String, CashFlowCategory> getCashFlowCategories()
    {
        return new HashMap<>(cashFlowCategories);
    }

    /**
     * Sets the name of the family.
     *
     * @param familyName The name of the family
     * @throws IllegalArgumentException if the name is null or is not alphanumeric With Spaces
     */
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

    /**
     * Adds an amount to the family's savings.
     *
     * @param amount The amount to add
     * @throws IllegalArgumentException if the amount is negative
     */
    public void addToSavings(int amount)
    {
        if (!canAddToSavings(amount))
        {
            throw new IllegalArgumentException("Amount should not be negative");
        }
        savings += amount;
        yearlySavings += amount;
    }

    /**
     * Checks if an amount can be added to the savings.
     *
     * @param amount The amount to check
     * @return true if the amount is positive or zero, false otherwise
     */
    public boolean canAddToSavings(int amount)
    {
        return amount >= 0;
    }

    /**
     * Removes an amount from the family's savings.
     *
     * @param amount The amount to remove
     * @throws IllegalArgumentException if the amount is negative or insufficient savings
     */
    public void removeFromSavings(int amount)
    {
        if (!canRemoveFromSavings(amount))
        {
            throw new IllegalArgumentException("Amount is invalid. Amount is negative or savings amount is insufficient");
        }
        savings -= amount;
        yearlySavings -= amount;
    }

    /**
     * Checks if an amount can be removed from the savings.
     *
     * @param amount The amount to check
     * @return true if the amount is positive and less than savings, false otherwise
     */
    public boolean canRemoveFromSavings(int amount)
    {
        return amount >= 0 && savings - amount >= 0;
    }

    /**
     * Adds a member to the family.
     *
     * @param user The user to add
     * @throws IllegalArgumentException if the user is null or already a member with same ID.
     */
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

    /**
     * Removes a member from the family.
     *
     * @param user The user to remove
     * @throws IllegalArgumentException if the user is null
     */
    public void removeMember(User user)
    {
        if (user == null)
        {
            throw new IllegalArgumentException("User shouldn't be null");
        }
        members.remove(user.getID());
    }

    /**
     * Validates if a user can be added as a member.
     *
     * @param user The user to validate
     * @return true if the user is not already a member with same ID, false otherwise.
     */
    public boolean validateMember(User user)
    {
        return !members.containsKey(user.getID());
    }

    /**
     * Adds a surplus for the family.
     *
     * @param surplus The surplus to add
     * @throws IllegalArgumentException if the surplus is null or already exists
     */
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

    /**
     * Validates if a surplus can be added.
     *
     * @param surplus The surplus to validate
     * @return true if the surplus doesn't have the same date in YearMonth format, false otherwise
     */
    public boolean validateSurplus(MonthlySurplus surplus)
    {
        return !monthlySurpluses.containsKey(YearMonth.of(surplus.getDate().getYear(), surplus.getDate().getMonth()));
    }

    /**
     * Adds a cash flow category to the family.
     *
     * @param category The category to add
     * @throws IllegalArgumentException if the category is null or already exists with the same name.
     */
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
        cashFlowCategories.put(category.getName().toLowerCase(), category);
    }

    /**
     * Validates if a cash flow category can be added.
     *
     * @param category The category to validate
     * @return true if the category name is unique, false otherwise
     */
    public boolean validateCashFlowCategory(CashFlowCategory category)
    {
        return !cashFlowCategories.containsKey(category.getName().toLowerCase());
    }

    /**
     * Removes a cash flow category from the family.
     *
     * @param category The category to remove
     * @throws IllegalArgumentException if the category is null
     */
    public void removeCashFlowCategory(CashFlowCategory category)
    {
        if (category == null)
        {
            throw new IllegalArgumentException("Category shouldn't be null");
        }
        cashFlowCategories.remove(category.getName().toLowerCase(), category);
    }

    /**
     * Resets the yearly savings of the family.
     */
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
