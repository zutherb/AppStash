package io.github.appstash.shop.repository.product.model;


public enum ProductType {
    TABLET("Tablet", "tablet"),
    HANDY("Handy", "handy");

    private String name;
    private String urlname;

    private ProductType(String name, String urlname) {
        this.name = name;
        this.urlname = urlname;
    }

    public String getName() {
        return name;
    }

    public String getUrlname() {
        return urlname;
    }

    public static ProductType fromUrlname(String urlname) {
        for(ProductType productType : values()){
            if(productType.getUrlname().equals(urlname)){
                return productType;
            }
        }
        throw new IllegalArgumentException("No Product Type is known for urlname:" + urlname);
    }
}