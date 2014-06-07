package com.comsysto.common.util;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author zutherb
 */
public class SeoUtilsTest {
    @Test
    public void testUrlFriendly() throws Exception {
        String sentence = new String("Männer fahren gerne mit dem Floß und springen über Möhren, daß ist schön. [  ] 12.01.2012 ?*#.".getBytes(), "UTF8");
        String urlfriendly = SeoUtils.urlFriendly(sentence);
        assertEquals(new String("maenner-fahren-gerne-mit-dem-floss-und-springen-ueber-moehren-dass-ist-schoen-12-01-2012".getBytes(), "UTF8"), urlfriendly);
    }
}
