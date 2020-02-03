package com.madt.mad3463;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value=ChequingAccount.class, name = "ChequingAccount"),
        @JsonSubTypes.Type(value=SavingsAccount.class, name = "SavingsAccount"),
        @JsonSubTypes.Type(value=CreditAccount.class, name = "CreditAccount")
})

public class Account {
    public long UserID;
    public String AccountHolderName;
    public String Phone;
    public String Email;
    public String Occupation;
    public String AccountType;
    public double AvailableFunds;
    public long AccountNumber;
    public double AccountMonthlyFee;
    public int TransactionsLimitPerMonth; //-1 is unlimited

    void setUserID(long id){
        UserID = id;
    }
    long getUserID(){
        return UserID;
    }

    String getAccountHolderName(){
        return AccountHolderName;
    }
    void setAccountHolderName(String name){
        AccountHolderName = name;
    }

    void setPhone(String phone){
        Phone = phone;
    }
    String getPhone(){
        return Phone;
    }

    void setEmail(String email){
        Phone = email;
    }
    String getEmail(){
        return Email;
    }

    void setOccupation(String occupation){
        Phone = occupation;
    }
    String getOccupation(){
        return Occupation;
    }

    void setAccountType(String type){
        AccountType = type;
    }
    String getAccountType(){
        return AccountType;
    }

    void setAvailableFunds(double balance){
        AvailableFunds = balance;
    }
    double getAvailableFunds(){
        return AvailableFunds;
    }

    void setAccountNumber(long accountNumber){
        AccountNumber = accountNumber;
    }
    long getAccountNumber(){
        return AccountNumber;
    }

    double getAccountMonthlyFee(){
        return AccountMonthlyFee;
    }
    void setAccountMonthlyFee(double amount){
        AccountMonthlyFee = amount;
    }

    void setTransactionsLimitPerMonth(int transactionsLimitPerMonth){
        TransactionsLimitPerMonth = transactionsLimitPerMonth;
    }
    int getTransactionsLimitPerMonth(){
        return TransactionsLimitPerMonth;
    }

    void depositAmount(long amount){
        AvailableFunds += amount;
    }

    void drawMoney(double amount){
        AvailableFunds -= amount;
    }


}
