package com.finance.tracker.service;

import com.finance.tracker.dto.TransactionDTO;
import com.finance.tracker.exception.NotFoundException;

public interface TransactionService {

        public void createTransaction(TransactionDTO transaction, String userCreation) throws NotFoundException;

        public void updateTransaction(TransactionDTO transaction, String userModifier) throws NotFoundException;

        public void deleteTransaction(Integer transactionId, String userDeletion) throws NotFoundException;

}
