package com.example.transfer_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import com.example.account_service.model.Account;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-service", url = "http://localhost:8082") // account-service'in URL'ini ayarlayın
public interface AccountService {

    @GetMapping("/api/accounts/{id}")
    Account getAccountById(@PathVariable Long id);

    @PutMapping("/api/accounts")
    Account updateAccount(@RequestBody Account account);
}
