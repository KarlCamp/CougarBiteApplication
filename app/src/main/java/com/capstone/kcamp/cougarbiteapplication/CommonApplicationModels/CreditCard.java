package com.capstone.kcamp.cougarbiteapplication.CommonApplicationModels;

/**
 * The CreditCard class models the credit card object model detailed in Firebase.
 * In other words, the class is reflective of the JSON object stored in the
 * database and properly parses object values by having identical parameter names.
 * Stores basic information regarding a credit card.
 *
 * @author Karl Camp
 * @version 1.0.0
 * @since 2019-05-04
 */
public class CreditCard {
    private String number; //stores credit card number
    private String accountID; //stores accountID
    private String credit; //stores credit card balance in credit (value used for payments)
    private String savings; //stores credit card balance in savings
    private String name; //stores credit card name
    private String cvv; //stores credit card CVV

    //generated appropriate setters and getters

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

    //generated appropriate constructor initializing all class variables

    public CreditCard(String accountID, String credit, String savings, String name, String cvv) {
        this.accountID = accountID;
        this.credit = credit;
        this.savings = savings;
        this.name = name;
        this.cvv = cvv;
    }

    //generated appropriate default constructor

    public CreditCard() {}
}