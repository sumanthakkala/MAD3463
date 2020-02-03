package com.madt.mad3463;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
	// write your code here

        File MADT_Bank_DB_TextFile = new File("textDB//MADT_Bank_DB.json");
        try {
            MADT_Bank_DB_TextFile.createNewFile();
        } catch (IOException e) {
            System.out.println(e);
        }
        systemPrompter();
    }
    public static void systemPrompter(){
        int ch;
        System.out.println("Hello, Wlcome to MADT Bank!");
        do{
            Scanner sc = new Scanner(System.in);
            System.out.println("1. Open an acoount \n2. Access your account \n3. Exit");
            System.out.println("Enter your choice: ");
            ch = sc.nextInt();
            switch (ch){
                case 1:
                    accountOpenningHandler();
                    break;
                case 2:
                    accountAccessesHandler();
                    break;
                case 3:
                    System.out.println("Thank you for choosing our bank! See you soon.");
                    break;
            }
        } while (ch != 3);
    }
    public static void accountOpenningHandler(){
        System.out.println("Do you have an existing account in MADT Bank? \n" +
                "Y/N ?: ");
        Scanner sc = new Scanner(System.in);
        String yn = sc.next();
        if(yn.equalsIgnoreCase("Y")){
            existingUserAccountsOpenner();
        }
        else if(yn.equalsIgnoreCase("N")){
            newUsersAccountsOppenner();
        }
        else {
            accountOpenningHandler();
            return;
        }
    }
    public static void existingUserAccountsOpenner(){
        System.out.println("Please enter your User ID: ");
        Scanner sc = new Scanner(System.in);
        long userID = sc.nextLong();
        //Fetch remaining accounts that this user did't open and show them to user and ask him to choose one.
        String accountType = "";
        if(createAccount(accountType, userID) == "OK"){

        }
        else {
            System.out.println("Sorry, please try again later.");
        }
    }
    public static void newUsersAccountsOppenner(){
        System.out.println("Which of the following account do you want to open? ");
        System.out.println("1. Chequing\n2. Savings\n3. Credit");
        System.out.println("Enter your choice: ");
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        //Account selectedAccount;
        int ch = sc.nextInt();
        System.out.println("Enter your name: ");
        String name = sc.next();
        System.out.println("Enter your phone: ");
        String phone = sc.next();
        System.out.println("Enter your email: ");
        String email = sc.next();
        System.out.println("Enter your occupation: ");
        String occupation = sc.next();
        switch (ch){
            case 1:
                ChequingAccount chequingAccount = new ChequingAccount();
                System.out.println("How much amount you want to deposit now?:");
                double chequingAmount = sc.nextDouble();
                chequingAccount.AccountHolderName = name;
                chequingAccount.Email = email;
                chequingAccount.Phone = phone;
                chequingAccount.Occupation = occupation;
                chequingAccount.AvailableFunds = chequingAmount;
                chequingAccount.AccountNumber = rand.nextInt(1000000);
                chequingAccount.AccountMonthlyFee = 4.5;
                chequingAccount.AccountType = AccountTypes.Chequing;
                chequingAccount.TransactionsLimitPerMonth = -1; //unlimited transactions per month (-1)
                chequingAccount.DailyATMWithdrawLimit = 3000;
                chequingAccount.DailyDebitPurchaseLimit = 1000;
                break;
            case 2:
                SavingsAccount savingsAccount = new SavingsAccount();
                System.out.println("How much amount you want to deposit now?:");
                double savingsAmount = sc.nextDouble();
                savingsAccount.AccountHolderName = name;
                savingsAccount.Email = email;
                savingsAccount.Phone = phone;
                savingsAccount.Occupation = occupation;
                savingsAccount.AvailableFunds = savingsAmount;
                savingsAccount.AccountNumber = rand.nextInt(1000000);
                savingsAccount.AccountMonthlyFee = 4.5;
                savingsAccount.AccountType = AccountTypes.Savings;
                savingsAccount.TransactionsLimitPerMonth = 10; //unlimited transactions per month (-1)
                savingsAccount.InterestRate = 3.25;
                savingsAccount.OverageChargePerTransaction = 3;
                break;
            case 3:
                CreditAccount creditAccount = new CreditAccount();
                System.out.println("Thank you for your interest in MADT Bank credit account. Your initial limit of credit given to you is CA$1000.");

                creditAccount.AccountHolderName = name;
                creditAccount.Email = email;
                creditAccount.Phone = phone;
                creditAccount.Occupation = occupation;
                creditAccount.AvailableFunds = 1000;
                creditAccount.AccountNumber = rand.nextInt(1000000);
                creditAccount.AccountMonthlyFee = 0;
                creditAccount.AccountType = AccountTypes.Credit;
                creditAccount.TransactionsLimitPerMonth = -1; //unlimited transactions per month (-1)
                creditAccount.CreditLimit = 1000;
                creditAccount.AmountDue = 0;
                creditAccount.MinimumPayment = 0;
                creditAccount.LastPaymentPosted = 0;
                break;
        }

    }
    public static String createAccount(Account filledAccountModelObject){
        Random rand = new Random();
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Account> accountsObjects = new HashMap<String, Account>();
        MADT_Bank_DB bankDB = new MADT_Bank_DB();
        filledAccountModelObject.UserID = rand.nextInt(100000);
        if (AccountTypes.Chequing.equals(filledAccountModelObject.AccountType)) {
            accountsObjects.put(AccountTypes.Chequing, filledAccountModelObject);
        }
        else if (AccountTypes.Savings.equals(filledAccountModelObject.AccountType)) {
            accountsObjects.put(AccountTypes.Savings, filledAccountModelObject);
        }
        else if (AccountTypes.Credit.equals(filledAccountModelObject.AccountType)) {
            accountsObjects.put(AccountTypes.Credit, filledAccountModelObject);
        }
        bankDB.userAndHisAccountsObject.put(filledAccountModelObject.UserID, accountsObjects);
        try {
            objectMapper.writeValue(new File("textDB//MADT_Bank_DB.json"), bankDB.userAndHisAccountsObject);
        } catch (IOException e) {
            System.out.println(e);
        }
        return "OK";
    }
    public static String createAccount(String type, long userID){
        //Fetch existing users DB
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            MADT_Bank_DB bankDB = objectMapper.readValue(new File("textDB//MADT_Bank_DB.json"), MADT_Bank_DB.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "OK";
    }
    public static void accountAccessesHandler(){

    }
}
