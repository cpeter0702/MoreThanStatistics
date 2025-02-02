package com.morethan.statistics.web.rest;

import com.morethan.statistics.domain.MtsIncome;
import com.morethan.statistics.repository.MtsIncomeRepository;
import com.morethan.statistics.service.MtsIncomeService;
import com.morethan.statistics.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.morethan.statistics.domain.MtsIncome}.
 */
@RestController
@RequestMapping("/api/mts-incomes")
public class MtsIncomeResource {

    private final Logger log = LoggerFactory.getLogger(MtsIncomeResource.class);

    private static final String ENTITY_NAME = "mtsIncome";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MtsIncomeService mtsIncomeService;

    private final MtsIncomeRepository mtsIncomeRepository;

    public MtsIncomeResource(MtsIncomeService mtsIncomeService, MtsIncomeRepository mtsIncomeRepository) {
        this.mtsIncomeService = mtsIncomeService;
        this.mtsIncomeRepository = mtsIncomeRepository;
    }

    /**
     * {@code POST  /mts-incomes} : Create a new mtsIncome.
     *
     * @param mtsIncome the mtsIncome to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mtsIncome, or with status {@code 400 (Bad Request)} if the mtsIncome has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MtsIncome> createMtsIncome(@Valid @RequestBody MtsIncome mtsIncome) throws URISyntaxException {
        log.debug("REST request to save MtsIncome : {}", mtsIncome);
        if (mtsIncome.getId() != null) {
            throw new BadRequestAlertException("A new mtsIncome cannot already have an ID", ENTITY_NAME, "idexists");
        }
        mtsIncome = mtsIncomeService.save(mtsIncome);
        return ResponseEntity.created(new URI("/api/mts-incomes/" + mtsIncome.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, mtsIncome.getId().toString()))
            .body(mtsIncome);
    }

    /**
     * {@code PUT  /mts-incomes/:id} : Updates an existing mtsIncome.
     *
     * @param id the id of the mtsIncome to save.
     * @param mtsIncome the mtsIncome to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mtsIncome,
     * or with status {@code 400 (Bad Request)} if the mtsIncome is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mtsIncome couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MtsIncome> updateMtsIncome(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MtsIncome mtsIncome
    ) throws URISyntaxException {
        log.debug("REST request to update MtsIncome : {}, {}", id, mtsIncome);
        if (mtsIncome.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mtsIncome.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mtsIncomeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        mtsIncome = mtsIncomeService.update(mtsIncome);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mtsIncome.getId().toString()))
            .body(mtsIncome);
    }

    /**
     * {@code PATCH  /mts-incomes/:id} : Partial updates given fields of an existing mtsIncome, field will ignore if it is null
     *
     * @param id the id of the mtsIncome to save.
     * @param mtsIncome the mtsIncome to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mtsIncome,
     * or with status {@code 400 (Bad Request)} if the mtsIncome is not valid,
     * or with status {@code 404 (Not Found)} if the mtsIncome is not found,
     * or with status {@code 500 (Internal Server Error)} if the mtsIncome couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MtsIncome> partialUpdateMtsIncome(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MtsIncome mtsIncome
    ) throws URISyntaxException {
        log.debug("REST request to partial update MtsIncome partially : {}, {}", id, mtsIncome);
        if (mtsIncome.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mtsIncome.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mtsIncomeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MtsIncome> result = mtsIncomeService.partialUpdate(mtsIncome);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mtsIncome.getId().toString())
        );
    }

    /**
     * {@code GET  /mts-incomes} : get all the mtsIncomes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mtsIncomes in body.
     */
    @GetMapping("")
    public List<MtsIncome> getAllMtsIncomes() {
        log.debug("REST request to get all MtsIncomes");
        return mtsIncomeService.findAll();
    }

    /**
     * {@code GET  /mts-incomes/:id} : get the "id" mtsIncome.
     *
     * @param id the id of the mtsIncome to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mtsIncome, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MtsIncome> getMtsIncome(@PathVariable("id") Long id) {
        log.debug("REST request to get MtsIncome : {}", id);
        Optional<MtsIncome> mtsIncome = mtsIncomeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mtsIncome);
    }

    /**
     * {@code DELETE  /mts-incomes/:id} : delete the "id" mtsIncome.
     *
     * @param id the id of the mtsIncome to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMtsIncome(@PathVariable("id") Long id) {
        log.debug("REST request to delete MtsIncome : {}", id);
        mtsIncomeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
