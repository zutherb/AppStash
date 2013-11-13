package com.comsysto.dataloader.impl.reader;

import com.comsysto.dataloader.reader.SupplierCsvReader;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;

/**
 * @author zutherb
 */
public class SupplierCsvReaderTest {
    @Test
    public void testParseCsv() throws Exception {
        SupplierCsvReader supplierCsvReader = new SupplierCsvReader();
        assertFalse(supplierCsvReader.parseCsv().isEmpty());
    }
}
