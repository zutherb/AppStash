package io.github.appstash.dataloader.impl.reader;


import io.github.appstash.dataloader.reader.ProductCsvReader;
import io.github.appstash.shop.repository.product.model.Product;
import io.github.appstash.shop.repository.product.model.ProductType;
import org.apache.commons.collections.ListUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static io.github.appstash.shop.repository.product.model.ProductType.*;
import static junit.framework.Assert.assertEquals;

public class ProductCsvReaderTest {

    private List<Product> parsedProducts;


    @Before
    @SuppressWarnings( "unchecked" )
    public void parseCsv() throws IOException {
        parsedProducts = ListUtils.unmodifiableList( new ProductCsvReader().parseCsv( ));
    }

    @Test
    public void testAllProductsParsed() throws IOException {
        assertEquals( 32, parsedProducts.size( ));
    }

    @Test
    public void testParsedPizza() {
        checkCountAndArticleIds( PIZZA, 8, 100 );
    }

    @Test
    public void testParsedPasta() {
        checkCountAndArticleIds( PASTA, 8, 200 );
    }

    @Test
    public void testParsedSalad() {
        checkCountAndArticleIds( SALAD, 8, 300 );
    }

    @Test
    public void testParsedBeverages() {
        checkCountAndArticleIds( BEVERAGE, 8, 400 );
    }


    private void checkCountAndArticleIds( ProductType type, int expectedProductCount, int startingArticleNumber ) {
        int actualProductCount = 0;

        for ( Product parsedProduct : parsedProducts ) {
            if( parsedProduct.getType() == type ) {
                int expectedArticleNumber = startingArticleNumber + actualProductCount++;
                assertEquals( expectedArticleNumber + "", parsedProduct.getArticleId( ));
            }
        }
        assertEquals( expectedProductCount, actualProductCount );
    }
}
