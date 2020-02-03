package com.madt.mad3463;

public class ChequingAccount extends Account {
    protected double DailyATMWithdrawLimit;
    protected double DailyDebitPurchaseLimit;

    void setDailyATMWithdrawLimit(double limit){
        DailyATMWithdrawLimit = limit;
    }
    double getDailyATMWithdrawLimit(){
        return DailyATMWithdrawLimit;
    }
    void setDailyDebitPurchaseLimit(double limit){
        DailyDebitPurchaseLimit = limit;
    }
    double getDailyDebitPurchaseLimit(){
        return DailyDebitPurchaseLimit;
    }
}
