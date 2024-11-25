package com.example.transfer_service.service;


import com.example.account_service.model.Account;
import com.example.transfer_service.client.AccountService;
import com.example.transfer_service.kafka.TransferProducer;
import com.example.transfer_service.model.Transfer;
import com.example.transfer_service.repository.TransferRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final TransferProducer transferProducer;
    private final AccountService accountService;

    public TransferService(TransferRepository transferRepository, TransferProducer transferProducer,
                           AccountService accountService) {
        this.transferRepository = transferRepository;
        this.transferProducer = transferProducer;
        this.accountService = accountService;
    }

    public List<Transfer> getAllTransfers() {
        return transferRepository.findAll();
    }

    public Transfer createTransfer(Transfer transfer) {
        // Transfer işlemi öncesi hesap bakiyelerini güncelleyin
        Account fromAccount = accountService.getAccountById(transfer.getFromAccountId());
        Account toAccount = accountService.getAccountById(transfer.getToAccountId());

        // Hesapların null olup olmadığını kontrol et
        if (fromAccount == null || toAccount == null) {
            throw new IllegalArgumentException("Hesap bulunamadı.");
        }

        // Transfer miktarının geçerli olup olmadığını kontrol et
        if (transfer.getAmount() <= 0) {
            throw new IllegalArgumentException("Geçersiz transfer miktarı.");
        }

        // Gönderen hesabın bakiyesini kontrol et
        if (fromAccount.getBalance() < transfer.getAmount()) {
            throw new IllegalArgumentException("Yetersiz bakiye.");
        }

        // Gönderen hesabın bakiyesini azalt
        fromAccount.setBalance(fromAccount.getBalance() - transfer.getAmount());
        // Alıcı hesabın bakiyesini artır
        toAccount.setBalance(toAccount.getBalance() + transfer.getAmount());

        // Hesapları güncelle
        accountService.updateAccount(fromAccount);
        accountService.updateAccount(toAccount);

        Transfer savedTransfer = transferRepository.save(transfer);
        transferProducer.sendTransfer(savedTransfer); // Mesajı gönder
        return savedTransfer;
    }
}