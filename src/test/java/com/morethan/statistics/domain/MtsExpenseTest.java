package com.morethan.statistics.domain;

import static com.morethan.statistics.domain.MtsExpenseTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.morethan.statistics.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MtsExpenseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MtsExpense.class);
        MtsExpense mtsExpense1 = getMtsExpenseSample1();
        MtsExpense mtsExpense2 = new MtsExpense();
        assertThat(mtsExpense1).isNotEqualTo(mtsExpense2);

        mtsExpense2.setId(mtsExpense1.getId());
        assertThat(mtsExpense1).isEqualTo(mtsExpense2);

        mtsExpense2 = getMtsExpenseSample2();
        assertThat(mtsExpense1).isNotEqualTo(mtsExpense2);
    }
}
