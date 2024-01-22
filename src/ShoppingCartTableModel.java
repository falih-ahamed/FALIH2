import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ShoppingCartTableModel extends AbstractTableModel {
    private String[] columnNames = {"Product", "Quantity", "Price"};
    private ArrayList<Product> productsList;

    public ShoppingCartTableModel(ArrayList<Product> productsList) {
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
        Product product = productsList.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return product.getProductName();
            case 1:
                return 1; // Assuming quantity is always 1 for simplicity
            case 2:
                return product.getProductPrice();
            default:
                return null;
        }
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }
}
