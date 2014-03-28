package com.comsysto.dataloader.impl.reader;

import com.comsysto.dataloader.reader.AddressCsvReader;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;

/**
 * @author zuther
 */
public class AddressCsvReaderTest {
    @Test
    public void testParseCsv() throws Exception {
        AddressCsvReader addressCsvReader = new AddressCsvReader();
        assertFalse(addressCsvReader.parseCsv().isEmpty());
    }
}
