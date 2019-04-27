package com.capstone.kcamp.cougarbiteapplication.Model;

public class CreditCard {
    String number;
    String accountID;
    String credit;
    String savings;
    String name;
    String cvv;

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getSavings() {
        return savings;
    }

    public void setSavings(String savings) {
        this.savings = savings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public CreditCard(String accountID, String credit, String savings, String name, String cvv) {
        this.accountID = accountID;
        this.credit = credit;
        this.savings = savings;
        this.name = name;
        this.cvv = cvv;
    }
    public CreditCard() {}
}
