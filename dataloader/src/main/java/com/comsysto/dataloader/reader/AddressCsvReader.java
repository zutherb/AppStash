package com.comsysto.dataloader.reader;

import com.comsysto.shop.repository.user.model.Address;
import org.springframework.stereotype.Component;

/**
 * @author zutherb
 */
@Component("addressCsvReader")
public class AddressCsvReader extends AbstractCsvReader<Address> {

    private String filePath = "com/comsysto/dataloader/address.csv";

    @Override
    protected String getClassPathFilePath() {
        return filePath;
    }

    @Override
    protected String[] getColumnMapping() {
        return new String[]{
                "city",     "houseNumber",  "county",
                "zip",      "countryCode",  "country",
                "street",   "longitude",    "latitude"
        };
    }

    @Override
    protected Class<Address> getDestinationClass() {
        return Address.class;
    }

    /**
     * Setter for property override configure
     * @param filePath ilePath of the csv file that should be loaded
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
