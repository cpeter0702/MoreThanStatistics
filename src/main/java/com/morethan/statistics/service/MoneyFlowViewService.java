package com.morethan.statistics.service;

import com.morethan.statistics.domain.MoneyFlowView;
import com.morethan.statistics.repository.MoneyFlowViewRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.morethan.statistics.domain.MoneyFlowView}.
 */
@Service
@Transactional
public class MoneyFlowViewService {

    private final Logger log = LoggerFactory.getLogger(MoneyFlowViewService.class);

    private final MoneyFlowViewRepository moneyFlowViewRepository;

    public MoneyFlowViewService(MoneyFlowViewRepository moneyFlowViewRepository) {
        this.moneyFlowViewRepository = moneyFlowViewRepository;
    }

    /**
     * Save a moneyFlowView.
     *
     * @param moneyFlowView the entity to save.
     * @return the persisted entity.
     */
    public MoneyFlowView save(MoneyFlowView moneyFlowView) {
        log.debug("Request to save MoneyFlowView : {}", moneyFlowView);
        return moneyFlowViewRepository.save(moneyFlowView);
    }

    /**
     * Update a moneyFlowView.
     *
     * @param moneyFlowView the entity to save.
     * @return the persisted entity.
     */
    public MoneyFlowView update(MoneyFlowView moneyFlowView) {
        log.debug("Request to update MoneyFlowView : {}", moneyFlowView);
        return moneyFlowViewRepository.save(moneyFlowView);
    }

    /**
     * Partially update a moneyFlowView.
     *
     * @param moneyFlowView the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MoneyFlowView> partialUpdate(MoneyFlowView moneyFlowView) {
        log.debug("Request to partially update MoneyFlowView : {}", moneyFlowView);

        return moneyFlowViewRepository
            .findById(moneyFlowView.getId())
            .map(existingMoneyFlowView -> {
                if (moneyFlowView.getSource() != null) {
                    existingMoneyFlowView.setSource(moneyFlowView.getSource());
                }
                if (moneyFlowView.getBusinessId() != null) {
                    existingMoneyFlowView.setBusinessId(moneyFlowView.getBusinessId());
                }
                if (moneyFlowView.getBusinessDate() != null) {
                    existingMoneyFlowView.setBusinessDate(moneyFlowView.getBusinessDate());
                }
                if (moneyFlowView.getBusinessAmt() != null) {
                    existingMoneyFlowView.setBusinessAmt(moneyFlowView.getBusinessAmt());
                }
                if (moneyFlowView.getBusinessType() != null) {
                    existingMoneyFlowView.setBusinessType(moneyFlowView.getBusinessType());
                }
                if (moneyFlowView.getBusinessTypeDetail() != null) {
                    existingMoneyFlowView.setBusinessTypeDetail(moneyFlowView.getBusinessTypeDetail());
                }
                if (moneyFlowView.getPayer() != null) {
                    existingMoneyFlowView.setPayer(moneyFlowView.getPayer());
                }
                if (moneyFlowView.getReceiver() != null) {
                    existingMoneyFlowView.setReceiver(moneyFlowView.getReceiver());
                }
                if (moneyFlowView.getRemark() != null) {
                    existingMoneyFlowView.setRemark(moneyFlowView.getRemark());
                }
                if (moneyFlowView.getIsActive() != null) {
                    existingMoneyFlowView.setIsActive(moneyFlowView.getIsActive());
                }

                return existingMoneyFlowView;
            })
            .map(moneyFlowViewRepository::save);
    }

    /**
     * Get all the moneyFlowViews.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MoneyFlowView> findAll() {
        log.debug("Request to get all MoneyFlowViews");
        return moneyFlowViewRepository.findAll();
    }

    /**
     * Get one moneyFlowView by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MoneyFlowView> findOne(Long id) {
        log.debug("Request to get MoneyFlowView : {}", id);
        return moneyFlowViewRepository.findById(id);
    }

    /**
     * Delete the moneyFlowView by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MoneyFlowView : {}", id);
        moneyFlowViewRepository.deleteById(id);
    }
}
