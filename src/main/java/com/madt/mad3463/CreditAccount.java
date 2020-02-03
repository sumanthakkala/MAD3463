package com.madt.mad3463;

public class CreditAccount extends Account {
    protected double CreditLimit;
    protected double AmountDue;
    protected double MinimumPayment;
    protected double LastPaymentPosted;

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
}
