package com.madt.mad3463;
import com.fasterxml.jackson.annotation.*;


public class SavingsAccount extends Account {
    public double InterestRate;
    public double OverageChargePerTransaction;
    double getInterestRate(){
        return InterestRate;
    }
    void setInterestRate(double percentage){
        InterestRate = percentage;
    }
    double getOverageCharges(){
        return OverageChargePerTransaction;
    }
    void setOverageChargePerTransaction(double charge){
        OverageChargePerTransaction = charge;
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
                + "\nTransaction Limit: " + super.getTransactionsLimitPerMonth()
                + "\nAccount Monthly Fee: " + super.getAccountMonthlyFee()
                + "\nInterest Rate: " + getInterestRate()
                + "\nOverage Charge Per Transaction: " + getOverageCharges();
    }
}
