package com.morethan.statistics.repository;

import com.morethan.statistics.domain.MtsIncome;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MtsIncome entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MtsIncomeRepository extends JpaRepository<MtsIncome, Long> {}
