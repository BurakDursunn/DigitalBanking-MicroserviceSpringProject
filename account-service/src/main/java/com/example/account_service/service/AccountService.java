package com.example.account_service.service;

import com.example.account_service.model.Account;
import com.example.account_service.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account createAccount(Account account) {
        Optional<Account> existingAccount = accountRepository.findByUserId(account.getUserId());

        if (existingAccount.isPresent()) {
            throw new IllegalArgumentException("Bu kullanıcı ID'si ile zaten bir hesap mevcut.");
        }

        return accountRepository.save(account);
    }

    public Account updateAccount(Account account) {
        return accountRepository.save(account);
    }

    // Yeni metod: Hesabı ID'sine göre almak için
    public Account getAccountById(Long id) {
        System.out.println("Hesap ID'si: " + id); // Loglama eklendi
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()) {
            System.out.println("Hesap bulunamadı: " + id); // Loglama eklendi
        }
        return account.orElse(null); // Hesap bulunamazsa null döner
    }
}