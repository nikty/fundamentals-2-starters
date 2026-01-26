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
date. Naturally, all three types come with a current balance. ...

Represent the following examples using your classes:

1. Earl Gray, id# 1729, has $1,250 in a checking account with minimum
balance of $500;

2. Ima Flatt, id# 4104, has $10,123 in a certificate of deposit whose inter-
est rate is 4% and whose maturity date is June 1, 2005;

3. Annie Proulx, id# 2992, has $800 in a savings account; the account
yields interest at the rate of 3.5%.
*/

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

// ...

class ExamplesAccounts {
    ExamplesAccounts() {}

    // ...
}
