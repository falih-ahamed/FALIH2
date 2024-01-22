import java.util.Random;
import static java.lang.String.valueOf;

public abstract class Product implements Comparable<Product> {
    // Product variables
    private String productID;
    private String productName;
    private int availableProductsCount;
    private double productPrice;
    private String productType;


    public Product(String productName, double productPrice, String productType, int availableProductsCount){
        super();
        this.productName = productName;
        this.productPrice = productPrice;
        this.productType = productType;
        this.availableProductsCount = availableProductsCount;
        productID = createProductID();
    }

    public Product(String productID, String productName, double productPrice, String productType, int availableProductsCount){
        super();
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productType = productType;
        this.availableProductsCount = availableProductsCount;
    }

    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public int getAvailableProductsCount() {
        return availableProductsCount;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public String getProductType() {
        return productType;
    }


    @Override
    public String toString() {
        return "Product [" +
                " productID = '" + productID + '\'' +
                ", productType = '" + productType + '\''+
                ", productName = '" + productName + '\'' +
                ", productCount = " + availableProductsCount +
                ", productPrice = " + productPrice ;
    }

    private static String createProductID() {
        String characters1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String characters2 = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        StringBuilder randomString = new StringBuilder();

        // GENERATE THE RANDOM STRING BY APPENDING RANDOM CHARACTERS AND NUMBERS

        randomString.append(generateID(characters1,2));
        randomString.append(generateID(characters2, 2));
        randomString.append(generateID(numbers, 4));

        return valueOf(randomString);
    }

    public static String generateID(String list, int length){
        Random random = new Random();
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(list.length());
            char randomChar = list.charAt(randomIndex);
            randomString.append(randomChar);
        }
        return valueOf(randomString);
    }

    @Override
    public int compareTo(Product product){
        return this.productID.compareTo(product.getProductID());
    }
}
