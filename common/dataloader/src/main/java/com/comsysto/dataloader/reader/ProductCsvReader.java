package com.comsysto.dataloader.reader;


import com.comsysto.shop.repository.product.model.Product;
import org.springframework.stereotype.Component;

@Component( "productCsvReader" )
public class ProductCsvReader extends AbstractCsvReader<Product>{

    private String filePath = "com/comsysto/dataloader/product.csv";

    @Override
    protected String getClassPathFilePath() {
        return filePath;
    }

    @Override
    protected String[] getColumnMapping() {
        return new String[] { "articleId", "type", "name", "description", "price", "category" };
    }

    @Override
    protected Class<Product> getDestinationClass() {
        return Product.class;
    }

    /**
     * Setter for property override configure
     * @param filePath
     */
    public void setFilePath( String filePath ) {
        this.filePath = filePath;
    }


}
