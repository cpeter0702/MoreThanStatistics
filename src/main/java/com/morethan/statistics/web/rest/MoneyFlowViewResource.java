package com.morethan.statistics.web.rest;

import com.morethan.statistics.domain.MoneyFlowView;
import com.morethan.statistics.repository.MoneyFlowViewRepository;
import com.morethan.statistics.service.MoneyFlowViewService;
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
 * REST controller for managing {@link com.morethan.statistics.domain.MoneyFlowView}.
 */
@RestController
@RequestMapping("/api/money-flow-views")
public class MoneyFlowViewResource {

    private final Logger log = LoggerFactory.getLogger(MoneyFlowViewResource.class);

    private static final String ENTITY_NAME = "moneyFlowView";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MoneyFlowViewService moneyFlowViewService;

    private final MoneyFlowViewRepository moneyFlowViewRepository;

    public MoneyFlowViewResource(MoneyFlowViewService moneyFlowViewService, MoneyFlowViewRepository moneyFlowViewRepository) {
        this.moneyFlowViewService = moneyFlowViewService;
        this.moneyFlowViewRepository = moneyFlowViewRepository;
    }

    /**
     * {@code POST  /money-flow-views} : Create a new moneyFlowView.
     *
     * @param moneyFlowView the moneyFlowView to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new moneyFlowView, or with status {@code 400 (Bad Request)} if the moneyFlowView has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MoneyFlowView> createMoneyFlowView(@Valid @RequestBody MoneyFlowView moneyFlowView) throws URISyntaxException {
        log.debug("REST request to save MoneyFlowView : {}", moneyFlowView);
        if (moneyFlowView.getId() != null) {
            throw new BadRequestAlertException("A new moneyFlowView cannot already have an ID", ENTITY_NAME, "idexists");
        }
        moneyFlowView = moneyFlowViewService.save(moneyFlowView);
        return ResponseEntity.created(new URI("/api/money-flow-views/" + moneyFlowView.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, moneyFlowView.getId().toString()))
            .body(moneyFlowView);
    }

    /**
     * {@code PUT  /money-flow-views/:id} : Updates an existing moneyFlowView.
     *
     * @param id the id of the moneyFlowView to save.
     * @param moneyFlowView the moneyFlowView to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moneyFlowView,
     * or with status {@code 400 (Bad Request)} if the moneyFlowView is not valid,
     * or with status {@code 500 (Internal Server Error)} if the moneyFlowView couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MoneyFlowView> updateMoneyFlowView(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MoneyFlowView moneyFlowView
    ) throws URISyntaxException {
        log.debug("REST request to update MoneyFlowView : {}, {}", id, moneyFlowView);
        if (moneyFlowView.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, moneyFlowView.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moneyFlowViewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        moneyFlowView = moneyFlowViewService.update(moneyFlowView);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, moneyFlowView.getId().toString()))
            .body(moneyFlowView);
    }

    /**
     * {@code PATCH  /money-flow-views/:id} : Partial updates given fields of an existing moneyFlowView, field will ignore if it is null
     *
     * @param id the id of the moneyFlowView to save.
     * @param moneyFlowView the moneyFlowView to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moneyFlowView,
     * or with status {@code 400 (Bad Request)} if the moneyFlowView is not valid,
     * or with status {@code 404 (Not Found)} if the moneyFlowView is not found,
     * or with status {@code 500 (Internal Server Error)} if the moneyFlowView couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MoneyFlowView> partialUpdateMoneyFlowView(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MoneyFlowView moneyFlowView
    ) throws URISyntaxException {
        log.debug("REST request to partial update MoneyFlowView partially : {}, {}", id, moneyFlowView);
        if (moneyFlowView.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, moneyFlowView.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moneyFlowViewRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MoneyFlowView> result = moneyFlowViewService.partialUpdate(moneyFlowView);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, moneyFlowView.getId().toString())
        );
    }

    /**
     * {@code GET  /money-flow-views} : get all the moneyFlowViews.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of moneyFlowViews in body.
     */
    @GetMapping("")
    public List<MoneyFlowView> getAllMoneyFlowViews() {
        log.debug("REST request to get all MoneyFlowViews");
        return moneyFlowViewService.findAll();
    }

    /**
     * {@code GET  /money-flow-views/:id} : get the "id" moneyFlowView.
     *
     * @param id the id of the moneyFlowView to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the moneyFlowView, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MoneyFlowView> getMoneyFlowView(@PathVariable("id") Long id) {
        log.debug("REST request to get MoneyFlowView : {}", id);
        Optional<MoneyFlowView> moneyFlowView = moneyFlowViewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(moneyFlowView);
    }

    /**
     * {@code DELETE  /money-flow-views/:id} : delete the "id" moneyFlowView.
     *
     * @param id the id of the moneyFlowView to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMoneyFlowView(@PathVariable("id") Long id) {
        log.debug("REST request to delete MoneyFlowView : {}", id);
        moneyFlowViewService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
