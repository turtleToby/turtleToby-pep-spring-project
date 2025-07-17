package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;
import com.example.entity.Account;
import com.example.exception.AccountAlreadyExistsException;

import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account addAccount(Account account) throws AccountAlreadyExistsException {
        if (getAccountByUsername(account.getUsername()) != null) {
            throw new AccountAlreadyExistsException();
        }
        else if (!account.getUsername().isEmpty() && account.getPassword().length() > 4) {
            return accountRepository.save(account);
        }

        return null;
    }

    public Account getAccountById(Integer accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isPresent()) {
            return optionalAccount.get();
        }
        else {
            return null;
        }
    }

    public Account getAccountByUsername(String username) {
        Account account = accountRepository.findAccountByUsername(username);
        if (account != null) {
            return account;
        }
        
        return null;
    }

    public Account getAccountLogin(Account account) {
        return accountRepository.findAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
    }
}
