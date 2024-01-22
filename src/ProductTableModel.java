import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ProductTableModel extends AbstractTableModel {
    private String[] columnNames = {"Product ID", "Name", "Category", "Price($)", "info","Item Count"};
    private ArrayList<Product> productsList;

    public ProductTableModel(ArrayList<Product> productsList){

        this.productsList = productsList;
    }

    @Override
    public int getRowCount() {
        return productsList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object temp = null;
        switch (columnIndex){
            case 0:
                temp = productsList.get(rowIndex).getProductID();
                break;
            case 1:
                temp = productsList.get(rowIndex).getProductName();
                break;
            case 2:
                temp = productsList.get(rowIndex).getProductType();
                break;
            case 3:
                temp = productsList.get(rowIndex).getProductPrice();
                break;
            case 4:
                if (productsList.get(rowIndex) instanceof Clothing clothing) {
                    temp = clothing.getproductSize()+", "+clothing.getproductColor();
                } else {
                    Electronics electronics = (Electronics) productsList.get(rowIndex);
                    temp = electronics.getproductBrand()+", "+electronics.getproductWarranty();
                }
                break;
            case 5:
                temp = productsList.get(rowIndex).getAvailableProductsCount();
                break;
        }
        return temp;
    }

    public String getColumnName(int col){
        return columnNames[col];
    }
}
