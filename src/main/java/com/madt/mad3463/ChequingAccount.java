package com.madt.mad3463;
import com.fasterxml.jackson.annotation.*;

public class ChequingAccount extends Account {
    public double DailyATMWithdrawLimit;
    public double DailyDebitPurchaseLimit;

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
                + "\nATM Withdraw Limit Per Day: " + getDailyATMWithdrawLimit()
                + "\nDaily Debit Purchase Limit: " + getDailyDebitPurchaseLimit();
    }
}
