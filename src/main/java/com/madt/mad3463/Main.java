package com.madt.mad3463;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.*;

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
        System.out.println("Hello, Welcome to MADT Bank!");
        do{
            Scanner sc = new Scanner(System.in);
            System.out.println("\n-----------------------------------\n1. Open an acoount \n2. Access your account \n3. Exit");
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


    //Account openning handellers
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
            return;
        }
        else {
            accountOpenningHandler();
            return;
        }
    }
    public static void existingUserAccountsOpenner(){
        System.out.println("Please enter your User ID: ");
        Scanner sc = new Scanner(System.in);
        String userID = sc.nextLine();
        //Fetch remaining accounts that this user did't open and show them to user and ask him to choose one.
        ArrayList<String> Accounts = new ArrayList<String>();
        Accounts.add("Chequing");
        Accounts.add("Savings");
        Accounts.add("Credit");
        int ch;
        Random rand = new Random();
        //HashMap<String, HashMap<String, Account>> bankDB = getBankDB();
        MADT_Bank_DB bank_db = getBankDB();
        HashMap<String, Account> accountsObjects = new HashMap<String, Account>();

        accountsObjects = bank_db.userAndHisAccountsObject.get(userID);
        System.out.println("You already have the following accounts with us.");
        for (String i : accountsObjects.keySet()) {
            System.out.println("\u2022 " + i);
            Accounts.remove(i);
        }
        if(accountsObjects.size() == 3){
            return;
        }
        System.out.println("Which of the following account do you want to open? ");
        for (int i = 0; i < Accounts.size(); i++){
            System.out.println((i+1) + ". " + Accounts.get(i));
        }
        System.out.println("Enter your choice: ");
        ch = sc.nextInt();
        String accountType = Accounts.get(ch-1);
        if(createAccount(accountType, userID) == "OK"){
            return;
        }
        else {
            System.out.println("Sorry, please try again later.");
            return;
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
        if(createAccount(accountOpennerQuestionare(ch)) == "OK"){
            return;
        }
        else {
            System.out.println("Sorry, please try again later.");
            return;
        }
    }




    //Account Openners (New & Existing)
    public static String createAccount(Account filledAccountModelObject){
        Random rand = new Random();
        //HashMap<String, HashMap<String, Account>> bankDB = new HashMap<String, HashMap<String, Account>>();
        MADT_Bank_DB bankDB = new MADT_Bank_DB();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        //PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder().build();
        //objectMapper.activateDefaultTyping(ptv);
        HashMap<String, Account> accountsObjects = new HashMap<String, Account>();
        File dbJSONFile = new File("textDB//MADT_Bank_DB.json");
        if (dbJSONFile.length() != 0) {
            try {
                //bankDB = objectMapper.readValue(dbJSONFile, HashMap.class);
                bankDB = objectMapper.readValue(dbJSONFile, MADT_Bank_DB.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            //bankDB = new MADT_Bank_DB();
        }
        filledAccountModelObject.UserID = Long.toString(rand.nextInt(100000));
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
        //bankDB.put(filledAccountModelObject.UserID, accountsObjects);
        try {
            objectMapper.writeValue(new File("textDB//MADT_Bank_DB.json"), bankDB);
            System.out.println("Account created successfully! Your userID is "+filledAccountModelObject.UserID+". Please note this for your reference. " +
                    "You need this ID whenever you want to use our banking system. Thank you.");
            return "OK";
        } catch (IOException e) {
            System.out.println(e);
        }
        return "ERROR";
    }
    public static String createAccount(String type, String userID){
        //Fetch existing users DB
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        //PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder().build();
        //objectMapper.activateDefaultTyping(ptv);
        //HashMap<String, HashMap<String, Account>> bankDB = new HashMap<String, HashMap<String, Account>>();
        MADT_Bank_DB bankDB = new MADT_Bank_DB();
        Account filledAccountModelObject;
        try {
            //bankDB = objectMapper.readValue(new File("textDB//MADT_Bank_DB.json"), HashMap.class);
            bankDB = objectMapper.readValue(new File("textDB//MADT_Bank_DB.json"), MADT_Bank_DB.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(type == AccountTypes.Chequing)
            filledAccountModelObject = accountOpennerQuestionare(1);
        else if(type == AccountTypes.Savings)
            filledAccountModelObject = accountOpennerQuestionare(2);
        else
            filledAccountModelObject = accountOpennerQuestionare(3);
        //HashMap<String, Account> accountsObjects = bankDB.get(userID);
        HashMap<String, Account> accountsObjects = bankDB.userAndHisAccountsObject.get(userID);
        filledAccountModelObject.UserID = userID;
        if(accountsObjects.get(type) == null){
            if (AccountTypes.Chequing.equals(type)) {
                accountsObjects.put(AccountTypes.Chequing, filledAccountModelObject);
            }
            else if (AccountTypes.Savings.equals(type)) {
                accountsObjects.put(AccountTypes.Savings, filledAccountModelObject);
            }
            else if (AccountTypes.Credit.equals(type)) {
                accountsObjects.put(AccountTypes.Credit, filledAccountModelObject);
            }
        }
        //bankDB.replace(userID,accountsObjects);
        bankDB.userAndHisAccountsObject.replace(userID,accountsObjects);

        try {
            objectMapper.writeValue(new File("textDB//MADT_Bank_DB.json"), bankDB);
            System.out.println("Account created successfully! Your userID is "+filledAccountModelObject.UserID+". Please note this for your reference. " +
                    "You need this ID whenever you want to use our banking system. Thank you.");
            return "OK";
        } catch (IOException e) {
            System.out.println(e);
        }
        return "ERROR";
    }
    public static Account accountOpennerQuestionare(int ch){
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        System.out.println("Enter your name: ");
        String name = sc.nextLine();
        System.out.println("Enter your phone: ");
        String phone = sc.nextLine();
        System.out.println("Enter your email: ");
        String email = sc.nextLine();
        System.out.println("Enter your occupation: ");
        String occupation = sc.nextLine();
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
                return chequingAccount;
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
                return savingsAccount;
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
                return creditAccount;
        }
        return new Account();
    }



    public static void accountAccessesHandler(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your userID: ");
        String userID = sc.nextLine();
        System.out.println("Select an operation you want to perform.");
        System.out.println("1. View my profile \n2. Edit my profile \n3. Display my balances \n4. Deposit money " +
                "\n5. Withdraw money \n6. Trasfer money between accounts within the bank \n7. Pay utility bills");
        int ch = sc.nextInt();
        switch (ch){
            case 1:
                viewProfile(userID);
                break;
            case 2:
                editProfile(userID);
                break;
            case 3:
                displayBalanceHandler(userID);
                break;
            case 4:
                depositMoneyHandler(userID);
                break;
            case 5:
                withdrawMoneyHandler(userID);
                break;
            case 6:
                transferFundsHandler(userID);
                break;
            case 7:
                payUtilityBillsHandler(userID);
                break;
        }
    }
    public static void viewProfile(String userID){
        Scanner sc = new Scanner(System.in);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        MADT_Bank_DB bank_db = getBankDB();
        Account selectedAccountObject = filterSelectedAccount(userID, bank_db);
        String selectedAccountType = (selectedAccountObject).AccountType;
        if (AccountTypes.Chequing.equals(selectedAccountType)) {
            ChequingAccount selectedAccount = (ChequingAccount) selectedAccountObject;
            System.out.println(selectedAccount.toStringWithAttr());
        }
        else if (AccountTypes.Savings.equals(selectedAccountType)) {
            SavingsAccount selectedAccount = (SavingsAccount) selectedAccountObject;
            System.out.println(selectedAccount.toStringWithAttr());
        }
        else if (AccountTypes.Credit.equals(selectedAccountType)) {
            CreditAccount selectedAccount = (CreditAccount) selectedAccountObject;
            System.out.println(selectedAccount.toStringWithAttr());
        }
    }
    public static void editProfile(String userID){
        Scanner sc = new Scanner(System.in);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        MADT_Bank_DB bank_db = getBankDB();
        Account selectedAccountObject = filterSelectedAccount(userID, bank_db);
        System.out.println("Which of the following details you want to update?");
        System.out.println("1. Phone \n2. Email \n3. Occupation");
        int utilCh = sc.nextInt();
        switch (utilCh){
            case 1:
                System.out.println("Enter your phone:");
                String newPhone = sc.next();
                selectedAccountObject.setPhone(newPhone);
                System.out.println("Phone changed successfully!" +
                        "Thank you for using our banking services.");
                break;
            case 2:
                System.out.println("Enter your email:");
                String newEmail = sc.next();
                selectedAccountObject.setEmail(newEmail);
                System.out.println("Email changed successfully!" +
                        "Thank you for using our banking services.");
                break;
            case 3:
                System.out.println("Enter your occupation:");
                String newOccupation = sc.next();
                selectedAccountObject.setOccupation(newOccupation);
                System.out.println("Occupation changed successfully!" +
                        "Thank you for using our banking services.");
                break;
        }
        bank_db.userAndHisAccountsObject.get(userID).replace(selectedAccountObject.AccountType, selectedAccountObject);
        try {
            objectMapper.writeValue(new File("textDB//MADT_Bank_DB.json"), bank_db);

        } catch (IOException e) {
            System.out.println(e);
        }
        return;
    }
    public static void displayBalanceHandler(String userID){
        Scanner sc = new Scanner(System.in);
        ObjectMapper mapper = new ObjectMapper();
        MADT_Bank_DB bank_db = getBankDB();
        Account selectedAccountObject = filterSelectedAccount(userID, bank_db);
        System.out.println("Your Available balance is: " +selectedAccountObject.AvailableFunds + ". " +
                "Thank you for using our banking services.");
        return;
    }
    public static void depositMoneyHandler(String userID){
        Scanner sc = new Scanner(System.in);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        MADT_Bank_DB bank_db = getBankDB();
        Account selectedAccountObject = filterSelectedAccount(userID, bank_db);
        System.out.println("How much amount you want to deposit?");
        int amount = sc.nextInt();
        selectedAccountObject.depositAmount(amount);
        bank_db.userAndHisAccountsObject.get(userID).replace(selectedAccountObject.AccountType, selectedAccountObject);
        try {
            objectMapper.writeValue(new File("textDB//MADT_Bank_DB.json"), bank_db);
            System.out.println("Amount deposited successfully! Your updated balance is " + selectedAccountObject.AvailableFunds + ". " +
                    "Thank you for using our banking services.");

        } catch (IOException e) {
            System.out.println(e);
        }
        return;
    }
    public static void withdrawMoneyHandler(String userID){
        Scanner sc = new Scanner(System.in);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        MADT_Bank_DB bank_db = getBankDB();
        Account selectedAccountObject = filterSelectedAccount(userID, bank_db);
        System.out.println("How much amount you want to withdraw?");
        int amount = sc.nextInt();
        selectedAccountObject.drawMoney(amount);
        bank_db.userAndHisAccountsObject.get(userID).replace(selectedAccountObject.AccountType, selectedAccountObject);
        try {
            objectMapper.writeValue(new File("textDB//MADT_Bank_DB.json"), bank_db);
            System.out.println("Amount withdrawed successfully! Your updated balance is " + selectedAccountObject.AvailableFunds + ". " +
                    "Thank you for using our banking services.");

        } catch (IOException e) {
            System.out.println(e);
        }
        return;
    }
    public static void transferFundsHandler(String userID){
        Scanner sc = new Scanner(System.in);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        MADT_Bank_DB bank_db = getBankDB();
        if(bank_db.userAndHisAccountsObject.get(userID).size() == 1){
            System.out.println("You dont have any other accounts registered with us. Please create one to transfer funds between accounts. " +
                    "Thank you for using our banking services.");
            return;
        }
        else {
            ArrayList<String> ExistingAccountTypes = new ArrayList<String>();
            Account selectedFromAccountObject = filterSelectedAccount(userID, bank_db);
            HashMap<String, Account> accountsObjects = bank_db.userAndHisAccountsObject.get(userID);
            System.out.println("To which account you want to transfer funds?");
            int index = 1;
            for (String i : accountsObjects.keySet()) {
                if(!(i.equalsIgnoreCase(selectedFromAccountObject.AccountType))){
                    System.out.println((index) + ". " + i);
                    ExistingAccountTypes.add(i);
                    index++;
                }

            }
            int ch = sc.nextInt();
            Account selectedToAccountObject = accountsObjects.get(ExistingAccountTypes.get(ch-1));
            System.out.println("Your current balance in " + selectedFromAccountObject.AccountType + " is " +selectedFromAccountObject.AvailableFunds +
                    ". Please enter how much amount you would like to transfer to your " +selectedToAccountObject.AccountType + "?");
            int transferAmount = sc.nextInt();
            selectedFromAccountObject.drawMoney(transferAmount);
            selectedToAccountObject.depositAmount(transferAmount);
            System.out.println("Transfer funds successfully. Your updated balance is as follows.");
            System.out.println(selectedFromAccountObject.AccountType + ": " +selectedFromAccountObject.AvailableFunds);
            System.out.println(selectedToAccountObject.AccountType + ": " +selectedToAccountObject.AvailableFunds);
            System.out.println("Thank you for using our banking service.");

            bank_db.userAndHisAccountsObject.get(userID).replace(selectedFromAccountObject.AccountType, selectedFromAccountObject);
            bank_db.userAndHisAccountsObject.get(userID).replace(selectedToAccountObject.AccountType, selectedToAccountObject);
            try {
                objectMapper.writeValue(new File("textDB//MADT_Bank_DB.json"), bank_db);

            } catch (IOException e) {
                System.out.println(e);
            }
            return;
        }

    }
    public static void payUtilityBillsHandler(String userID){
        Scanner sc = new Scanner(System.in);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        MADT_Bank_DB bank_db = getBankDB();
        Account selectedAccountObject = filterSelectedAccount(userID, bank_db);
        System.out.println("Which of the following utilities you want to pay for?");
        System.out.println("1. Hydro \n2. Mobile \n3. Credit Card");
        int utilCh = sc.nextInt();
        switch (utilCh){
            case 1:
                System.out.println("Enter your Hydro account number: ");
                String hydroAccountNumber = sc.next();
                System.out.println("How much amount you would like to pay? ");
                int hydroAmount = sc.nextInt();
                selectedAccountObject.drawMoney(hydroAmount);
                System.out.println("Bill paid successfully! Your updated balance is " + selectedAccountObject.AvailableFunds + ". " +
                "Thank you for using our banking services.");
                break;
            case 2:
                System.out.println("Enter your mobile number: ");
                String mobileNumber = sc.next();
                System.out.println("How much amount you would like to pay? ");
                int mobileAmount = sc.nextInt();
                selectedAccountObject.drawMoney(mobileAmount);
                System.out.println("Bill paid successfully! Your updated balance is " + selectedAccountObject.AvailableFunds + ". " +
                        "Thank you for using our banking services.");
                break;
            case 3:
                System.out.println("Enter your credit card account number: ");
                String creditAccountNumber = sc.next();
                System.out.println("How much amount you would like to pay? ");
                int creditCardAmount = sc.nextInt();
                selectedAccountObject.drawMoney(creditCardAmount);
                System.out.println("Bill paid successfully! Your updated balance is " + selectedAccountObject.AvailableFunds + ". " +
                        "Thank you for using our banking services.");
                break;
        }
        bank_db.userAndHisAccountsObject.get(userID).replace(selectedAccountObject.AccountType, selectedAccountObject);
        try {
            objectMapper.writeValue(new File("textDB//MADT_Bank_DB.json"), bank_db);

        } catch (IOException e) {
            System.out.println(e);
        }
        return;
    }
    public static Account filterSelectedAccount(String userID, MADT_Bank_DB bank_db){
        Scanner sc = new Scanner(System.in);
        ObjectMapper mapper = new ObjectMapper();
        //HashMap<String, HashMap<String, Account>> bankDB = getBankDB();

        HashMap<String, Account> accountsObjects = new HashMap<String, Account>();
        accountsObjects = bank_db.userAndHisAccountsObject.get(userID);
        ArrayList<String> ExistingAccountTypes = new ArrayList<String>();
        System.out.println("Choose your account");

        int index = 1;
        for (String i : accountsObjects.keySet()) {
            System.out.println((index) + ". " + i);
            ExistingAccountTypes.add(i);
            index++;
        }
        int ch = sc.nextInt();
        Account selectedAccountObject = accountsObjects.get(ExistingAccountTypes.get(ch-1));

        return selectedAccountObject;
    }
    public static MADT_Bank_DB getBankDB(){
        //HashMap<String, HashMap<String, Account>> bankDB = new HashMap<String, HashMap<String, Account>>();
        MADT_Bank_DB bankDB = new MADT_Bank_DB();
        //JsonNode bankDB;
        HashMap<String, HashMap<String, Account>> bankDBObj = new HashMap<String, HashMap<String, Account>>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        File dbJSONFile = new File("textDB//MADT_Bank_DB.json");
        if (dbJSONFile.length() != 0) {
            try {
                //bankDB = objectMapper.readValue(dbJSONFile, new TypeReference<HashMap<String, HashMap<String, Account>>>(){});
                bankDB = objectMapper.readValue(dbJSONFile, MADT_Bank_DB.class);
                //bankDBObj = objectMapper.convertValue(bankDB, new TypeReference<HashMap<String, HashMap<String, Account>>>(){});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //return bankDBObj;
        return bankDB;
    }

}
