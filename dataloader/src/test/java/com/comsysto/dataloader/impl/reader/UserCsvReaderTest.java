package com.comsysto.dataloader.impl.reader;

import com.comsysto.dataloader.reader.UserCsvReader;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author zutherb
 */
public class UserCsvReaderTest {
    @Test
    public void testParseCsv() throws Exception {
        UserCsvReader userReader = new UserCsvReader();
        assertEquals(100, userReader.parseCsv().size());
    }
}