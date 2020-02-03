package com.madt.mad3463;

public class SavingsAccount extends Account {
    protected double InterestRate;
    protected double OverageChargePerTransaction;
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
}
