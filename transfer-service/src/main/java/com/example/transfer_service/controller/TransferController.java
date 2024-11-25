package com.example.transfer_service.controller;


import com.example.transfer_service.model.Transfer;
import com.example.transfer_service.service.TransferService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping
    public List<Transfer> getAllTransfers() {
        return transferService.getAllTransfers();
    }

    @PostMapping
    public Transfer createTransfer(@RequestBody Transfer transfer) {
        return transferService.createTransfer(transfer);
    }
}

