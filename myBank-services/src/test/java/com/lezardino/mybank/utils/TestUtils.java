package com.lezardino.mybank.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

import com.lezardino.mybank.utils.Utils;

public class TestUtils {

    @Test
    public void testBigDecimalToString() {
        BigDecimal bigDecimal = new BigDecimal(10.50);
        assertThat(Utils.bigDecimalToString(bigDecimal)).isEqualTo("10,50");
    }

}
