package com.morethan.statistics.web.rest;

import com.morethan.statistics.domain.NayaxTransactions;
import com.morethan.statistics.repository.NayaxTransactionsRepository;
import com.morethan.statistics.service.NayaxTransactionsService;
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
 * REST controller for managing {@link com.morethan.statistics.domain.NayaxTransactions}.
 */
@RestController
@RequestMapping("/api/nayax-transactions")
public class NayaxTransactionsResource {

    private final Logger log = LoggerFactory.getLogger(NayaxTransactionsResource.class);

    private static final String ENTITY_NAME = "nayaxTransactions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NayaxTransactionsService nayaxTransactionsService;

    private final NayaxTransactionsRepository nayaxTransactionsRepository;

    public NayaxTransactionsResource(
        NayaxTransactionsService nayaxTransactionsService,
        NayaxTransactionsRepository nayaxTransactionsRepository
    ) {
        this.nayaxTransactionsService = nayaxTransactionsService;
        this.nayaxTransactionsRepository = nayaxTransactionsRepository;
    }

    /**
     * {@code POST  /nayax-transactions} : Create a new nayaxTransactions.
     *
     * @param nayaxTransactions the nayaxTransactions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nayaxTransactions, or with status {@code 400 (Bad Request)} if the nayaxTransactions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<NayaxTransactions> createNayaxTransactions(@Valid @RequestBody NayaxTransactions nayaxTransactions)
        throws URISyntaxException {
        log.debug("REST request to save NayaxTransactions : {}", nayaxTransactions);
        if (nayaxTransactions.getId() != null) {
            throw new BadRequestAlertException("A new nayaxTransactions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        nayaxTransactions = nayaxTransactionsService.save(nayaxTransactions);
        return ResponseEntity.created(new URI("/api/nayax-transactions/" + nayaxTransactions.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, nayaxTransactions.getId().toString()))
            .body(nayaxTransactions);
    }

    /**
     * {@code PUT  /nayax-transactions/:id} : Updates an existing nayaxTransactions.
     *
     * @param id the id of the nayaxTransactions to save.
     * @param nayaxTransactions the nayaxTransactions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nayaxTransactions,
     * or with status {@code 400 (Bad Request)} if the nayaxTransactions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nayaxTransactions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NayaxTransactions> updateNayaxTransactions(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NayaxTransactions nayaxTransactions
    ) throws URISyntaxException {
        log.debug("REST request to update NayaxTransactions : {}, {}", id, nayaxTransactions);
        if (nayaxTransactions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nayaxTransactions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nayaxTransactionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        nayaxTransactions = nayaxTransactionsService.update(nayaxTransactions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nayaxTransactions.getId().toString()))
            .body(nayaxTransactions);
    }

    /**
     * {@code PATCH  /nayax-transactions/:id} : Partial updates given fields of an existing nayaxTransactions, field will ignore if it is null
     *
     * @param id the id of the nayaxTransactions to save.
     * @param nayaxTransactions the nayaxTransactions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nayaxTransactions,
     * or with status {@code 400 (Bad Request)} if the nayaxTransactions is not valid,
     * or with status {@code 404 (Not Found)} if the nayaxTransactions is not found,
     * or with status {@code 500 (Internal Server Error)} if the nayaxTransactions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NayaxTransactions> partialUpdateNayaxTransactions(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NayaxTransactions nayaxTransactions
    ) throws URISyntaxException {
        log.debug("REST request to partial update NayaxTransactions partially : {}, {}", id, nayaxTransactions);
        if (nayaxTransactions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nayaxTransactions.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nayaxTransactionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NayaxTransactions> result = nayaxTransactionsService.partialUpdate(nayaxTransactions);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nayaxTransactions.getId().toString())
        );
    }

    /**
     * {@code GET  /nayax-transactions} : get all the nayaxTransactions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nayaxTransactions in body.
     */
    @GetMapping("")
    public List<NayaxTransactions> getAllNayaxTransactions() {
        log.debug("REST request to get all NayaxTransactions");
        return nayaxTransactionsService.findAll();
    }

    /**
     * {@code GET  /nayax-transactions/:id} : get the "id" nayaxTransactions.
     *
     * @param id the id of the nayaxTransactions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nayaxTransactions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NayaxTransactions> getNayaxTransactions(@PathVariable("id") Long id) {
        log.debug("REST request to get NayaxTransactions : {}", id);
        Optional<NayaxTransactions> nayaxTransactions = nayaxTransactionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nayaxTransactions);
    }

    /**
     * {@code DELETE  /nayax-transactions/:id} : delete the "id" nayaxTransactions.
     *
     * @param id the id of the nayaxTransactions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNayaxTransactions(@PathVariable("id") Long id) {
        log.debug("REST request to delete NayaxTransactions : {}", id);
        nayaxTransactionsService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
