/*
Exercise 4.4

Design a data representation for this problem:

... Develop a “bank account” program. The program keeps
track of the balances in a person’s bank accounts. Each account
has an id number and a customer’s name. There are three kinds
of accounts: a checking account, a savings account, and a cer-
tificate of deposit (CD). Checking account information also in-
cludes the minimum balance. Savings account includes the in-
terest rate. A CD specifies the interest rate and the maturity
date. Naturally, all three types come with a current balance.

Represent the following examples using your classes:

1. Earl Gray, id# 1729, has $1,250 in a checking account with minimum
balance of $500;

2. Ima Flatt, id# 4104, has $10,123 in a certificate of deposit whose inter-
est rate is 4% and whose maturity date is June 1, 2005;

3. Annie Proulx, id# 2992, has $800 in a savings account; the account
yields interest at the rate of 3.5%.
*/

interface IAccount {}

class CheckingAccount implements IAccount {
    int id;
    String name;
    int minimumBalance;
    int currentBalance;

    CheckingAccount(int id, String name, int minimumBalance, int currentBalance) {
        this.id = id;
        this.name = name;
        this.minimumBalance = minimumBalance;
        this.currentBalance = currentBalance;
        
    }
}

class SavingsAccount implements IAccount {
    int id;
    String name;
    double interestRate;
    int currentBalance;
    
    SavingsAccount(int id, String name, double interestRate, int currentBalance) {
        this.id = id;
        this.name = name;
        this.interestRate = interestRate;
        this.currentBalance = currentBalance;
    }
}

class CertificateDepositAccount implements IAccount {
    int id;
    String name;
    double interestRate;
    Date maturityDate;
    int currentBalance;
    
    CertificateDepositAccount(int id, String name, double interestRate, Date maturityDate, int currentBalance) {
        this.id = id;
        this.name = name;
        this.interestRate = interestRate;
        this.maturityDate = maturityDate;
        this.currentBalance = currentBalance;
    }
}

class Date {
    int day;
    int month;
    int year;

    Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }
}

class ExamplesAccounts {
    ExamplesAccounts() {}

    Date d2 = new Date(1, 6, 2005);

    IAccount acc1 = new CheckingAccount(1729, "Earl Gray", 500, 1250);
    IAccount acc2 = new CertificateDepositAccount(4104, "Ima Flatt", 4, d2, 10123);
    IAccount acc3 = new SavingsAccount(2992, "Annie Proulx", 3.5, 800);
}
