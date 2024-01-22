import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList<Product> productsList;
    private boolean firstPurchase = true;

    public ShoppingCart() {
        this.productsList = new ArrayList<>();
    }

    public void addProductToCart(Product product) {
        productsList.add(product);
    }

    public ArrayList<Product> getProductsList() {
        return productsList;
    }

    public boolean isFirstPurchase() {
        return firstPurchase;
    }

    public void setFirstPurchase(boolean firstPurchase) {
        this.firstPurchase = firstPurchase;
    }

    public void clearCart() {
        productsList.clear();
        firstPurchase = true;
    }
}
