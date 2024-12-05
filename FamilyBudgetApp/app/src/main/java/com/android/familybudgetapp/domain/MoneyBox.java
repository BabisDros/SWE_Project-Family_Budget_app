package com.android.familybudgetapp.domain;

import java.util.ArrayList;
import java.util.List;

public class MoneyBox {
    private String reason;
    private int target;
    private int currentAmount;
    private List<Allowance> allowances;

    MoneyBox()
    {
        allowances = new ArrayList<>();
    }

    MoneyBox(String reason, int target)
    {
        this();
        this.reason = reason;
        this.target = target;
    }
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public boolean addMoney(Allowance allowance) {
        if (allowance.getAmount() == 0)
            return false;
        if (currentAmount + allowance.getAmount() > target)
            return false;
        currentAmount += allowance.getAmount();
        allowances.add(allowance);
        return true;
    }

    public List<Allowance> getAllowances()
    {
        return allowances;
    }
    public boolean isTargetReached()
    {
        return currentAmount == target;
    }

}
