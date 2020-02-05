package com.madt.mad3463;

import java.lang.Object;
import com.fasterxml.jackson.annotation.*;

public class CreditAccount extends Account {
    public double CreditLimit;
    public double AmountDue;
    public double MinimumPayment;
    public double LastPaymentPosted;


    void setCreditLimit(double limit){
        CreditLimit = limit;
    }
    double getCreditLimit(){
        return CreditLimit;
    }
    void setAmountDue(double creditTaken){
        AmountDue += creditTaken;
    }
    double getAmountDue(){
        return AmountDue;
    }
    void adjustAvailableFunds(double creditTaken){
        AvailableFunds -= creditTaken;
    }
    void setMinimumPayment(double minimumPayment){
        MinimumPayment = minimumPayment;
    }
    double getMinimumPayment(){
        return MinimumPayment;
    }
    void setLastPaymentPosted(double lastPaymentPosted){
        LastPaymentPosted = lastPaymentPosted;
    }
    double getLastPaymentPosted(){
        return LastPaymentPosted;
    }
    String toStringWithAttr(){
        return "User ID: " + super.getUserID()
                + "\nName: " + super.getAccountHolderName()
                + "\nPhone: " + super.getPhone()
                + "\nEmail: " + super.getEmail()
                + "\nOccupation: " + super.getOccupation()
                + "\nAccount Type: " + super.getAccountType()
                + "\nAvailable Funds: " + super.getAvailableFunds()
                + "\nAccount Number: " + super.getAccountNumber()
                + "\nTransaction Limit: Unlimited"
                + "\nAccount Monthly Fee: " + super.getAccountMonthlyFee()
                + "\nCredit Limit: " + getCreditLimit();
    }

}
