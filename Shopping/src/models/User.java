package models;

public class User {

    private String name;
    private String role;
    private double bankAccount;
    private double initialBankAccount;
    private double missingAmount;

    public User(String name, double bankAccount) {
        this.name = name;
        this.initialBankAccount = bankAccount;
        this.bankAccount = bankAccount;
        this.missingAmount = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(double bankAccount) {
        this.bankAccount = bankAccount;
    }

    public double getInitialBankAccount() {
        return initialBankAccount;
    }

    public double getBoughtAmount() {
        return (this.initialBankAccount - this.bankAccount);
    }

    public void addMissingAmount(double amount) {
        this.missingAmount += amount;
    }

    public void debitBalance(double amount) {
        this.bankAccount -= amount;
    }

    public double getMissingAmount() {
        return missingAmount;
    }
}
