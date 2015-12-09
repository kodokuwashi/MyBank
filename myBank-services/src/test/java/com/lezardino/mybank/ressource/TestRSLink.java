package com.lezardino.mybank.ressource;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.data.domain.Sort.Direction;

public class TestRSLink {

    @Test
    public void testRSLink() {
        RSLink rsLink = new RSLink("/comptes?offset={0}&limit={1}&direction={2}");

        rsLink.setPreviousLink(2, 2, Direction.ASC);
        rsLink.setSelfLink(2, 2, Direction.ASC);
        rsLink.setNextLink(2, 2, 8, Direction.ASC);

        assertThat(rsLink.toString())
        .isEqualTo(
                "RSLink{uriPattern='/comptes?offset={0}&limit={1}&direction={2}', links={previous=/comptes?offset=0&limit=2&direction=ASC, next=/comptes?offset=4&limit=2&direction=ASC, self=/comptes?offset=2&limit=2&direction=ASC}}");
    }

}
