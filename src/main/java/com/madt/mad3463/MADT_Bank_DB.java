package com.madt.mad3463;

import java.util.HashMap;

public class MADT_Bank_DB {
    public HashMap<String, HashMap<String, Account>> userAndHisAccountsObject;
    public MADT_Bank_DB(){
        userAndHisAccountsObject = new HashMap<String, HashMap<String, Account>>();
    }
    void setUserAndHisAccountsObject(HashMap<String, HashMap<String, Account>> object){
        userAndHisAccountsObject = object;
    }
    HashMap<String, HashMap<String, Account>> getUserAndHisAccountsObject(){
        return userAndHisAccountsObject;
    }
}