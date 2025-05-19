package ru.itk.concurrency.bank;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class BankAccount {
    private final UUID accountUuid;
    private BigDecimal balance;

    public BankAccount(BigDecimal balance) {
        this.accountUuid = UUID.randomUUID();
        this.balance = balance;
    }

    public synchronized void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            balance = balance.add(amount);
        }
    }

    public synchronized boolean withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0 && amount.compareTo(balance) <= 0) {
            balance = balance.subtract(amount);
            return true;
        }
        return false;
    }

    public synchronized BigDecimal getBalance() {
        return balance;
    }
}
