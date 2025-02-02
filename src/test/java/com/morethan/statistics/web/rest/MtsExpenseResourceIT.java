package com.morethan.statistics.web.rest;

import static com.morethan.statistics.domain.MtsExpenseAsserts.*;
import static com.morethan.statistics.web.rest.TestUtil.createUpdateProxyForBean;
import static com.morethan.statistics.web.rest.TestUtil.sameInstant;
import static com.morethan.statistics.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.morethan.statistics.IntegrationTest;
import com.morethan.statistics.domain.MtsExpense;
import com.morethan.statistics.repository.MtsExpenseRepository;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Base64;
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
 * Integration tests for the {@link MtsExpenseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MtsExpenseResourceIT {

    private static final ZonedDateTime DEFAULT_EXPENSE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EXPENSE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final BigDecimal DEFAULT_EXPENSE_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXPENSE_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_EXPENSE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EXPENSE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EXPENSE_TYPE_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_EXPENSE_TYPE_DETAIL = "BBBBBBBBBB";

    private static final String DEFAULT_EXPENSE_PAYER = "AAAAAAAAAA";
    private static final String UPDATED_EXPENSE_PAYER = "BBBBBBBBBB";

    private static final String DEFAULT_EXPENSE_RECEIVER = "AAAAAAAAAA";
    private static final String UPDATED_EXPENSE_RECEIVER = "BBBBBBBBBB";

    private static final String DEFAULT_EXPENSE_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_EXPENSE_REMARK = "BBBBBBBBBB";

    private static final byte[] DEFAULT_EXPENSE_RECEIPT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_EXPENSE_RECEIPT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_EXPENSE_RECEIPT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_EXPENSE_RECEIPT_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String DEFAULT_MODIFIER = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_MODIFY_DATETIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFY_DATETIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATOR = "AAAAAAAAAA";
    private static final String UPDATED_CREATOR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_DATETIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_DATETIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/mts-expenses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MtsExpenseRepository mtsExpenseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMtsExpenseMockMvc;

    private MtsExpense mtsExpense;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MtsExpense createEntity(EntityManager em) {
        MtsExpense mtsExpense = new MtsExpense()
            .expenseDate(DEFAULT_EXPENSE_DATE)
            .expenseAmount(DEFAULT_EXPENSE_AMOUNT)
            .expenseType(DEFAULT_EXPENSE_TYPE)
            .expenseTypeDetail(DEFAULT_EXPENSE_TYPE_DETAIL)
            .expensePayer(DEFAULT_EXPENSE_PAYER)
            .expenseReceiver(DEFAULT_EXPENSE_RECEIVER)
            .expenseRemark(DEFAULT_EXPENSE_REMARK)
            .expenseReceipt(DEFAULT_EXPENSE_RECEIPT)
            .expenseReceiptContentType(DEFAULT_EXPENSE_RECEIPT_CONTENT_TYPE)
            .isActive(DEFAULT_IS_ACTIVE)
            .modifier(DEFAULT_MODIFIER)
            .modifyDatetime(DEFAULT_MODIFY_DATETIME)
            .creator(DEFAULT_CREATOR)
            .createDatetime(DEFAULT_CREATE_DATETIME);
        return mtsExpense;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MtsExpense createUpdatedEntity(EntityManager em) {
        MtsExpense mtsExpense = new MtsExpense()
            .expenseDate(UPDATED_EXPENSE_DATE)
            .expenseAmount(UPDATED_EXPENSE_AMOUNT)
            .expenseType(UPDATED_EXPENSE_TYPE)
            .expenseTypeDetail(UPDATED_EXPENSE_TYPE_DETAIL)
            .expensePayer(UPDATED_EXPENSE_PAYER)
            .expenseReceiver(UPDATED_EXPENSE_RECEIVER)
            .expenseRemark(UPDATED_EXPENSE_REMARK)
            .expenseReceipt(UPDATED_EXPENSE_RECEIPT)
            .expenseReceiptContentType(UPDATED_EXPENSE_RECEIPT_CONTENT_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .modifier(UPDATED_MODIFIER)
            .modifyDatetime(UPDATED_MODIFY_DATETIME)
            .creator(UPDATED_CREATOR)
            .createDatetime(UPDATED_CREATE_DATETIME);
        return mtsExpense;
    }

    @BeforeEach
    public void initTest() {
        mtsExpense = createEntity(em);
    }

    @Test
    @Transactional
    void createMtsExpense() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MtsExpense
        var returnedMtsExpense = om.readValue(
            restMtsExpenseMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mtsExpense)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MtsExpense.class
        );

        // Validate the MtsExpense in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMtsExpenseUpdatableFieldsEquals(returnedMtsExpense, getPersistedMtsExpense(returnedMtsExpense));
    }

    @Test
    @Transactional
    void createMtsExpenseWithExistingId() throws Exception {
        // Create the MtsExpense with an existing ID
        mtsExpense.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMtsExpenseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mtsExpense)))
            .andExpect(status().isBadRequest());

        // Validate the MtsExpense in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkExpenseDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        mtsExpense.setExpenseDate(null);

        // Create the MtsExpense, which fails.

        restMtsExpenseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mtsExpense)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkExpenseAmountIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        mtsExpense.setExpenseAmount(null);

        // Create the MtsExpense, which fails.

        restMtsExpenseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mtsExpense)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMtsExpenses() throws Exception {
        // Initialize the database
        mtsExpenseRepository.saveAndFlush(mtsExpense);

        // Get all the mtsExpenseList
        restMtsExpenseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mtsExpense.getId().intValue())))
            .andExpect(jsonPath("$.[*].expenseDate").value(hasItem(sameInstant(DEFAULT_EXPENSE_DATE))))
            .andExpect(jsonPath("$.[*].expenseAmount").value(hasItem(sameNumber(DEFAULT_EXPENSE_AMOUNT))))
            .andExpect(jsonPath("$.[*].expenseType").value(hasItem(DEFAULT_EXPENSE_TYPE)))
            .andExpect(jsonPath("$.[*].expenseTypeDetail").value(hasItem(DEFAULT_EXPENSE_TYPE_DETAIL)))
            .andExpect(jsonPath("$.[*].expensePayer").value(hasItem(DEFAULT_EXPENSE_PAYER)))
            .andExpect(jsonPath("$.[*].expenseReceiver").value(hasItem(DEFAULT_EXPENSE_RECEIVER)))
            .andExpect(jsonPath("$.[*].expenseRemark").value(hasItem(DEFAULT_EXPENSE_REMARK)))
            .andExpect(jsonPath("$.[*].expenseReceiptContentType").value(hasItem(DEFAULT_EXPENSE_RECEIPT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].expenseReceipt").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_EXPENSE_RECEIPT))))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].modifier").value(hasItem(DEFAULT_MODIFIER)))
            .andExpect(jsonPath("$.[*].modifyDatetime").value(hasItem(sameInstant(DEFAULT_MODIFY_DATETIME))))
            .andExpect(jsonPath("$.[*].creator").value(hasItem(DEFAULT_CREATOR)))
            .andExpect(jsonPath("$.[*].createDatetime").value(hasItem(sameInstant(DEFAULT_CREATE_DATETIME))));
    }

    @Test
    @Transactional
    void getMtsExpense() throws Exception {
        // Initialize the database
        mtsExpenseRepository.saveAndFlush(mtsExpense);

        // Get the mtsExpense
        restMtsExpenseMockMvc
            .perform(get(ENTITY_API_URL_ID, mtsExpense.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mtsExpense.getId().intValue()))
            .andExpect(jsonPath("$.expenseDate").value(sameInstant(DEFAULT_EXPENSE_DATE)))
            .andExpect(jsonPath("$.expenseAmount").value(sameNumber(DEFAULT_EXPENSE_AMOUNT)))
            .andExpect(jsonPath("$.expenseType").value(DEFAULT_EXPENSE_TYPE))
            .andExpect(jsonPath("$.expenseTypeDetail").value(DEFAULT_EXPENSE_TYPE_DETAIL))
            .andExpect(jsonPath("$.expensePayer").value(DEFAULT_EXPENSE_PAYER))
            .andExpect(jsonPath("$.expenseReceiver").value(DEFAULT_EXPENSE_RECEIVER))
            .andExpect(jsonPath("$.expenseRemark").value(DEFAULT_EXPENSE_REMARK))
            .andExpect(jsonPath("$.expenseReceiptContentType").value(DEFAULT_EXPENSE_RECEIPT_CONTENT_TYPE))
            .andExpect(jsonPath("$.expenseReceipt").value(Base64.getEncoder().encodeToString(DEFAULT_EXPENSE_RECEIPT)))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.modifier").value(DEFAULT_MODIFIER))
            .andExpect(jsonPath("$.modifyDatetime").value(sameInstant(DEFAULT_MODIFY_DATETIME)))
            .andExpect(jsonPath("$.creator").value(DEFAULT_CREATOR))
            .andExpect(jsonPath("$.createDatetime").value(sameInstant(DEFAULT_CREATE_DATETIME)));
    }

    @Test
    @Transactional
    void getNonExistingMtsExpense() throws Exception {
        // Get the mtsExpense
        restMtsExpenseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMtsExpense() throws Exception {
        // Initialize the database
        mtsExpenseRepository.saveAndFlush(mtsExpense);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mtsExpense
        MtsExpense updatedMtsExpense = mtsExpenseRepository.findById(mtsExpense.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMtsExpense are not directly saved in db
        em.detach(updatedMtsExpense);
        updatedMtsExpense
            .expenseDate(UPDATED_EXPENSE_DATE)
            .expenseAmount(UPDATED_EXPENSE_AMOUNT)
            .expenseType(UPDATED_EXPENSE_TYPE)
            .expenseTypeDetail(UPDATED_EXPENSE_TYPE_DETAIL)
            .expensePayer(UPDATED_EXPENSE_PAYER)
            .expenseReceiver(UPDATED_EXPENSE_RECEIVER)
            .expenseRemark(UPDATED_EXPENSE_REMARK)
            .expenseReceipt(UPDATED_EXPENSE_RECEIPT)
            .expenseReceiptContentType(UPDATED_EXPENSE_RECEIPT_CONTENT_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .modifier(UPDATED_MODIFIER)
            .modifyDatetime(UPDATED_MODIFY_DATETIME)
            .creator(UPDATED_CREATOR)
            .createDatetime(UPDATED_CREATE_DATETIME);

        restMtsExpenseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMtsExpense.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMtsExpense))
            )
            .andExpect(status().isOk());

        // Validate the MtsExpense in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMtsExpenseToMatchAllProperties(updatedMtsExpense);
    }

    @Test
    @Transactional
    void putNonExistingMtsExpense() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mtsExpense.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMtsExpenseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mtsExpense.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mtsExpense))
            )
            .andExpect(status().isBadRequest());

        // Validate the MtsExpense in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMtsExpense() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mtsExpense.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMtsExpenseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(mtsExpense))
            )
            .andExpect(status().isBadRequest());

        // Validate the MtsExpense in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMtsExpense() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mtsExpense.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMtsExpenseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mtsExpense)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MtsExpense in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMtsExpenseWithPatch() throws Exception {
        // Initialize the database
        mtsExpenseRepository.saveAndFlush(mtsExpense);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mtsExpense using partial update
        MtsExpense partialUpdatedMtsExpense = new MtsExpense();
        partialUpdatedMtsExpense.setId(mtsExpense.getId());

        partialUpdatedMtsExpense
            .expenseDate(UPDATED_EXPENSE_DATE)
            .expensePayer(UPDATED_EXPENSE_PAYER)
            .expenseRemark(UPDATED_EXPENSE_REMARK)
            .expenseReceipt(UPDATED_EXPENSE_RECEIPT)
            .expenseReceiptContentType(UPDATED_EXPENSE_RECEIPT_CONTENT_TYPE)
            .modifyDatetime(UPDATED_MODIFY_DATETIME)
            .creator(UPDATED_CREATOR);

        restMtsExpenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMtsExpense.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMtsExpense))
            )
            .andExpect(status().isOk());

        // Validate the MtsExpense in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMtsExpenseUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMtsExpense, mtsExpense),
            getPersistedMtsExpense(mtsExpense)
        );
    }

    @Test
    @Transactional
    void fullUpdateMtsExpenseWithPatch() throws Exception {
        // Initialize the database
        mtsExpenseRepository.saveAndFlush(mtsExpense);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mtsExpense using partial update
        MtsExpense partialUpdatedMtsExpense = new MtsExpense();
        partialUpdatedMtsExpense.setId(mtsExpense.getId());

        partialUpdatedMtsExpense
            .expenseDate(UPDATED_EXPENSE_DATE)
            .expenseAmount(UPDATED_EXPENSE_AMOUNT)
            .expenseType(UPDATED_EXPENSE_TYPE)
            .expenseTypeDetail(UPDATED_EXPENSE_TYPE_DETAIL)
            .expensePayer(UPDATED_EXPENSE_PAYER)
            .expenseReceiver(UPDATED_EXPENSE_RECEIVER)
            .expenseRemark(UPDATED_EXPENSE_REMARK)
            .expenseReceipt(UPDATED_EXPENSE_RECEIPT)
            .expenseReceiptContentType(UPDATED_EXPENSE_RECEIPT_CONTENT_TYPE)
            .isActive(UPDATED_IS_ACTIVE)
            .modifier(UPDATED_MODIFIER)
            .modifyDatetime(UPDATED_MODIFY_DATETIME)
            .creator(UPDATED_CREATOR)
            .createDatetime(UPDATED_CREATE_DATETIME);

        restMtsExpenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMtsExpense.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMtsExpense))
            )
            .andExpect(status().isOk());

        // Validate the MtsExpense in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMtsExpenseUpdatableFieldsEquals(partialUpdatedMtsExpense, getPersistedMtsExpense(partialUpdatedMtsExpense));
    }

    @Test
    @Transactional
    void patchNonExistingMtsExpense() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mtsExpense.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMtsExpenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mtsExpense.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(mtsExpense))
            )
            .andExpect(status().isBadRequest());

        // Validate the MtsExpense in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMtsExpense() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mtsExpense.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMtsExpenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(mtsExpense))
            )
            .andExpect(status().isBadRequest());

        // Validate the MtsExpense in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMtsExpense() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mtsExpense.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMtsExpenseMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(mtsExpense)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MtsExpense in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMtsExpense() throws Exception {
        // Initialize the database
        mtsExpenseRepository.saveAndFlush(mtsExpense);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the mtsExpense
        restMtsExpenseMockMvc
            .perform(delete(ENTITY_API_URL_ID, mtsExpense.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return mtsExpenseRepository.count();
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

    protected MtsExpense getPersistedMtsExpense(MtsExpense mtsExpense) {
        return mtsExpenseRepository.findById(mtsExpense.getId()).orElseThrow();
    }

    protected void assertPersistedMtsExpenseToMatchAllProperties(MtsExpense expectedMtsExpense) {
        assertMtsExpenseAllPropertiesEquals(expectedMtsExpense, getPersistedMtsExpense(expectedMtsExpense));
    }

    protected void assertPersistedMtsExpenseToMatchUpdatableProperties(MtsExpense expectedMtsExpense) {
        assertMtsExpenseAllUpdatablePropertiesEquals(expectedMtsExpense, getPersistedMtsExpense(expectedMtsExpense));
    }
}
