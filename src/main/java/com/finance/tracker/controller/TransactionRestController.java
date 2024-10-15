package com.finance.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.tracker.dto.TransactionDTO;
import com.finance.tracker.exception.NotFoundException;
import com.finance.tracker.service.TransactionService;
import com.finance.tracker.utility.Utilities;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionRestController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<?> createTransaction(@RequestBody TransactionDTO transactionDTO) throws NotFoundException {
        String user = Utilities.getAuthenticatedUser();
        transactionService.createTransaction(transactionDTO, user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTransaction(@RequestBody TransactionDTO transactionDTO) throws NotFoundException {
        String user = Utilities.getAuthenticatedUser();
        transactionService.updateTransaction(transactionDTO, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{transactionId}")
    public ResponseEntity<?> deleteTransaction(@PathVariable("transactionId") Integer transactionId) throws NotFoundException {
        String user = Utilities.getAuthenticatedUser();
        transactionService.deleteTransaction(transactionId, user);
        return ResponseEntity.ok().build();
    }

}
