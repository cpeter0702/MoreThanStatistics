package com.morethan.statistics.domain;

import static com.morethan.statistics.domain.MoneyFlowViewTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.morethan.statistics.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MoneyFlowViewTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MoneyFlowView.class);
        MoneyFlowView moneyFlowView1 = getMoneyFlowViewSample1();
        MoneyFlowView moneyFlowView2 = new MoneyFlowView();
        assertThat(moneyFlowView1).isNotEqualTo(moneyFlowView2);

        moneyFlowView2.setId(moneyFlowView1.getId());
        assertThat(moneyFlowView1).isEqualTo(moneyFlowView2);

        moneyFlowView2 = getMoneyFlowViewSample2();
        assertThat(moneyFlowView1).isNotEqualTo(moneyFlowView2);
    }
}
