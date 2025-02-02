package com.morethan.statistics.repository;

import com.morethan.statistics.domain.MtsExpense;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MtsExpense entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MtsExpenseRepository extends JpaRepository<MtsExpense, Long> {}
