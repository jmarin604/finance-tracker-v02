package com.finance.tracker.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.finance.tracker.domain.Category;
import com.finance.tracker.domain.Transaction;
import com.finance.tracker.domain.TransactionType;
import com.finance.tracker.domain.User;
import com.finance.tracker.dto.TransactionDTO;
import com.finance.tracker.exception.NotFoundException;
import com.finance.tracker.repository.CategoryRepository;
import com.finance.tracker.repository.TransactionRepository;
import com.finance.tracker.repository.TransactionTypeRepository;
import com.finance.tracker.repository.UserRepository;
import com.finance.tracker.utility.Constants;

import lombok.extern.slf4j.Slf4j;

@Scope("singleton")
@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionTypeRepository transactionTypeRepository;
    
    public void validateTransaction(TransactionDTO transaction) throws NotFoundException {
        if (transaction == null) {
            throw new NotFoundException("Transaction is null");
        }
        if (transaction.getAmount() == null) {
            throw new NotFoundException("Transaction amount is null");
        }
        if (transaction.getTransactionDate() == null) {
            throw new NotFoundException("Transaction date is null");            
        }
        if (transaction.getUser() == null) {
            throw new NotFoundException("Transaction user is null");            
        }
        if (transaction.getCategory() == null) {
            throw new NotFoundException("Transaction category is null");
        }
        if (transaction.getTransactionType() == null) {
            throw new NotFoundException("Transaction type is null");
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void createTransaction(TransactionDTO transaction, String userCreation) throws NotFoundException {
        log.info("Creating transaction: " + transaction.getAmount());
        validateTransaction(transaction);

        Optional<User> user = userRepository.findById(transaction.getUser());
        User userTransaction = user.orElseThrow(() -> new NotFoundException("User not found"));

        Optional<Category> category = categoryRepository.findById(transaction.getCategory());
        Category categoryTransaction = category.orElseThrow(() -> new NotFoundException("Category not found"));

        Optional<TransactionType> transactionType = transactionTypeRepository.findById(transaction.getTransactionType());
        TransactionType transactionTypeTransaction = transactionType.orElseThrow(() -> new NotFoundException("TransactionType not found"));

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.getAmount());
        newTransaction.setTransactionDate(transaction.getTransactionDate());
        newTransaction.setUser(userTransaction);
        newTransaction.setCategory(categoryTransaction);
        newTransaction.setTransactionType(transactionTypeTransaction);
        newTransaction.setCreatorUser(userCreation);
        newTransaction.setCreationDate(new Date());
        newTransaction.setStatus(Constants.ENABLED);

        transactionRepository.save(newTransaction);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateTransaction(TransactionDTO transaction, String userModifier) throws NotFoundException {
        log.info("Updating transaction: " + transaction.getAmount());
        validateTransaction(transaction);
        Transaction transactionUpdate;

        Optional<Transaction> optionalTransaction = transactionRepository.findById(transaction.getTransId());
        transactionUpdate = optionalTransaction.orElseThrow(() -> new NotFoundException("Transaction not found"));

        Optional<User> user = userRepository.findById(transaction.getUser());
        User userTransaction = user.orElseThrow(() -> new NotFoundException("User not found"));

        Optional<Category> category = categoryRepository.findById(transaction.getCategory());
        Category categoryTransaction = category.orElseThrow(() -> new NotFoundException("Category not found"));

        Optional<TransactionType> transactionType = transactionTypeRepository.findById(transaction
                .getTransactionType());
        TransactionType transactionTypeTransaction = transactionType.orElseThrow(() -> new NotFoundException("TransactionType not found"));

        transactionUpdate.setAmount(transaction.getAmount());
        transactionUpdate.setTransactionDate(transaction.getTransactionDate());
        transactionUpdate.setUser(userTransaction);
        transactionUpdate.setCategory(categoryTransaction);
        transactionUpdate.setTransactionType(transactionTypeTransaction);
        transactionUpdate.setModifierUser(userModifier);
        transactionUpdate.setModificationDate(new Date());
        transactionUpdate.setStatus(Constants.ENABLED);

        transactionRepository.save(transactionUpdate);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteTransaction(Integer transactionId, String userDeletion) throws NotFoundException {
        log.info("Deleting transaction: " + transactionId);
        Transaction transactionDelete;

        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        transactionDelete = optionalTransaction.orElseThrow(() -> new NotFoundException("Transaction not found"));

        transactionDelete.setStatus(Constants.DISABLED);
        transactionDelete.setModifierUser(userDeletion);
        transactionDelete.setModificationDate(new Date());

        transactionRepository.save(transactionDelete);
    }

}
