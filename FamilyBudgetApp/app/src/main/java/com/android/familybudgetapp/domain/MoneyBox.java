package com.android.familybudgetapp.domain;

import java.util.ArrayList;
import java.util.List;

public class MoneyBox {
    private String reason;
    private int target;
    private int currentAmount;
    private List<Allowance> allowances = new ArrayList<>();

    MoneyBox(String reason, int target)
    {
        setReason(reason);
        setTarget(target);
    }
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        if(reason == null)
            throw new IllegalArgumentException("Reason shouldn't be null");
        else if (!Utilities.isAlphanumericWithSpaces(reason))
            throw new IllegalArgumentException("Reason should be consisted only by: Numbers, letters and underscores");
        this.reason = reason;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        if (!validateTarget(target))
            throw new IllegalArgumentException("Target should be a positive number");
        this.target = target;
    }

    public static boolean validateTarget(int target)
    {
        return target > 0;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public boolean addMoney(Allowance allowance) {
        if (!validateAddMoney(allowance))
            throw new IllegalArgumentException("Allowance's amount should not be 0 or MoneyBox target should not be exceeded");
        currentAmount += allowance.getAmount();
        allowances.add(allowance);
        return true;
    }

    public boolean validateAddMoney(Allowance allowance)
    {
        return allowance.getAmount() != 0 && currentAmount + allowance.getAmount() <= target;
    }

    public List<Allowance> getAllowances()
    {
        return new ArrayList<>(allowances);
    }
    public boolean isTargetReached()
    {
        return currentAmount == target;
    }

}
