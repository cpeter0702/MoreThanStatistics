package com.morethan.statistics.domain;

import static com.morethan.statistics.domain.MtsIncomeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.morethan.statistics.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MtsIncomeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MtsIncome.class);
        MtsIncome mtsIncome1 = getMtsIncomeSample1();
        MtsIncome mtsIncome2 = new MtsIncome();
        assertThat(mtsIncome1).isNotEqualTo(mtsIncome2);

        mtsIncome2.setId(mtsIncome1.getId());
        assertThat(mtsIncome1).isEqualTo(mtsIncome2);

        mtsIncome2 = getMtsIncomeSample2();
        assertThat(mtsIncome1).isNotEqualTo(mtsIncome2);
    }
}
