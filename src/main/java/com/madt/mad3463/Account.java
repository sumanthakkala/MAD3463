package com.madt.mad3463;

public class Account {
    protected long UserID;
    protected String AccountHolderName;
    protected String Phone;
    protected String Email;
    protected String Occupation;
    protected String AccountType;
    protected double AvailableFunds;
    protected long AccountNumber;
    protected double AccountMonthlyFee;
    protected int TransactionsLimitPerMonth; //-1 is unlimited
    void setBalance(double balance){
        AvailableFunds = balance;
    }
    void setAccountType(String type){
        AccountType = type;
    }
    void depositAmount(long amount){
        AvailableFunds += amount;
    }
    double getBalance(){
        return AvailableFunds;
    }
    void drawMoney(double amount){
        AvailableFunds -= amount;
    }
    double getAccountMonthlyFee(){
        return AccountMonthlyFee;
    }
    void setAccountMonthlyFee(double amount){
        AccountMonthlyFee = amount;
    }
}
