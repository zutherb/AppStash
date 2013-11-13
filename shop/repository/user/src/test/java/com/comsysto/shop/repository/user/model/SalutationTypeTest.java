package com.comsysto.shop.repository.user.model;

import com.comsysto.shop.repository.user.model.SalutationType;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author zutherb
 */
public class SalutationTypeTest {

    @Test
    public void testBySalutation() throws Exception {
        assertEquals(SalutationType.MISTER, SalutationType.bySalutation("Herr"));
        assertEquals(SalutationType.MISS, SalutationType.bySalutation("Frau"));
    }
}
