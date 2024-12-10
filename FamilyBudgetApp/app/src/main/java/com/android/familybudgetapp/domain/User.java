package com.android.familybudgetapp.domain;

import java.util.ArrayList;
import java.util.List;

public class User {

    // Static variable to track the next user ID
    private static long nextId = 1;

    private final long id; // read-only
    private FamPos familyPosition;
    private String name;
    private String username;
    private String password;
    private Family family;
    private List<CashFlow> cashFlows;

    public User(String name, String username, String password, FamPos familyPosition, Family family) {
        this.id = nextId++;
        setName(name);
        setUsername(username);
        setPassword(password);
        setFamilyPosition(familyPosition);
        setFamily(family);
        this.cashFlows = new ArrayList<>();
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
        if (!Utilities.isUsernameValid(username)) {
            throw new IllegalArgumentException("Name should only consist of: Numbers, letters and underscores");
        }
        this.username = username;
    }

    public void setPassword(String password) {
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null.");
        }
        this.password = password;
    }

    public void setName(String name) {
        if (!validateName(name)) {
            System.err.println("Invalid name value: " + name);
            throw new IllegalArgumentException("Name is invalid.");
        }
        this.name = name;
    }

    private boolean validateName(String name) {
        return (name!=null) && (Utilities.isAlphanumericWithSpaces(name));
    }

    public void addCashFlow(CashFlow cashflow) {
        if (!validateAddCashFlow(cashflow)) {
            System.err.println("Invalid cashflow value: " + cashflow);
            throw new IllegalArgumentException("Cashflow is invalid.");
        }
        this.cashFlows.add(cashflow);
    }

    private boolean validateAddCashFlow(CashFlow cashflow) {
        return (cashflow != null) && (cashflow.getAmount() > 0);
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
}
