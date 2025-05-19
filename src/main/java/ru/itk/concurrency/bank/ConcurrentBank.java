package ru.itk.concurrency.bank;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ConcurrentBank {
    private final List<BankAccount> accounts;

    public ConcurrentBank() {
        this.accounts = new ArrayList<>();
    }

    public synchronized BankAccount createAccount(BigDecimal amount) {
        BankAccount account = new BankAccount(amount);
        accounts.add(account);
        return account;
    }

    public void transfer(BankAccount from, BankAccount to, BigDecimal amount) {
        BankAccount firstLock = from.getAccountUuid().compareTo(to.getAccountUuid()) > 0 ? from : to;
        BankAccount secondLock = from.getAccountUuid().compareTo(to.getAccountUuid()) <= 0 ? from : to;

        synchronized (firstLock) {
            synchronized (secondLock) {
                if (from.withdraw(amount)) {
                    to.deposit(amount);
                }
            }
        }
    }

    public synchronized BigDecimal getTotalBalance() {
        return accounts.stream()
                .map(BankAccount::getBalance)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
