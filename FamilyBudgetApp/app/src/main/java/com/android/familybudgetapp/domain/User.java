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
        this.name = name;
        this.username = username;
        this.password = password;
        this.familyPosition = familyPosition;
        this.family = family;
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
        this.familyPosition = position;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean addCashFlow(CashFlow cashFlow) {
        return this.cashFlows.add(cashFlow);
    }

    public boolean verifyCredentials(String user, String pass) {
        return this.username.equals(user) && this.password.equals(pass);
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
