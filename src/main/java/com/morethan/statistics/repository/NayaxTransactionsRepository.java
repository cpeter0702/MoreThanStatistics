package com.morethan.statistics.repository;

import com.morethan.statistics.domain.NayaxTransactions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NayaxTransactions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NayaxTransactionsRepository extends JpaRepository<NayaxTransactions, Long> {}
