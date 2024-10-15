package com.finance.tracker.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.finance.tracker.domain.TransactionType;
import com.finance.tracker.dto.TransactionTypeDTO;
import com.finance.tracker.exception.NotFoundException;
import com.finance.tracker.repository.TransactionTypeRepository;
import com.finance.tracker.utility.Constants;

import lombok.extern.slf4j.Slf4j;

@Scope("singleton")
@Service
@Slf4j
public class TransactionTypeServiceImpl implements TransactionTypeService {

    @Autowired
    private TransactionTypeRepository transactionTypeRepository;

    public void validateTransactionType(TransactionTypeDTO transactionType) throws NotFoundException {
        if (transactionType == null) {
            throw new NotFoundException("TransactionType is null");
        }
        if (transactionType.getName() == null || transactionType.getName().isBlank()) {
            throw new NotFoundException("TransactionType name is null or empty");
        }
        if (transactionType.getDescription() == null || transactionType.getDescription().isBlank()) {
            throw new NotFoundException("TransactionType description is null or empty");
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void createTransactionType(TransactionTypeDTO transactionType, String userCreation)
            throws NotFoundException {
        log.info("Creating transactionType: " + transactionType.getName());
        validateTransactionType(transactionType);

        TransactionType newTransactionType = new TransactionType();
        newTransactionType.setName(transactionType.getName());
        newTransactionType.setDescription(transactionType.getDescription());
        newTransactionType.setCreatorUser(userCreation);
        newTransactionType.setCreationDate(new Date());
        newTransactionType.setStatus(Constants.ENABLED);

        transactionTypeRepository.save(newTransactionType);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateTransactionType(TransactionTypeDTO transactionType, String userModification)
            throws NotFoundException {
        log.info("Updating transactionType: " + transactionType.getName());
        validateTransactionType(transactionType);
        TransactionType categoryUpdate;

        Optional<TransactionType> optionalTransactionType = transactionTypeRepository.findById(transactionType
                .getTransTypId());
        categoryUpdate = optionalTransactionType.orElseThrow(() -> new NotFoundException("TransactionType not found"));

        categoryUpdate.setTransTypId(transactionType.getTransTypId());
        categoryUpdate.setName(transactionType.getName());
        categoryUpdate.setDescription(transactionType.getDescription());
        categoryUpdate.setModifierUser(userModification);
        categoryUpdate.setModificationDate(new Date());
        categoryUpdate.setStatus(Constants.ENABLED);

        transactionTypeRepository.save(categoryUpdate);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteTransactionType(Integer transactionTypeId, String userDeletion) throws NotFoundException {
        log.info("Deleting transactionType: " + transactionTypeId);
        TransactionType transactionTypeDelete;

        Optional<TransactionType> optionalTransactionType = transactionTypeRepository.findById(transactionTypeId);
        transactionTypeDelete = optionalTransactionType.orElseThrow(() -> new NotFoundException("TransactionType not found"));

        transactionTypeDelete.setStatus(Constants.DISABLED);
        transactionTypeDelete.setModifierUser(userDeletion);
        transactionTypeDelete.setModificationDate(new Date());

        transactionTypeRepository.save(transactionTypeDelete);
    }

}
