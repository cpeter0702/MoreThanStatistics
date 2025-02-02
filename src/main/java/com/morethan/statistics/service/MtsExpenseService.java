package com.morethan.statistics.service;

import com.morethan.statistics.domain.MtsExpense;
import com.morethan.statistics.repository.MtsExpenseRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.morethan.statistics.domain.MtsExpense}.
 */
@Service
@Transactional
public class MtsExpenseService {

    private final Logger log = LoggerFactory.getLogger(MtsExpenseService.class);

    private final MtsExpenseRepository mtsExpenseRepository;

    public MtsExpenseService(MtsExpenseRepository mtsExpenseRepository) {
        this.mtsExpenseRepository = mtsExpenseRepository;
    }

    /**
     * Save a mtsExpense.
     *
     * @param mtsExpense the entity to save.
     * @return the persisted entity.
     */
    public MtsExpense save(MtsExpense mtsExpense) {
        log.debug("Request to save MtsExpense : {}", mtsExpense);
        return mtsExpenseRepository.save(mtsExpense);
    }

    /**
     * Update a mtsExpense.
     *
     * @param mtsExpense the entity to save.
     * @return the persisted entity.
     */
    public MtsExpense update(MtsExpense mtsExpense) {
        log.debug("Request to update MtsExpense : {}", mtsExpense);
        return mtsExpenseRepository.save(mtsExpense);
    }

    /**
     * Partially update a mtsExpense.
     *
     * @param mtsExpense the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MtsExpense> partialUpdate(MtsExpense mtsExpense) {
        log.debug("Request to partially update MtsExpense : {}", mtsExpense);

        return mtsExpenseRepository
            .findById(mtsExpense.getId())
            .map(existingMtsExpense -> {
                if (mtsExpense.getExpenseDate() != null) {
                    existingMtsExpense.setExpenseDate(mtsExpense.getExpenseDate());
                }
                if (mtsExpense.getExpenseAmount() != null) {
                    existingMtsExpense.setExpenseAmount(mtsExpense.getExpenseAmount());
                }
                if (mtsExpense.getExpenseType() != null) {
                    existingMtsExpense.setExpenseType(mtsExpense.getExpenseType());
                }
                if (mtsExpense.getExpenseTypeDetail() != null) {
                    existingMtsExpense.setExpenseTypeDetail(mtsExpense.getExpenseTypeDetail());
                }
                if (mtsExpense.getExpensePayer() != null) {
                    existingMtsExpense.setExpensePayer(mtsExpense.getExpensePayer());
                }
                if (mtsExpense.getExpenseReceiver() != null) {
                    existingMtsExpense.setExpenseReceiver(mtsExpense.getExpenseReceiver());
                }
                if (mtsExpense.getExpenseRemark() != null) {
                    existingMtsExpense.setExpenseRemark(mtsExpense.getExpenseRemark());
                }
                if (mtsExpense.getExpenseReceipt() != null) {
                    existingMtsExpense.setExpenseReceipt(mtsExpense.getExpenseReceipt());
                }
                if (mtsExpense.getExpenseReceiptContentType() != null) {
                    existingMtsExpense.setExpenseReceiptContentType(mtsExpense.getExpenseReceiptContentType());
                }
                if (mtsExpense.getIsActive() != null) {
                    existingMtsExpense.setIsActive(mtsExpense.getIsActive());
                }
                if (mtsExpense.getModifier() != null) {
                    existingMtsExpense.setModifier(mtsExpense.getModifier());
                }
                if (mtsExpense.getModifyDatetime() != null) {
                    existingMtsExpense.setModifyDatetime(mtsExpense.getModifyDatetime());
                }
                if (mtsExpense.getCreator() != null) {
                    existingMtsExpense.setCreator(mtsExpense.getCreator());
                }
                if (mtsExpense.getCreateDatetime() != null) {
                    existingMtsExpense.setCreateDatetime(mtsExpense.getCreateDatetime());
                }

                return existingMtsExpense;
            })
            .map(mtsExpenseRepository::save);
    }

    /**
     * Get all the mtsExpenses.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MtsExpense> findAll() {
        log.debug("Request to get all MtsExpenses");
        return mtsExpenseRepository.findAll();
    }

    /**
     * Get one mtsExpense by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MtsExpense> findOne(Long id) {
        log.debug("Request to get MtsExpense : {}", id);
        return mtsExpenseRepository.findById(id);
    }

    /**
     * Delete the mtsExpense by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MtsExpense : {}", id);
        mtsExpenseRepository.deleteById(id);
    }
}
