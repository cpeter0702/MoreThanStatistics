package com.morethan.statistics.web.rest;

import static com.morethan.statistics.domain.NayaxTransactionsAsserts.*;
import static com.morethan.statistics.web.rest.TestUtil.createUpdateProxyForBean;
import static com.morethan.statistics.web.rest.TestUtil.sameInstant;
import static com.morethan.statistics.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.morethan.statistics.IntegrationTest;
import com.morethan.statistics.domain.NayaxTransactions;
import com.morethan.statistics.repository.NayaxTransactionsRepository;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NayaxTransactionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NayaxTransactionsResourceIT {

    private static final Integer DEFAULT_SITE_ID = 1;
    private static final Integer UPDATED_SITE_ID = 2;

    private static final String DEFAULT_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_PAYMENT_METHOD_ID = 1;
    private static final Integer UPDATED_PAYMENT_METHOD_ID = 2;

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final String DEFAULT_MACHINE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MACHINE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_AUTHORIZATION_VALUE = 1;
    private static final Integer UPDATED_AUTHORIZATION_VALUE = 2;

    private static final String DEFAULT_CAMPAIGN_ID = "AAAAAAAAAA";
    private static final String UPDATED_CAMPAIGN_ID = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_SETTLEMENT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SETTLEMENT_VALUE = new BigDecimal(2);

    private static final String DEFAULT_PRODUCT_SELECTION_INFO = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_SELECTION_INFO = "BBBBBBBBBB";

    private static final String DEFAULT_CARD_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CARD_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHRIZATION_RRN = "AAAAAAAAAA";
    private static final String UPDATED_AUTHRIZATION_RRN = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_MACHINE_AUTHORIZATION_TIME = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(0L),
        ZoneOffset.UTC
    );
    private static final ZonedDateTime UPDATED_MACHINE_AUTHORIZATION_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_MACHINE_SETTLEMENT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MACHINE_SETTLEMENT_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREDIT_CARD_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_CARD_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CARD_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CARD_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_METHOD = "BBBBBBBBBB";

    private static final Integer DEFAULT_TRANSACTION_STATUS_ID = 1;
    private static final Integer UPDATED_TRANSACTION_STATUS_ID = 2;

    private static final Integer DEFAULT_TRANSACTION_TYPE_ID = 1;
    private static final Integer UPDATED_TRANSACTION_TYPE_ID = 2;

    private static final String DEFAULT_BILLING_PROVIDER = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_PROVIDER = "BBBBBBBBBB";

    private static final String DEFAULT_PREPAID_CARD_HOLDER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PREPAID_CARD_HOLDER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REFUND_REQUEST_BY = "AAAAAAAAAA";
    private static final String UPDATED_REFUND_REQUEST_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_REFUND_REQUEST_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REFUND_REQUEST_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_REFUND_REASON = "AAAAAAAAAA";
    private static final String UPDATED_REFUND_REASON = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nayax-transactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private NayaxTransactionsRepository nayaxTransactionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNayaxTransactionsMockMvc;

    private NayaxTransactions nayaxTransactions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NayaxTransactions createEntity(EntityManager em) {
        NayaxTransactions nayaxTransactions = new NayaxTransactions()
            .siteID(DEFAULT_SITE_ID)
            .transactionID(DEFAULT_TRANSACTION_ID)
            .paymentMethodID(DEFAULT_PAYMENT_METHOD_ID)
            .currency(DEFAULT_CURRENCY)
            .machineName(DEFAULT_MACHINE_NAME)
            .authorizationValue(DEFAULT_AUTHORIZATION_VALUE)
            .campaignID(DEFAULT_CAMPAIGN_ID)
            .settlementValue(DEFAULT_SETTLEMENT_VALUE)
            .productSelectionInfo(DEFAULT_PRODUCT_SELECTION_INFO)
            .cardNumber(DEFAULT_CARD_NUMBER)
            .authrizationRRN(DEFAULT_AUTHRIZATION_RRN)
            .machineAuthorizationTime(DEFAULT_MACHINE_AUTHORIZATION_TIME)
            .machineSettlementTime(DEFAULT_MACHINE_SETTLEMENT_TIME)
            .creditCardType(DEFAULT_CREDIT_CARD_TYPE)
            .cardType(DEFAULT_CARD_TYPE)
            .paymentMethod(DEFAULT_PAYMENT_METHOD)
            .transactionStatusID(DEFAULT_TRANSACTION_STATUS_ID)
            .transactionTypeID(DEFAULT_TRANSACTION_TYPE_ID)
            .billingProvider(DEFAULT_BILLING_PROVIDER)
            .prepaidCardHolderName(DEFAULT_PREPAID_CARD_HOLDER_NAME)
            .refundRequestBy(DEFAULT_REFUND_REQUEST_BY)
            .refundRequestDate(DEFAULT_REFUND_REQUEST_DATE)
            .refundReason(DEFAULT_REFUND_REASON);
        return nayaxTransactions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NayaxTransactions createUpdatedEntity(EntityManager em) {
        NayaxTransactions nayaxTransactions = new NayaxTransactions()
            .siteID(UPDATED_SITE_ID)
            .transactionID(UPDATED_TRANSACTION_ID)
            .paymentMethodID(UPDATED_PAYMENT_METHOD_ID)
            .currency(UPDATED_CURRENCY)
            .machineName(UPDATED_MACHINE_NAME)
            .authorizationValue(UPDATED_AUTHORIZATION_VALUE)
            .campaignID(UPDATED_CAMPAIGN_ID)
            .settlementValue(UPDATED_SETTLEMENT_VALUE)
            .productSelectionInfo(UPDATED_PRODUCT_SELECTION_INFO)
            .cardNumber(UPDATED_CARD_NUMBER)
            .authrizationRRN(UPDATED_AUTHRIZATION_RRN)
            .machineAuthorizationTime(UPDATED_MACHINE_AUTHORIZATION_TIME)
            .machineSettlementTime(UPDATED_MACHINE_SETTLEMENT_TIME)
            .creditCardType(UPDATED_CREDIT_CARD_TYPE)
            .cardType(UPDATED_CARD_TYPE)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .transactionStatusID(UPDATED_TRANSACTION_STATUS_ID)
            .transactionTypeID(UPDATED_TRANSACTION_TYPE_ID)
            .billingProvider(UPDATED_BILLING_PROVIDER)
            .prepaidCardHolderName(UPDATED_PREPAID_CARD_HOLDER_NAME)
            .refundRequestBy(UPDATED_REFUND_REQUEST_BY)
            .refundRequestDate(UPDATED_REFUND_REQUEST_DATE)
            .refundReason(UPDATED_REFUND_REASON);
        return nayaxTransactions;
    }

    @BeforeEach
    public void initTest() {
        nayaxTransactions = createEntity(em);
    }

    @Test
    @Transactional
    void createNayaxTransactions() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the NayaxTransactions
        var returnedNayaxTransactions = om.readValue(
            restNayaxTransactionsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nayaxTransactions)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            NayaxTransactions.class
        );

        // Validate the NayaxTransactions in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertNayaxTransactionsUpdatableFieldsEquals(returnedNayaxTransactions, getPersistedNayaxTransactions(returnedNayaxTransactions));
    }

    @Test
    @Transactional
    void createNayaxTransactionsWithExistingId() throws Exception {
        // Create the NayaxTransactions with an existing ID
        nayaxTransactions.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNayaxTransactionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nayaxTransactions)))
            .andExpect(status().isBadRequest());

        // Validate the NayaxTransactions in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTransactionIDIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        nayaxTransactions.setTransactionID(null);

        // Create the NayaxTransactions, which fails.

        restNayaxTransactionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nayaxTransactions)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCurrencyIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        nayaxTransactions.setCurrency(null);

        // Create the NayaxTransactions, which fails.

        restNayaxTransactionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nayaxTransactions)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMachineNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        nayaxTransactions.setMachineName(null);

        // Create the NayaxTransactions, which fails.

        restNayaxTransactionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nayaxTransactions)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProductSelectionInfoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        nayaxTransactions.setProductSelectionInfo(null);

        // Create the NayaxTransactions, which fails.

        restNayaxTransactionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nayaxTransactions)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCardNumberIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        nayaxTransactions.setCardNumber(null);

        // Create the NayaxTransactions, which fails.

        restNayaxTransactionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nayaxTransactions)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAuthrizationRRNIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        nayaxTransactions.setAuthrizationRRN(null);

        // Create the NayaxTransactions, which fails.

        restNayaxTransactionsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nayaxTransactions)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNayaxTransactions() throws Exception {
        // Initialize the database
        nayaxTransactionsRepository.saveAndFlush(nayaxTransactions);

        // Get all the nayaxTransactionsList
        restNayaxTransactionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nayaxTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].siteID").value(hasItem(DEFAULT_SITE_ID)))
            .andExpect(jsonPath("$.[*].transactionID").value(hasItem(DEFAULT_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].paymentMethodID").value(hasItem(DEFAULT_PAYMENT_METHOD_ID)))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].machineName").value(hasItem(DEFAULT_MACHINE_NAME)))
            .andExpect(jsonPath("$.[*].authorizationValue").value(hasItem(DEFAULT_AUTHORIZATION_VALUE)))
            .andExpect(jsonPath("$.[*].campaignID").value(hasItem(DEFAULT_CAMPAIGN_ID)))
            .andExpect(jsonPath("$.[*].settlementValue").value(hasItem(sameNumber(DEFAULT_SETTLEMENT_VALUE))))
            .andExpect(jsonPath("$.[*].productSelectionInfo").value(hasItem(DEFAULT_PRODUCT_SELECTION_INFO)))
            .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DEFAULT_CARD_NUMBER)))
            .andExpect(jsonPath("$.[*].authrizationRRN").value(hasItem(DEFAULT_AUTHRIZATION_RRN)))
            .andExpect(jsonPath("$.[*].machineAuthorizationTime").value(hasItem(sameInstant(DEFAULT_MACHINE_AUTHORIZATION_TIME))))
            .andExpect(jsonPath("$.[*].machineSettlementTime").value(hasItem(sameInstant(DEFAULT_MACHINE_SETTLEMENT_TIME))))
            .andExpect(jsonPath("$.[*].creditCardType").value(hasItem(DEFAULT_CREDIT_CARD_TYPE)))
            .andExpect(jsonPath("$.[*].cardType").value(hasItem(DEFAULT_CARD_TYPE)))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD)))
            .andExpect(jsonPath("$.[*].transactionStatusID").value(hasItem(DEFAULT_TRANSACTION_STATUS_ID)))
            .andExpect(jsonPath("$.[*].transactionTypeID").value(hasItem(DEFAULT_TRANSACTION_TYPE_ID)))
            .andExpect(jsonPath("$.[*].billingProvider").value(hasItem(DEFAULT_BILLING_PROVIDER)))
            .andExpect(jsonPath("$.[*].prepaidCardHolderName").value(hasItem(DEFAULT_PREPAID_CARD_HOLDER_NAME)))
            .andExpect(jsonPath("$.[*].refundRequestBy").value(hasItem(DEFAULT_REFUND_REQUEST_BY)))
            .andExpect(jsonPath("$.[*].refundRequestDate").value(hasItem(sameInstant(DEFAULT_REFUND_REQUEST_DATE))))
            .andExpect(jsonPath("$.[*].refundReason").value(hasItem(DEFAULT_REFUND_REASON)));
    }

    @Test
    @Transactional
    void getNayaxTransactions() throws Exception {
        // Initialize the database
        nayaxTransactionsRepository.saveAndFlush(nayaxTransactions);

        // Get the nayaxTransactions
        restNayaxTransactionsMockMvc
            .perform(get(ENTITY_API_URL_ID, nayaxTransactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nayaxTransactions.getId().intValue()))
            .andExpect(jsonPath("$.siteID").value(DEFAULT_SITE_ID))
            .andExpect(jsonPath("$.transactionID").value(DEFAULT_TRANSACTION_ID))
            .andExpect(jsonPath("$.paymentMethodID").value(DEFAULT_PAYMENT_METHOD_ID))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY))
            .andExpect(jsonPath("$.machineName").value(DEFAULT_MACHINE_NAME))
            .andExpect(jsonPath("$.authorizationValue").value(DEFAULT_AUTHORIZATION_VALUE))
            .andExpect(jsonPath("$.campaignID").value(DEFAULT_CAMPAIGN_ID))
            .andExpect(jsonPath("$.settlementValue").value(sameNumber(DEFAULT_SETTLEMENT_VALUE)))
            .andExpect(jsonPath("$.productSelectionInfo").value(DEFAULT_PRODUCT_SELECTION_INFO))
            .andExpect(jsonPath("$.cardNumber").value(DEFAULT_CARD_NUMBER))
            .andExpect(jsonPath("$.authrizationRRN").value(DEFAULT_AUTHRIZATION_RRN))
            .andExpect(jsonPath("$.machineAuthorizationTime").value(sameInstant(DEFAULT_MACHINE_AUTHORIZATION_TIME)))
            .andExpect(jsonPath("$.machineSettlementTime").value(sameInstant(DEFAULT_MACHINE_SETTLEMENT_TIME)))
            .andExpect(jsonPath("$.creditCardType").value(DEFAULT_CREDIT_CARD_TYPE))
            .andExpect(jsonPath("$.cardType").value(DEFAULT_CARD_TYPE))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD))
            .andExpect(jsonPath("$.transactionStatusID").value(DEFAULT_TRANSACTION_STATUS_ID))
            .andExpect(jsonPath("$.transactionTypeID").value(DEFAULT_TRANSACTION_TYPE_ID))
            .andExpect(jsonPath("$.billingProvider").value(DEFAULT_BILLING_PROVIDER))
            .andExpect(jsonPath("$.prepaidCardHolderName").value(DEFAULT_PREPAID_CARD_HOLDER_NAME))
            .andExpect(jsonPath("$.refundRequestBy").value(DEFAULT_REFUND_REQUEST_BY))
            .andExpect(jsonPath("$.refundRequestDate").value(sameInstant(DEFAULT_REFUND_REQUEST_DATE)))
            .andExpect(jsonPath("$.refundReason").value(DEFAULT_REFUND_REASON));
    }

    @Test
    @Transactional
    void getNonExistingNayaxTransactions() throws Exception {
        // Get the nayaxTransactions
        restNayaxTransactionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNayaxTransactions() throws Exception {
        // Initialize the database
        nayaxTransactionsRepository.saveAndFlush(nayaxTransactions);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nayaxTransactions
        NayaxTransactions updatedNayaxTransactions = nayaxTransactionsRepository.findById(nayaxTransactions.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedNayaxTransactions are not directly saved in db
        em.detach(updatedNayaxTransactions);
        updatedNayaxTransactions
            .siteID(UPDATED_SITE_ID)
            .transactionID(UPDATED_TRANSACTION_ID)
            .paymentMethodID(UPDATED_PAYMENT_METHOD_ID)
            .currency(UPDATED_CURRENCY)
            .machineName(UPDATED_MACHINE_NAME)
            .authorizationValue(UPDATED_AUTHORIZATION_VALUE)
            .campaignID(UPDATED_CAMPAIGN_ID)
            .settlementValue(UPDATED_SETTLEMENT_VALUE)
            .productSelectionInfo(UPDATED_PRODUCT_SELECTION_INFO)
            .cardNumber(UPDATED_CARD_NUMBER)
            .authrizationRRN(UPDATED_AUTHRIZATION_RRN)
            .machineAuthorizationTime(UPDATED_MACHINE_AUTHORIZATION_TIME)
            .machineSettlementTime(UPDATED_MACHINE_SETTLEMENT_TIME)
            .creditCardType(UPDATED_CREDIT_CARD_TYPE)
            .cardType(UPDATED_CARD_TYPE)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .transactionStatusID(UPDATED_TRANSACTION_STATUS_ID)
            .transactionTypeID(UPDATED_TRANSACTION_TYPE_ID)
            .billingProvider(UPDATED_BILLING_PROVIDER)
            .prepaidCardHolderName(UPDATED_PREPAID_CARD_HOLDER_NAME)
            .refundRequestBy(UPDATED_REFUND_REQUEST_BY)
            .refundRequestDate(UPDATED_REFUND_REQUEST_DATE)
            .refundReason(UPDATED_REFUND_REASON);

        restNayaxTransactionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNayaxTransactions.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedNayaxTransactions))
            )
            .andExpect(status().isOk());

        // Validate the NayaxTransactions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedNayaxTransactionsToMatchAllProperties(updatedNayaxTransactions);
    }

    @Test
    @Transactional
    void putNonExistingNayaxTransactions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nayaxTransactions.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNayaxTransactionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nayaxTransactions.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(nayaxTransactions))
            )
            .andExpect(status().isBadRequest());

        // Validate the NayaxTransactions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNayaxTransactions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nayaxTransactions.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNayaxTransactionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(nayaxTransactions))
            )
            .andExpect(status().isBadRequest());

        // Validate the NayaxTransactions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNayaxTransactions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nayaxTransactions.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNayaxTransactionsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(nayaxTransactions)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NayaxTransactions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNayaxTransactionsWithPatch() throws Exception {
        // Initialize the database
        nayaxTransactionsRepository.saveAndFlush(nayaxTransactions);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nayaxTransactions using partial update
        NayaxTransactions partialUpdatedNayaxTransactions = new NayaxTransactions();
        partialUpdatedNayaxTransactions.setId(nayaxTransactions.getId());

        partialUpdatedNayaxTransactions
            .machineName(UPDATED_MACHINE_NAME)
            .authorizationValue(UPDATED_AUTHORIZATION_VALUE)
            .campaignID(UPDATED_CAMPAIGN_ID)
            .settlementValue(UPDATED_SETTLEMENT_VALUE)
            .productSelectionInfo(UPDATED_PRODUCT_SELECTION_INFO)
            .cardNumber(UPDATED_CARD_NUMBER)
            .machineSettlementTime(UPDATED_MACHINE_SETTLEMENT_TIME)
            .creditCardType(UPDATED_CREDIT_CARD_TYPE)
            .cardType(UPDATED_CARD_TYPE)
            .transactionTypeID(UPDATED_TRANSACTION_TYPE_ID)
            .prepaidCardHolderName(UPDATED_PREPAID_CARD_HOLDER_NAME)
            .refundRequestDate(UPDATED_REFUND_REQUEST_DATE)
            .refundReason(UPDATED_REFUND_REASON);

        restNayaxTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNayaxTransactions.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNayaxTransactions))
            )
            .andExpect(status().isOk());

        // Validate the NayaxTransactions in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNayaxTransactionsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedNayaxTransactions, nayaxTransactions),
            getPersistedNayaxTransactions(nayaxTransactions)
        );
    }

    @Test
    @Transactional
    void fullUpdateNayaxTransactionsWithPatch() throws Exception {
        // Initialize the database
        nayaxTransactionsRepository.saveAndFlush(nayaxTransactions);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the nayaxTransactions using partial update
        NayaxTransactions partialUpdatedNayaxTransactions = new NayaxTransactions();
        partialUpdatedNayaxTransactions.setId(nayaxTransactions.getId());

        partialUpdatedNayaxTransactions
            .siteID(UPDATED_SITE_ID)
            .transactionID(UPDATED_TRANSACTION_ID)
            .paymentMethodID(UPDATED_PAYMENT_METHOD_ID)
            .currency(UPDATED_CURRENCY)
            .machineName(UPDATED_MACHINE_NAME)
            .authorizationValue(UPDATED_AUTHORIZATION_VALUE)
            .campaignID(UPDATED_CAMPAIGN_ID)
            .settlementValue(UPDATED_SETTLEMENT_VALUE)
            .productSelectionInfo(UPDATED_PRODUCT_SELECTION_INFO)
            .cardNumber(UPDATED_CARD_NUMBER)
            .authrizationRRN(UPDATED_AUTHRIZATION_RRN)
            .machineAuthorizationTime(UPDATED_MACHINE_AUTHORIZATION_TIME)
            .machineSettlementTime(UPDATED_MACHINE_SETTLEMENT_TIME)
            .creditCardType(UPDATED_CREDIT_CARD_TYPE)
            .cardType(UPDATED_CARD_TYPE)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .transactionStatusID(UPDATED_TRANSACTION_STATUS_ID)
            .transactionTypeID(UPDATED_TRANSACTION_TYPE_ID)
            .billingProvider(UPDATED_BILLING_PROVIDER)
            .prepaidCardHolderName(UPDATED_PREPAID_CARD_HOLDER_NAME)
            .refundRequestBy(UPDATED_REFUND_REQUEST_BY)
            .refundRequestDate(UPDATED_REFUND_REQUEST_DATE)
            .refundReason(UPDATED_REFUND_REASON);

        restNayaxTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNayaxTransactions.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedNayaxTransactions))
            )
            .andExpect(status().isOk());

        // Validate the NayaxTransactions in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertNayaxTransactionsUpdatableFieldsEquals(
            partialUpdatedNayaxTransactions,
            getPersistedNayaxTransactions(partialUpdatedNayaxTransactions)
        );
    }

    @Test
    @Transactional
    void patchNonExistingNayaxTransactions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nayaxTransactions.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNayaxTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nayaxTransactions.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(nayaxTransactions))
            )
            .andExpect(status().isBadRequest());

        // Validate the NayaxTransactions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNayaxTransactions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nayaxTransactions.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNayaxTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(nayaxTransactions))
            )
            .andExpect(status().isBadRequest());

        // Validate the NayaxTransactions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNayaxTransactions() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        nayaxTransactions.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNayaxTransactionsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(nayaxTransactions)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NayaxTransactions in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNayaxTransactions() throws Exception {
        // Initialize the database
        nayaxTransactionsRepository.saveAndFlush(nayaxTransactions);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the nayaxTransactions
        restNayaxTransactionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, nayaxTransactions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return nayaxTransactionsRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected NayaxTransactions getPersistedNayaxTransactions(NayaxTransactions nayaxTransactions) {
        return nayaxTransactionsRepository.findById(nayaxTransactions.getId()).orElseThrow();
    }

    protected void assertPersistedNayaxTransactionsToMatchAllProperties(NayaxTransactions expectedNayaxTransactions) {
        assertNayaxTransactionsAllPropertiesEquals(expectedNayaxTransactions, getPersistedNayaxTransactions(expectedNayaxTransactions));
    }

    protected void assertPersistedNayaxTransactionsToMatchUpdatableProperties(NayaxTransactions expectedNayaxTransactions) {
        assertNayaxTransactionsAllUpdatablePropertiesEquals(
            expectedNayaxTransactions,
            getPersistedNayaxTransactions(expectedNayaxTransactions)
        );
    }
}
