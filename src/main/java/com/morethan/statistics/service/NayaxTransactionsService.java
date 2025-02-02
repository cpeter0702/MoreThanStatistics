package com.morethan.statistics.service;

import com.morethan.statistics.domain.NayaxTransactions;
import com.morethan.statistics.repository.NayaxTransactionsRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.morethan.statistics.domain.NayaxTransactions}.
 */
@Service
@Transactional
public class NayaxTransactionsService {

    private final Logger log = LoggerFactory.getLogger(NayaxTransactionsService.class);

    private final NayaxTransactionsRepository nayaxTransactionsRepository;

    public NayaxTransactionsService(NayaxTransactionsRepository nayaxTransactionsRepository) {
        this.nayaxTransactionsRepository = nayaxTransactionsRepository;
    }

    /**
     * Save a nayaxTransactions.
     *
     * @param nayaxTransactions the entity to save.
     * @return the persisted entity.
     */
    public NayaxTransactions save(NayaxTransactions nayaxTransactions) {
        log.debug("Request to save NayaxTransactions : {}", nayaxTransactions);
        return nayaxTransactionsRepository.save(nayaxTransactions);
    }

    /**
     * Update a nayaxTransactions.
     *
     * @param nayaxTransactions the entity to save.
     * @return the persisted entity.
     */
    public NayaxTransactions update(NayaxTransactions nayaxTransactions) {
        log.debug("Request to update NayaxTransactions : {}", nayaxTransactions);
        return nayaxTransactionsRepository.save(nayaxTransactions);
    }

    /**
     * Partially update a nayaxTransactions.
     *
     * @param nayaxTransactions the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NayaxTransactions> partialUpdate(NayaxTransactions nayaxTransactions) {
        log.debug("Request to partially update NayaxTransactions : {}", nayaxTransactions);

        return nayaxTransactionsRepository
            .findById(nayaxTransactions.getId())
            .map(existingNayaxTransactions -> {
                if (nayaxTransactions.getSiteID() != null) {
                    existingNayaxTransactions.setSiteID(nayaxTransactions.getSiteID());
                }
                if (nayaxTransactions.getTransactionID() != null) {
                    existingNayaxTransactions.setTransactionID(nayaxTransactions.getTransactionID());
                }
                if (nayaxTransactions.getPaymentMethodID() != null) {
                    existingNayaxTransactions.setPaymentMethodID(nayaxTransactions.getPaymentMethodID());
                }
                if (nayaxTransactions.getCurrency() != null) {
                    existingNayaxTransactions.setCurrency(nayaxTransactions.getCurrency());
                }
                if (nayaxTransactions.getMachineName() != null) {
                    existingNayaxTransactions.setMachineName(nayaxTransactions.getMachineName());
                }
                if (nayaxTransactions.getAuthorizationValue() != null) {
                    existingNayaxTransactions.setAuthorizationValue(nayaxTransactions.getAuthorizationValue());
                }
                if (nayaxTransactions.getCampaignID() != null) {
                    existingNayaxTransactions.setCampaignID(nayaxTransactions.getCampaignID());
                }
                if (nayaxTransactions.getSettlementValue() != null) {
                    existingNayaxTransactions.setSettlementValue(nayaxTransactions.getSettlementValue());
                }
                if (nayaxTransactions.getProductSelectionInfo() != null) {
                    existingNayaxTransactions.setProductSelectionInfo(nayaxTransactions.getProductSelectionInfo());
                }
                if (nayaxTransactions.getCardNumber() != null) {
                    existingNayaxTransactions.setCardNumber(nayaxTransactions.getCardNumber());
                }
                if (nayaxTransactions.getAuthrizationRRN() != null) {
                    existingNayaxTransactions.setAuthrizationRRN(nayaxTransactions.getAuthrizationRRN());
                }
                if (nayaxTransactions.getMachineAuthorizationTime() != null) {
                    existingNayaxTransactions.setMachineAuthorizationTime(nayaxTransactions.getMachineAuthorizationTime());
                }
                if (nayaxTransactions.getMachineSettlementTime() != null) {
                    existingNayaxTransactions.setMachineSettlementTime(nayaxTransactions.getMachineSettlementTime());
                }
                if (nayaxTransactions.getCreditCardType() != null) {
                    existingNayaxTransactions.setCreditCardType(nayaxTransactions.getCreditCardType());
                }
                if (nayaxTransactions.getCardType() != null) {
                    existingNayaxTransactions.setCardType(nayaxTransactions.getCardType());
                }
                if (nayaxTransactions.getPaymentMethod() != null) {
                    existingNayaxTransactions.setPaymentMethod(nayaxTransactions.getPaymentMethod());
                }
                if (nayaxTransactions.getTransactionStatusID() != null) {
                    existingNayaxTransactions.setTransactionStatusID(nayaxTransactions.getTransactionStatusID());
                }
                if (nayaxTransactions.getTransactionTypeID() != null) {
                    existingNayaxTransactions.setTransactionTypeID(nayaxTransactions.getTransactionTypeID());
                }
                if (nayaxTransactions.getBillingProvider() != null) {
                    existingNayaxTransactions.setBillingProvider(nayaxTransactions.getBillingProvider());
                }
                if (nayaxTransactions.getPrepaidCardHolderName() != null) {
                    existingNayaxTransactions.setPrepaidCardHolderName(nayaxTransactions.getPrepaidCardHolderName());
                }
                if (nayaxTransactions.getRefundRequestBy() != null) {
                    existingNayaxTransactions.setRefundRequestBy(nayaxTransactions.getRefundRequestBy());
                }
                if (nayaxTransactions.getRefundRequestDate() != null) {
                    existingNayaxTransactions.setRefundRequestDate(nayaxTransactions.getRefundRequestDate());
                }
                if (nayaxTransactions.getRefundReason() != null) {
                    existingNayaxTransactions.setRefundReason(nayaxTransactions.getRefundReason());
                }

                return existingNayaxTransactions;
            })
            .map(nayaxTransactionsRepository::save);
    }

    /**
     * Get all the nayaxTransactions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<NayaxTransactions> findAll() {
        log.debug("Request to get all NayaxTransactions");
        return nayaxTransactionsRepository.findAll();
    }

    /**
     * Get one nayaxTransactions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NayaxTransactions> findOne(Long id) {
        log.debug("Request to get NayaxTransactions : {}", id);
        return nayaxTransactionsRepository.findById(id);
    }

    /**
     * Delete the nayaxTransactions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete NayaxTransactions : {}", id);
        nayaxTransactionsRepository.deleteById(id);
    }
}
