package com.example.account_service.controller;

import com.example.account_service.model.Account;
import com.example.account_service.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Tüm hesapları listeleme
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts); // Tüm hesapları döner
    }

    // ID'ye göre hesap alma
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        Account account = accountService.getAccountById(id);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Hesap bulunmazsa 404 döner
        }
        return ResponseEntity.ok(account); // Hesap bulunduysa, hesabı döner
    }

    // Hesap oluşturma
    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        try {
            Account createdAccount = accountService.createAccount(account);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Hesap güncelleme
    @PutMapping
    public ResponseEntity<Account> updateAccount(@RequestBody Account account) {
        Account updatedAccount = accountService.updateAccount(account);
        return ResponseEntity.ok(updatedAccount); // Güncellenen hesabı döner
    }
}
