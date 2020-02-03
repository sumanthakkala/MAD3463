package com.madt.mad3463;

import java.util.HashMap;

public class MADT_Bank_DB {
    public HashMap<Long, HashMap<String, Account>> userAndHisAccountsObject;
    public MADT_Bank_DB(){
        userAndHisAccountsObject = new HashMap<Long, HashMap<String, Account>>();
    }
    void setUserAndHisAccountsObject(HashMap<Long, HashMap<String, Account>> object){
        userAndHisAccountsObject = object;
    }
    HashMap<Long, HashMap<String, Account>> getUserAndHisAccountsObject(){
        return userAndHisAccountsObject;
    }
}