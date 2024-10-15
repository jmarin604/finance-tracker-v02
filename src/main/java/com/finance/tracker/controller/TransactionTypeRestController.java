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

import com.finance.tracker.dto.TransactionTypeDTO;
import com.finance.tracker.exception.NotFoundException;
import com.finance.tracker.service.TransactionTypeService;
import com.finance.tracker.utility.Utilities;


@RestController
@RequestMapping("/api/v1/transactionType")
public class TransactionTypeRestController {

    @Autowired
    private TransactionTypeService transactionTypeService;

    @PostMapping("/create")
    public ResponseEntity<?> createTransactionType(@RequestBody TransactionTypeDTO transactionTypeDTO) throws NotFoundException {
        String user = Utilities.getAuthenticatedUser();
        transactionTypeService.createTransactionType(transactionTypeDTO, user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTransactionType(@RequestBody TransactionTypeDTO transactionTypeDTO) throws NotFoundException {
        String user = Utilities.getAuthenticatedUser();
        transactionTypeService.updateTransactionType(transactionTypeDTO, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{transactionTypeId}")
    public ResponseEntity<?> deleteTransactionType(@PathVariable("transactionTypeId") Integer transactionTypeId) throws NotFoundException {
        String user = Utilities.getAuthenticatedUser();
        transactionTypeService.deleteTransactionType(transactionTypeId, user);
        return ResponseEntity.ok().build();
    }
    
    

}
