package com.morethan.statistics.web.rest;

import static com.morethan.statistics.domain.MtsIncomeAsserts.*;
import static com.morethan.statistics.web.rest.TestUtil.createUpdateProxyForBean;
import static com.morethan.statistics.web.rest.TestUtil.sameInstant;
import static com.morethan.statistics.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.morethan.statistics.IntegrationTest;
import com.morethan.statistics.domain.MtsIncome;
import com.morethan.statistics.repository.MtsIncomeRepository;
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
 * Integration tests for the {@link MtsIncomeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MtsIncomeResourceIT {

    private static final ZonedDateTime DEFAULT_INCOME_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_INCOME_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final BigDecimal DEFAULT_INCOME_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_INCOME_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_INCOME_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_INCOME_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_INCOME_TYPE_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_INCOME_TYPE_DETAIL = "BBBBBBBBBB";

    private static final String DEFAULT_INCOME_PAYER = "AAAAAAAAAA";
    private static final String UPDATED_INCOME_PAYER = "BBBBBBBBBB";

    private static final String DEFAULT_INCOME_RECEIVER = "AAAAAAAAAA";
    private static final String UPDATED_INCOME_RECEIVER = "BBBBBBBBBB";

    private static final String DEFAULT_INCOME_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_INCOME_REMARK = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/mts-incomes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MtsIncomeRepository mtsIncomeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMtsIncomeMockMvc;

    private MtsIncome mtsIncome;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MtsIncome createEntity(EntityManager em) {
        MtsIncome mtsIncome = new MtsIncome()
            .incomeDate(DEFAULT_INCOME_DATE)
            .incomeAmount(DEFAULT_INCOME_AMOUNT)
            .incomeType(DEFAULT_INCOME_TYPE)
            .incomeTypeDetail(DEFAULT_INCOME_TYPE_DETAIL)
            .incomePayer(DEFAULT_INCOME_PAYER)
            .incomeReceiver(DEFAULT_INCOME_RECEIVER)
            .incomeRemark(DEFAULT_INCOME_REMARK)
            .isActive(DEFAULT_IS_ACTIVE)
            .modifier(DEFAULT_MODIFIER)
            .modifyDatetime(DEFAULT_MODIFY_DATETIME)
            .creator(DEFAULT_CREATOR)
            .createDatetime(DEFAULT_CREATE_DATETIME);
        return mtsIncome;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MtsIncome createUpdatedEntity(EntityManager em) {
        MtsIncome mtsIncome = new MtsIncome()
            .incomeDate(UPDATED_INCOME_DATE)
            .incomeAmount(UPDATED_INCOME_AMOUNT)
            .incomeType(UPDATED_INCOME_TYPE)
            .incomeTypeDetail(UPDATED_INCOME_TYPE_DETAIL)
            .incomePayer(UPDATED_INCOME_PAYER)
            .incomeReceiver(UPDATED_INCOME_RECEIVER)
            .incomeRemark(UPDATED_INCOME_REMARK)
            .isActive(UPDATED_IS_ACTIVE)
            .modifier(UPDATED_MODIFIER)
            .modifyDatetime(UPDATED_MODIFY_DATETIME)
            .creator(UPDATED_CREATOR)
            .createDatetime(UPDATED_CREATE_DATETIME);
        return mtsIncome;
    }

    @BeforeEach
    public void initTest() {
        mtsIncome = createEntity(em);
    }

    @Test
    @Transactional
    void createMtsIncome() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MtsIncome
        var returnedMtsIncome = om.readValue(
            restMtsIncomeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mtsIncome)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MtsIncome.class
        );

        // Validate the MtsIncome in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertMtsIncomeUpdatableFieldsEquals(returnedMtsIncome, getPersistedMtsIncome(returnedMtsIncome));
    }

    @Test
    @Transactional
    void createMtsIncomeWithExistingId() throws Exception {
        // Create the MtsIncome with an existing ID
        mtsIncome.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMtsIncomeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mtsIncome)))
            .andExpect(status().isBadRequest());

        // Validate the MtsIncome in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIncomeDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        mtsIncome.setIncomeDate(null);

        // Create the MtsIncome, which fails.

        restMtsIncomeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mtsIncome)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIncomeAmountIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        mtsIncome.setIncomeAmount(null);

        // Create the MtsIncome, which fails.

        restMtsIncomeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mtsIncome)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMtsIncomes() throws Exception {
        // Initialize the database
        mtsIncomeRepository.saveAndFlush(mtsIncome);

        // Get all the mtsIncomeList
        restMtsIncomeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mtsIncome.getId().intValue())))
            .andExpect(jsonPath("$.[*].incomeDate").value(hasItem(sameInstant(DEFAULT_INCOME_DATE))))
            .andExpect(jsonPath("$.[*].incomeAmount").value(hasItem(sameNumber(DEFAULT_INCOME_AMOUNT))))
            .andExpect(jsonPath("$.[*].incomeType").value(hasItem(DEFAULT_INCOME_TYPE)))
            .andExpect(jsonPath("$.[*].incomeTypeDetail").value(hasItem(DEFAULT_INCOME_TYPE_DETAIL)))
            .andExpect(jsonPath("$.[*].incomePayer").value(hasItem(DEFAULT_INCOME_PAYER)))
            .andExpect(jsonPath("$.[*].incomeReceiver").value(hasItem(DEFAULT_INCOME_RECEIVER)))
            .andExpect(jsonPath("$.[*].incomeRemark").value(hasItem(DEFAULT_INCOME_REMARK)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].modifier").value(hasItem(DEFAULT_MODIFIER)))
            .andExpect(jsonPath("$.[*].modifyDatetime").value(hasItem(sameInstant(DEFAULT_MODIFY_DATETIME))))
            .andExpect(jsonPath("$.[*].creator").value(hasItem(DEFAULT_CREATOR)))
            .andExpect(jsonPath("$.[*].createDatetime").value(hasItem(sameInstant(DEFAULT_CREATE_DATETIME))));
    }

    @Test
    @Transactional
    void getMtsIncome() throws Exception {
        // Initialize the database
        mtsIncomeRepository.saveAndFlush(mtsIncome);

        // Get the mtsIncome
        restMtsIncomeMockMvc
            .perform(get(ENTITY_API_URL_ID, mtsIncome.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mtsIncome.getId().intValue()))
            .andExpect(jsonPath("$.incomeDate").value(sameInstant(DEFAULT_INCOME_DATE)))
            .andExpect(jsonPath("$.incomeAmount").value(sameNumber(DEFAULT_INCOME_AMOUNT)))
            .andExpect(jsonPath("$.incomeType").value(DEFAULT_INCOME_TYPE))
            .andExpect(jsonPath("$.incomeTypeDetail").value(DEFAULT_INCOME_TYPE_DETAIL))
            .andExpect(jsonPath("$.incomePayer").value(DEFAULT_INCOME_PAYER))
            .andExpect(jsonPath("$.incomeReceiver").value(DEFAULT_INCOME_RECEIVER))
            .andExpect(jsonPath("$.incomeRemark").value(DEFAULT_INCOME_REMARK))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.modifier").value(DEFAULT_MODIFIER))
            .andExpect(jsonPath("$.modifyDatetime").value(sameInstant(DEFAULT_MODIFY_DATETIME)))
            .andExpect(jsonPath("$.creator").value(DEFAULT_CREATOR))
            .andExpect(jsonPath("$.createDatetime").value(sameInstant(DEFAULT_CREATE_DATETIME)));
    }

    @Test
    @Transactional
    void getNonExistingMtsIncome() throws Exception {
        // Get the mtsIncome
        restMtsIncomeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMtsIncome() throws Exception {
        // Initialize the database
        mtsIncomeRepository.saveAndFlush(mtsIncome);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mtsIncome
        MtsIncome updatedMtsIncome = mtsIncomeRepository.findById(mtsIncome.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMtsIncome are not directly saved in db
        em.detach(updatedMtsIncome);
        updatedMtsIncome
            .incomeDate(UPDATED_INCOME_DATE)
            .incomeAmount(UPDATED_INCOME_AMOUNT)
            .incomeType(UPDATED_INCOME_TYPE)
            .incomeTypeDetail(UPDATED_INCOME_TYPE_DETAIL)
            .incomePayer(UPDATED_INCOME_PAYER)
            .incomeReceiver(UPDATED_INCOME_RECEIVER)
            .incomeRemark(UPDATED_INCOME_REMARK)
            .isActive(UPDATED_IS_ACTIVE)
            .modifier(UPDATED_MODIFIER)
            .modifyDatetime(UPDATED_MODIFY_DATETIME)
            .creator(UPDATED_CREATOR)
            .createDatetime(UPDATED_CREATE_DATETIME);

        restMtsIncomeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMtsIncome.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedMtsIncome))
            )
            .andExpect(status().isOk());

        // Validate the MtsIncome in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMtsIncomeToMatchAllProperties(updatedMtsIncome);
    }

    @Test
    @Transactional
    void putNonExistingMtsIncome() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mtsIncome.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMtsIncomeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mtsIncome.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mtsIncome))
            )
            .andExpect(status().isBadRequest());

        // Validate the MtsIncome in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMtsIncome() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mtsIncome.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMtsIncomeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(mtsIncome))
            )
            .andExpect(status().isBadRequest());

        // Validate the MtsIncome in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMtsIncome() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mtsIncome.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMtsIncomeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mtsIncome)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MtsIncome in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMtsIncomeWithPatch() throws Exception {
        // Initialize the database
        mtsIncomeRepository.saveAndFlush(mtsIncome);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mtsIncome using partial update
        MtsIncome partialUpdatedMtsIncome = new MtsIncome();
        partialUpdatedMtsIncome.setId(mtsIncome.getId());

        partialUpdatedMtsIncome
            .incomeType(UPDATED_INCOME_TYPE)
            .incomeTypeDetail(UPDATED_INCOME_TYPE_DETAIL)
            .incomePayer(UPDATED_INCOME_PAYER)
            .incomeRemark(UPDATED_INCOME_REMARK)
            .isActive(UPDATED_IS_ACTIVE)
            .modifyDatetime(UPDATED_MODIFY_DATETIME)
            .createDatetime(UPDATED_CREATE_DATETIME);

        restMtsIncomeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMtsIncome.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMtsIncome))
            )
            .andExpect(status().isOk());

        // Validate the MtsIncome in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMtsIncomeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMtsIncome, mtsIncome),
            getPersistedMtsIncome(mtsIncome)
        );
    }

    @Test
    @Transactional
    void fullUpdateMtsIncomeWithPatch() throws Exception {
        // Initialize the database
        mtsIncomeRepository.saveAndFlush(mtsIncome);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mtsIncome using partial update
        MtsIncome partialUpdatedMtsIncome = new MtsIncome();
        partialUpdatedMtsIncome.setId(mtsIncome.getId());

        partialUpdatedMtsIncome
            .incomeDate(UPDATED_INCOME_DATE)
            .incomeAmount(UPDATED_INCOME_AMOUNT)
            .incomeType(UPDATED_INCOME_TYPE)
            .incomeTypeDetail(UPDATED_INCOME_TYPE_DETAIL)
            .incomePayer(UPDATED_INCOME_PAYER)
            .incomeReceiver(UPDATED_INCOME_RECEIVER)
            .incomeRemark(UPDATED_INCOME_REMARK)
            .isActive(UPDATED_IS_ACTIVE)
            .modifier(UPDATED_MODIFIER)
            .modifyDatetime(UPDATED_MODIFY_DATETIME)
            .creator(UPDATED_CREATOR)
            .createDatetime(UPDATED_CREATE_DATETIME);

        restMtsIncomeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMtsIncome.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMtsIncome))
            )
            .andExpect(status().isOk());

        // Validate the MtsIncome in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMtsIncomeUpdatableFieldsEquals(partialUpdatedMtsIncome, getPersistedMtsIncome(partialUpdatedMtsIncome));
    }

    @Test
    @Transactional
    void patchNonExistingMtsIncome() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mtsIncome.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMtsIncomeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mtsIncome.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(mtsIncome))
            )
            .andExpect(status().isBadRequest());

        // Validate the MtsIncome in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMtsIncome() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mtsIncome.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMtsIncomeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(mtsIncome))
            )
            .andExpect(status().isBadRequest());

        // Validate the MtsIncome in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMtsIncome() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mtsIncome.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMtsIncomeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(mtsIncome)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MtsIncome in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMtsIncome() throws Exception {
        // Initialize the database
        mtsIncomeRepository.saveAndFlush(mtsIncome);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the mtsIncome
        restMtsIncomeMockMvc
            .perform(delete(ENTITY_API_URL_ID, mtsIncome.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return mtsIncomeRepository.count();
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

    protected MtsIncome getPersistedMtsIncome(MtsIncome mtsIncome) {
        return mtsIncomeRepository.findById(mtsIncome.getId()).orElseThrow();
    }

    protected void assertPersistedMtsIncomeToMatchAllProperties(MtsIncome expectedMtsIncome) {
        assertMtsIncomeAllPropertiesEquals(expectedMtsIncome, getPersistedMtsIncome(expectedMtsIncome));
    }

    protected void assertPersistedMtsIncomeToMatchUpdatableProperties(MtsIncome expectedMtsIncome) {
        assertMtsIncomeAllUpdatablePropertiesEquals(expectedMtsIncome, getPersistedMtsIncome(expectedMtsIncome));
    }
}
