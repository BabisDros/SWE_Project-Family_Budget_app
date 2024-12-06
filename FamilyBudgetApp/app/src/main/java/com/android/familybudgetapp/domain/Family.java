package com.android.familybudgetapp.domain;

public class Family {
    // Placeholder
    String familyName;

    public Family(String familyName) {
        this.familyName = familyName;
    }

    @Override
    public String toString() {
        return "Family{" +
                "familyName='" + familyName + '\'' +
                '}';
    }
}
