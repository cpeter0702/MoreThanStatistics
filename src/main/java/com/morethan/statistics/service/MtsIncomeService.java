package com.morethan.statistics.service;

import com.morethan.statistics.domain.MtsIncome;
import com.morethan.statistics.repository.MtsIncomeRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.morethan.statistics.domain.MtsIncome}.
 */
@Service
@Transactional
public class MtsIncomeService {

    private final Logger log = LoggerFactory.getLogger(MtsIncomeService.class);

    private final MtsIncomeRepository mtsIncomeRepository;

    public MtsIncomeService(MtsIncomeRepository mtsIncomeRepository) {
        this.mtsIncomeRepository = mtsIncomeRepository;
    }

    /**
     * Save a mtsIncome.
     *
     * @param mtsIncome the entity to save.
     * @return the persisted entity.
     */
    public MtsIncome save(MtsIncome mtsIncome) {
        log.debug("Request to save MtsIncome : {}", mtsIncome);
        return mtsIncomeRepository.save(mtsIncome);
    }

    /**
     * Update a mtsIncome.
     *
     * @param mtsIncome the entity to save.
     * @return the persisted entity.
     */
    public MtsIncome update(MtsIncome mtsIncome) {
        log.debug("Request to update MtsIncome : {}", mtsIncome);
        return mtsIncomeRepository.save(mtsIncome);
    }

    /**
     * Partially update a mtsIncome.
     *
     * @param mtsIncome the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MtsIncome> partialUpdate(MtsIncome mtsIncome) {
        log.debug("Request to partially update MtsIncome : {}", mtsIncome);

        return mtsIncomeRepository
            .findById(mtsIncome.getId())
            .map(existingMtsIncome -> {
                if (mtsIncome.getIncomeDate() != null) {
                    existingMtsIncome.setIncomeDate(mtsIncome.getIncomeDate());
                }
                if (mtsIncome.getIncomeAmount() != null) {
                    existingMtsIncome.setIncomeAmount(mtsIncome.getIncomeAmount());
                }
                if (mtsIncome.getIncomeType() != null) {
                    existingMtsIncome.setIncomeType(mtsIncome.getIncomeType());
                }
                if (mtsIncome.getIncomeTypeDetail() != null) {
                    existingMtsIncome.setIncomeTypeDetail(mtsIncome.getIncomeTypeDetail());
                }
                if (mtsIncome.getIncomePayer() != null) {
                    existingMtsIncome.setIncomePayer(mtsIncome.getIncomePayer());
                }
                if (mtsIncome.getIncomeReceiver() != null) {
                    existingMtsIncome.setIncomeReceiver(mtsIncome.getIncomeReceiver());
                }
                if (mtsIncome.getIncomeRemark() != null) {
                    existingMtsIncome.setIncomeRemark(mtsIncome.getIncomeRemark());
                }
                if (mtsIncome.getIsActive() != null) {
                    existingMtsIncome.setIsActive(mtsIncome.getIsActive());
                }
                if (mtsIncome.getModifier() != null) {
                    existingMtsIncome.setModifier(mtsIncome.getModifier());
                }
                if (mtsIncome.getModifyDatetime() != null) {
                    existingMtsIncome.setModifyDatetime(mtsIncome.getModifyDatetime());
                }
                if (mtsIncome.getCreator() != null) {
                    existingMtsIncome.setCreator(mtsIncome.getCreator());
                }
                if (mtsIncome.getCreateDatetime() != null) {
                    existingMtsIncome.setCreateDatetime(mtsIncome.getCreateDatetime());
                }

                return existingMtsIncome;
            })
            .map(mtsIncomeRepository::save);
    }

    /**
     * Get all the mtsIncomes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MtsIncome> findAll() {
        log.debug("Request to get all MtsIncomes");
        return mtsIncomeRepository.findAll();
    }

    /**
     * Get one mtsIncome by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MtsIncome> findOne(Long id) {
        log.debug("Request to get MtsIncome : {}", id);
        return mtsIncomeRepository.findById(id);
    }

    /**
     * Delete the mtsIncome by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MtsIncome : {}", id);
        mtsIncomeRepository.deleteById(id);
    }
}
