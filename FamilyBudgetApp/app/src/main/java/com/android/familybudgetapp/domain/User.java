package com.android.familybudgetapp.domain;

import com.android.familybudgetapp.utilities.CommonStringValidations;
import com.android.familybudgetapp.utilities.Quadruples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class User {

    // Static variable to track the next user ID
    private static long nextId = 1;

    private final long id; // read-only
    private HashMap<String, MoneyBox> moneyBoxes = new HashMap<>();
    private FamPos familyPosition;
    private String name;
    private String username;
    private String password;
    private Family family;
    private List<CashFlow> cashFlows = new ArrayList<>();

    public User(String name, String username, String password, FamPos familyPosition, Family family) {
        this.id = nextId++;
        setName(name);
        setUsername(username);
        setPassword(password);
        setFamilyPosition(familyPosition);
        setFamily(family);
    }

    // Getters
    public long getID() {
        return id;
    }

    public FamPos getFamilyPosition() {
        return familyPosition;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public List<CashFlow> getCashFlows() {
        return new ArrayList<>(cashFlows);
    }

    public Family getFamily() {
        return family;
    }

    // Setters
    public void setFamilyPosition(FamPos position) {
        if (position == null) {
            throw new IllegalArgumentException("Family Position cannot be null.");
        }
        this.familyPosition = position;
    }

    public void setFamily(Family family) {
        if (family == null) {
            throw new IllegalArgumentException("Family cannot be null.");
        }
        this.family = family;
    }

    public void setUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null.");
        }
        if (!CommonStringValidations.isUsernameValid(username)) {
            throw new IllegalArgumentException("Name should only consist of: Numbers, letters and underscores");
        }
        this.username = username;
    }

    public void setPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null.");
        }

        if (!CommonStringValidations.isPasswordValid(password)) {
            throw new IllegalArgumentException("Password should only consist of:At least 4 numbers or letters");
        }
        this.password = password;
    }

    public void setName(String name) {
        if(name==null)
        {
            throw new IllegalArgumentException("Name shouldn't be null");
        }
        else if (!CommonStringValidations.isAlphanumericWithSpaces(name))
        {
            throw new IllegalArgumentException("Name should only consist of: Numbers, letters and spaces.");
        }
        this.name = name;
    }

    public void addCashFlow(CashFlow cashflow) {
        if (cashflow == null)
            throw new IllegalArgumentException("CashFlow is null.");
        this.cashFlows.add(cashflow);
    }

    public HashMap<String, MoneyBox> getMoneyBoxes() {
        return new HashMap<>(moneyBoxes);
    }

    public void addMoneyBox(MoneyBox moneyBox){
        if (!validateMoneyBox(moneyBox)) {
            throw new IllegalArgumentException("MoneyBox is null or already exists.");
        }
        moneyBoxes.put(moneyBox.getReason(), moneyBox);
    }

    public boolean validateMoneyBox(MoneyBox moneybox) {
        return moneybox != null && !moneyBoxes.containsKey(moneybox.getReason());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", familyPosition=" + familyPosition +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", family=" + family +
                ", cashFlows=" + cashFlows +
                '}';
    }

    /**
     * Used ONLY for Junit tests
     * @return nextID
     */
    public static Long getNextID()
    {
        return nextId;
    }
}
