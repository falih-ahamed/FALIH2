public class Clothing extends Product{
    private String productSize;
    private String productColor;

    public Clothing(String productName, double productPrice, String productType, String productSize,
                    String productColor, int availableProductsCount)
    {
        super(productName, productPrice, productType, availableProductsCount);
        this.productSize = productSize;
        this.productColor = productColor;
    }

    public Clothing(String productType, String productID, String productName, double productPrice, String productColor,
                    String productSize, int availableProductsCount)
    {
        super(productID, productName, productPrice, productType, availableProductsCount);
        this.productSize = productSize;
        this.productColor = productColor;
    }

    public String getproductSize() {
        return productSize;
    }

    public String getproductColor() {
        return productColor;
    }

    @Override
    public String toString()
    {
        return super.toString()+
                ", Product Size : '" + productSize + '\'' +
                ", Product Color : '" + productColor + '\'' +
                " ]";
    }
}