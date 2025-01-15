package com.android.familybudgetapp.domain;

import com.android.familybudgetapp.utilities.CommonStringValidations;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a {@link MoneyBox} which helps in saving money for a specific {@code String} reason
 * and {@code int} target amount. Supports tracking current {@code int} amount, saving a
 * {@code List< }{@link Allowance}{@code >}.
 */
public class MoneyBox {
    private String reason;
    private int target;
    private int currentAmount;
    private List<Allowance> allowances = new ArrayList<>();

    public MoneyBox(String reason, int target)
    {
        setReason(reason);
        setTarget(target);
    }

    /**
     * Retrieves the reason associated with this MoneyBox.
     *
     * @return a string representing the reason for saving money in this MoneyBox.
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets the reason for saving money in this {@link MoneyBox}.
     * The reason must not be null and must only contain alphanumeric characters
     * and spaces.
     *
     * @param reason the reason for saving money. Must be a non-null string
     *               containing only alphanumeric characters and spaces.
     * @throws IllegalArgumentException if the reason is null or contains invalid characters.
     */
    public void setReason(String reason) {
        if(reason == null)
            throw new IllegalArgumentException("Reason shouldn't be null");
        else if (!CommonStringValidations.isAlphanumericWithSpaces(reason))
            throw new IllegalArgumentException(CommonStringValidations.INVALID_ALPHANUMERICAL);
        this.reason = reason;
    }

    /**
     * Retrieves the target saving amount defined for this {@link MoneyBox}.
     *
     * @return an integer representing the target amount to be saved.
     */
    public int getTarget() {
        return target;
    }

    /**
     * Sets the target saving amount for this {@link MoneyBox}. The target must be a positive integer.
     *
     * @param target the target amount to be saved. Must be a positive integer.
     * @throws IllegalArgumentException if the target is not a positive integer.
     */
    public void setTarget(int target) {
        if (!validateTarget(target))
            throw new IllegalArgumentException("Target should be a positive number");
        this.target = target;
    }

    /**
     * Validates the target amount to ensure it is a positive integer.
     *
     * @param target the target amount to be validated.
     * @return true if the target is greater than 0, false otherwise.
     */
    public static boolean validateTarget(int target)
    {
        return target > 0;
    }

    /**
     * Retrieves the current amount saved in this MoneyBox.
     *
     * @return the current amount as an integer.
     */
    public int getCurrentAmount() {
        return currentAmount;
    }

    /**
     * Adds an {@link Allowance} to the {@link MoneyBox}. The operation will fail if the
     * {@link Allowance} amount is zero or if adding the {@link Allowance} exceeds the target amount
     * of the {@link MoneyBox}.
     *
     * @param {@link Alowance} the {@link Allowance} to be added.
     * @return true if the {@link Allowance} was successfully added to the {@link MoneyBox}.
     * @throws IllegalArgumentException if the {@link Allowance} amount is zero or if adding
     *                                  the {@link Allowance} exceeds the target amount.
     */
    public boolean addMoney(Allowance allowance) {
        if (!validateAddMoney(allowance))
            throw new IllegalArgumentException("Allowance's amount should not be 0 or MoneyBox target should not be exceeded");
        currentAmount += allowance.getAmount();
        allowances.add(allowance);
        return true;
    }

    /**
     * Validates whether the provided {@link Allowance} can be added to the {@link MoneyBox}
     * without exceeding the target amount and ensuring the {@link Allowance} has a non-zero amount.
     *
     * @param allowance the {@link Allowance} to be validated. Must have a non-zero amount.
     * @return true if the {@link Allowance} can be added to the {@link MoneyBox} without exceeding
     *         the target amount, false otherwise.
     */
    public boolean validateAddMoney(Allowance allowance)
    {
        return allowance.getAmount() != 0 && currentAmount + allowance.getAmount() <= target;
    }

    /**
     * Retrieves the {@code List} of {@link Allowance}s currently stored in the {@link MoneyBox}.
     *
     * @return a {@code List} of {@link Allowance} objects representing the {@link Allowance}s in the {@link MoneyBox}.
     */
    public List<Allowance> getAllowances()
    {
        return new ArrayList<>(allowances);
    }

    /**
     * Checks if the current saved amount has reached the target amount.
     *
     * @return true if the current amount equals the target amount; false otherwise.
     */
    public boolean isTargetReached()
    {
        return currentAmount == target;
    }

}
