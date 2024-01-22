public class Electronics extends Product{
    private String productBrand;
    private String productWarranty;

    public Electronics(String productName, double productPrice, String productType, String productBrand,
                       String productWarranty, int availableProductsCount)
    {
        super(productName, productPrice, productType, availableProductsCount);
        this.productWarranty = productWarranty;
        this.productBrand = productBrand;
    }

    public Electronics(String productType, String productID, String productName, double productPrice,
                       String productBrand, String productWarranty, int availableProductsCount)
    {
        super(productID, productName, productPrice, productType, availableProductsCount);
        this.productBrand = productBrand;
        this.productWarranty = productWarranty;
    }

    public String getproductBrand() {
        return productBrand;
    }

    public String getproductWarranty() {
        return productWarranty;
    }

    @Override
    public String toString()
    {
        return super.toString()+
                ", Product Brand : '" + productBrand + '\'' +
                ", Product Warranty : '" + productWarranty + '\'' +
                " ]";
    }
}
