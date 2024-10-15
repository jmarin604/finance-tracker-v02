package com.finance.tracker.service;

import com.finance.tracker.dto.TransactionTypeDTO;
import com.finance.tracker.exception.NotFoundException;

public interface TransactionTypeService {

    public void createTransactionType(TransactionTypeDTO transactionType, String userCreation)
            throws NotFoundException;

    public void updateTransactionType(TransactionTypeDTO transactionType, String userModification)
            throws NotFoundException;

    public void deleteTransactionType(Integer transactionTypeId, String userDeletion)
            throws NotFoundException;

}
