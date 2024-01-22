import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Frame extends JFrame {
    private ArrayList<Product> productArrayList;
    private ArrayList<Product> clothingArrayList = new ArrayList<>();
    private ArrayList<Product> electronicsArrayList = new ArrayList<>();
    private JPanel productPanel;
    private JPanel detailsPanel;
    private JTable table;
    String selectedOption = "All";
    private ShoppingCart shoppingCart;
    private JLabel firstPurchaseDiscountLabel;
    private ArrayList<User> userList = new ArrayList<>();
    private User currentUser;
    public Frame() {
        // Read data from file
        WestminsterShoppingManager.readFile();
        // Initialize product list
        productArrayList = new ArrayList<>(WestminsterShoppingManager.productArrayList);
        setLayout(new BorderLayout());

        // Create UI components
        JPanel topPanel = createTopPanel();
        productPanel = createProductPanel("All");
        detailsPanel = createDetailsPanel(productArrayList.get(0));

        // Set background colors
        topPanel.setBackground(Color.WHITE);
        productPanel.setBackground(Color.WHITE);
        detailsPanel.setBackground(Color.LIGHT_GRAY);

        // Add components to the frame
        add(topPanel, BorderLayout.NORTH);
        add(productPanel, BorderLayout.CENTER);
        add(detailsPanel, BorderLayout.SOUTH);
        // Initialize shopping cart
        shoppingCart = new ShoppingCart();

        // Set frame size and location
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int initialWidth = screenSize.width / 2;
        int initialHeight = screenSize.height / 2;
        setSize(initialWidth, initialHeight);
        setLocationRelativeTo(null);

        // Create label for first purchase discount
        firstPurchaseDiscountLabel = new JLabel("First Purchase Discount (10%): -$0.00");
    }

    // Create the top panel of the frame
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 1));

        // Create "Welcome" label in the top row
        JPanel welcomePanel = new JPanel();
        JLabel welcomeLabel = new JLabel("Welcome to Falih's Online Shopping");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomePanel.add(welcomeLabel);
        topPanel.add(welcomePanel);
        welcomePanel.setBackground(Color.BLACK);
        welcomeLabel.setForeground(Color.cyan);

        // Add the existing content in the bottom row
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(1, 4));
        contentPanel.setBackground(Color.white);

        contentPanel.add(new JLabel("Select Product Category"));
        JComboBox<String> productTypes = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        contentPanel.add(productTypes);

        JPanel emptySpacePanel = new JPanel();
        contentPanel.add(emptySpacePanel);
        emptySpacePanel.setBackground(Color.white);

        JButton shoppingCartButton = new JButton("Shopping Cart");
        contentPanel.add(shoppingCartButton);

        shoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoginFrame();
            }
        });

        JButton sortProductsButton = new JButton("Sort Products");
        contentPanel.add(sortProductsButton);

        productTypes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedOption = (String) productTypes.getSelectedItem();
                chooseArray();
                updateProductPanel(selectedOption);
            }
        });

        sortProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Collections.sort(productArrayList);
                chooseArray();
                updateProductPanel(selectedOption);
            }
        });

        // Add the contentPanel to the bottom row
        topPanel.add(contentPanel);
        // Set border gaps
        int topGap = 10;
        int leftGap = 20;
        int bottomGap = 10;
        int rightGap = 20;
        topPanel.setBorder(BorderFactory.createEmptyBorder(topGap, leftGap, bottomGap, rightGap));

        return topPanel;
    }

    // Create the product panel based on the selected array type
    private JPanel createProductPanel(String arrayType) {
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new GridLayout(1, 1));

        if (arrayType.equals("All")) {
            ProductTableModel tableModel = new ProductTableModel(productArrayList);
            table = new JTable(tableModel);
        } else if (arrayType.equals("Electronics")) {
            ProductTableModel tableModel = new ProductTableModel(electronicsArrayList);
            table = new JTable(tableModel);
        } else {
            ProductTableModel tableModel = new ProductTableModel(clothingArrayList);
            table = new JTable(tableModel);
        }

        // Set cell renderer for column 5
        table.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            Color originalColor = null;

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Assuming that the value is an Integer
                if (value instanceof Integer && (Integer) value <= 3) {
                    renderer.setBackground(Color.RED);
                } else {
                    // Set the default background color for other cells
                    renderer.setBackground(Color.CYAN);
                }
                return renderer;
            }
        });
        // Add selection listener
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    handleTableRowSelection(selectedRow);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        productPanel.add(scrollPane);

        int topGap = 10;
        int leftGap = 20;
        int bottomGap = 10;
        int rightGap = 20;
        productPanel.setBorder(BorderFactory.createEmptyBorder(topGap, leftGap, bottomGap, rightGap));

        return productPanel;
    }

    // Update the product panel based on the selected array type
    private void updateProductPanel(String arrayType) {
        remove(productPanel);
        productPanel = createProductPanel(arrayType);
        add(productPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // choose clothingArrayList and electronicsArrayList based on productArrayList
    private void chooseArray() {
        clothingArrayList.clear();
        electronicsArrayList.clear();

        for (Product product : productArrayList) {
            if (product instanceof Electronics electronics) {
                electronicsArrayList.add(electronics);
            } else if (product instanceof Clothing clothing) {
                clothingArrayList.add(clothing);
            }
        }
    }

    // Create the details panel for the selected product
    private JPanel createDetailsPanel(Product product) {
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(8, 1));
        detailsPanel.add(new JLabel("SELECTED PRODUCT DETAILS"));
        detailsPanel.add(new JLabel("Product ID : " + product.getProductID()));
        detailsPanel.add(new JLabel("Name          : " + product.getProductName()));
        detailsPanel.add(new JLabel("Category    : " + product.getProductType()));
        detailsPanel.add(new JLabel("Price           : " + product.getProductPrice()));

        detailsPanel.setBackground(Color.LIGHT_GRAY);

        // Add details specific to Electronics or Clothing
        if (product instanceof Electronics electronics) {
            detailsPanel.add(new JLabel("Size             : " + electronics.getproductBrand()));
            detailsPanel.add(new JLabel("Colour        : " + electronics.getproductWarranty()));
        } else if (product instanceof Clothing clothing) {
            detailsPanel.add(new JLabel("Size             : " + clothing.getproductSize()));
            detailsPanel.add(new JLabel("Colour        : " + clothing.getproductColor()));
        }

        // Add button to add the product to the shopping cart
        JButton addToCartButton = new JButton("Add to Shopping Cart");
        addToCartButton.setPreferredSize(new Dimension(50, 30));
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shoppingCart.addProductToCart(product);
                JOptionPane.showMessageDialog(Frame.this, "Product added to Shopping Cart!");

            }
        });
        // Create button panel and add components
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.add(new JLabel());
        buttonPanel.add(addToCartButton);
        buttonPanel.add(new JLabel());
        detailsPanel.add(buttonPanel);
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        addToCartButton.setBackground(Color.BLACK);
        addToCartButton.setForeground(Color.CYAN);
        // Set rounded border for the button
        int borderRadius = 2;
        Border roundedBorder = new LineBorder(Color.lightGray, borderRadius, true);
        addToCartButton.setBorder(roundedBorder);

        // Set border gaps
        int topGap = 5;
        int leftGap = 20;
        int bottomGap = 10;
        int rightGap = 20;
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(topGap, leftGap, bottomGap, rightGap));

        return detailsPanel;
    }

    // Handle selection of a row in the product table
    private void handleTableRowSelection(int selectedRow) {
        if (selectedRow >= 0 && selectedRow < productArrayList.size()) {
            Product selectedProduct = productArrayList.get(selectedRow);
            remove(detailsPanel);
            detailsPanel = createDetailsPanel(selectedProduct);
            add(detailsPanel, BorderLayout.SOUTH);
            revalidate();
            repaint();
        }
    }

    // Open the login frame
    private void openLoginFrame() {

        LoginFrame loginFrame = new LoginFrame(this);
        loginFrame.setVisible(true);
        setLocationRelativeTo(null);
    }

    // open the shopping cart UI
    public void openShoppingCartUI() {
        // Create and configure the shopping cart frame
        JFrame shoppingCartFrame = new JFrame("Shopping Cart");
        shoppingCartFrame.setSize(500, 500);
        shoppingCartFrame.setLayout(new BorderLayout());

        // Create components for the shopping cart frame
        JPanel cartTablePanel = createCartTablePanel();
        cartTablePanel.setBackground(Color.WHITE);
        JPanel cartDetailsPanel = createCartDetailsPanel();
        cartDetailsPanel.setBackground(Color.LIGHT_GRAY);

        // Add components to the shopping cart frame
        shoppingCartFrame.add(cartTablePanel, BorderLayout.CENTER);
        shoppingCartFrame.add(cartDetailsPanel, BorderLayout.SOUTH);

        // Make the shopping cart frame visible
        shoppingCartFrame.setVisible(true);


    }

    // Method to validate user login
    public boolean validateLogin(String username, String password) {
        // Iterate through the userList to find a matching user
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                // Set the current user
                currentUser = user;
                // Update user's purchase status and apply first purchase discount
                if (!user.hasMadePurchase()) {
                    user.setMadePurchase(true);
                    shoppingCart.isFirstPurchase();
                }
                return true;
            }
        }
        return false;
    }

    // Method to validate user registration
    public boolean validateRegistration(String username, String password) {
        // Check if the username is not already taken
        if (userList.contains(new User(username, null))) {
            return false;
        }

        // Check if the password meets the minimum length requirement
        return password.length() >= 8;
    }

    // Method to create the panel displaying the shopping cart table
    private JPanel createCartTablePanel() {
        JPanel cartTablePanel = new JPanel(new BorderLayout());

        String[] columnNames = {"Product", "Quantity", "Price"};
        ShoppingCartTableModel cartTableModel = new ShoppingCartTableModel(shoppingCart.getProductsList());
        JTable cartTable = new JTable(cartTableModel);
        JScrollPane scrollPane = new JScrollPane(cartTable);

        cartTablePanel.add(scrollPane, BorderLayout.CENTER);

        int topGap = 10;
        int leftGap = 20;
        int bottomGap = 10;
        int rightGap = 20;
        cartTablePanel.setBorder(BorderFactory.createEmptyBorder(topGap, leftGap, bottomGap, rightGap));

        return cartTablePanel;
    }

    // Method to create the panel displaying the shopping cart details
    private JPanel createCartDetailsPanel() {
        JPanel cartDetailsPanel = new JPanel();
        cartDetailsPanel.setLayout(new GridLayout(3, 2));
        cartDetailsPanel.setBackground(Color.LIGHT_GRAY);

        // Calculate total price
        double totalPrice = calculateTotalPrice();
        JLabel totalPriceLabel = new JLabel("Total Price: $" + String.format("%.2f", totalPrice));

        // Check if it's the user's first purchase
        boolean isFirstPurchase = checkFirstPurchase();
        double firstPurchaseDiscount = isFirstPurchase ? totalPrice * 0.10 : 0.0;

        // Calculate three items in the same category discount
        double threeItemsDiscount = calculateThreeItemsDiscount();
        JLabel threeItemsDiscountLabel = new JLabel("Three Items Discount (20%): -$" + String.format("%.2f", threeItemsDiscount));

        // Calculate final price
        double finalPrice = calculateFinalPrice(totalPrice, threeItemsDiscount);
        JLabel finalPriceLabel = new JLabel("Final Price: $" + String.format("%.2f", finalPrice));

        // Add labels to the panel
        cartDetailsPanel.add(totalPriceLabel);
        cartDetailsPanel.add(firstPurchaseDiscountLabel);
        cartDetailsPanel.add(threeItemsDiscountLabel);
        cartDetailsPanel.add(finalPriceLabel);

        // Add "Confirm Purchase" button
        JButton confirmPurchaseButton = new JButton("Confirm Purchase");
        confirmPurchaseButton.setBackground(Color.BLACK);
        confirmPurchaseButton.setForeground(Color.CYAN);
        int borderRadius = 2;
        Border roundedBorder = new LineBorder(Color.lightGray, borderRadius, true);
        confirmPurchaseButton.setBorder(roundedBorder);
        confirmPurchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleConfirmPurchase();
            }
        });
        cartDetailsPanel.add(confirmPurchaseButton);

        // Add padding to the panel
        int padding = 10;
        cartDetailsPanel.setBorder(new EmptyBorder(padding, padding, padding, padding));

        return cartDetailsPanel;
    }

    // Method to handle the confirmation of a purchase
    private void handleConfirmPurchase() {
        // Show message box
        JOptionPane.showMessageDialog(this, "Purchase successful!");

        // Get the details for the purchase
        String purchaseDetails = generatePurchaseDetails();

        // Store the details in a text file
        storePurchaseDetails(purchaseDetails);

        // Clear the shopping cart after successful purchase
        shoppingCart.clearCart();

        // Close the shopping cart frame if it exists
        closeShoppingCartFrameIfExists();

        // Update the labels and refresh the UI as needed
        updateProductPanel(selectedOption);
    }

    // Method to close the shopping cart frame if it exists
    private void closeShoppingCartFrameIfExists() {
        for (Window window : Window.getWindows()) {
            if (window instanceof JFrame && ((JFrame) window).getTitle().equals("Shopping Cart")) {
                window.dispose();
                break;
            }
        }
    }

    // Method to generate purchase details
    private String generatePurchaseDetails() {
        StringBuilder details = new StringBuilder();
        details.append("Purchase Details:\n");

        for (Product product : shoppingCart.getProductsList()) {
            details.append("Product Name: ").append(product.getProductName()).append("\n");
            details.append("Quantity: 1\n"); // Assuming quantity is always 1, modify as needed
            details.append("Price: $").append(String.format("%.2f", product.getProductPrice())).append("\n");
            details.append("-------------\n");
        }

        // Add logged-in username
        details.append("Username: ").append(currentUser.getUsername()).append("\n");

        return details.toString();
    }

    // Method to store purchase details in a text file
    private void storePurchaseDetails(String details) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("purchase_details.txt", true))) {
            writer.println(details);
            writer.println("=======================================");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to calculate the total price of items in the shopping cart
    private double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (Product product : shoppingCart.getProductsList()) {
            totalPrice += product.getProductPrice();
        }
        return totalPrice;
    }

    private boolean checkFirstPurchase() {
                return !shoppingCart.isFirstPurchase();
    }

    // Method to calculate the discount for purchasing at least three items of the same category
    private double calculateThreeItemsDiscount() {
        // Assuming you want to apply a 20% discount for purchasing at least three items of the same category
        Map<String, Integer> categoryCountMap = new HashMap<>();

        for (Product product : shoppingCart.getProductsList()) {
            String category = product.getProductType();
            categoryCountMap.put(category, categoryCountMap.getOrDefault(category, 0) + 1);
        }

        double threeItemsDiscount = 0.0;
        for (int itemCount : categoryCountMap.values()) {
            if (itemCount >= 3) {
                // Apply 20% discount for each category with at least three items
                threeItemsDiscount += 0.2 * calculateCategoryTotal(categoryCountMap, itemCount);
            }
        }

        return threeItemsDiscount;
    }

    private double calculateCategoryTotal(Map<String, Integer> categoryCountMap, int itemCount) {
        double categoryTotal = 0.0;
        for (Product product : shoppingCart.getProductsList()) {
            if (categoryCountMap.get(product.getProductType()) == itemCount) {
                categoryTotal += product.getProductPrice();
            }
        }
        return categoryTotal;
    }

    // Method to calculate the total price
    private double calculateFinalPrice(double totalPrice, double threeItemsDiscount) {
        // Get the first purchase discount from the shopping cart
        double firstPurchaseDiscount = shoppingCart.isFirstPurchase() ? totalPrice * 0.10 : 0.0;

        // Calculate final price including the first purchase discount
        double finalPrice = totalPrice - firstPurchaseDiscount - threeItemsDiscount;

        // Update the text of the existing label
        firstPurchaseDiscountLabel.setText("First Purchase Discount (10%): -$" + String.format("%.2f", firstPurchaseDiscount));

        return finalPrice;
    }

    // Method to load users from a file
    public void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.dat"))) {
            userList = (ArrayList<User>) ois.readObject();
        } catch (FileNotFoundException e) {
            // Ignore if the file is not found (first run)
            userList = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Method to save users to a file
    public void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.dat"))) {
            oos.writeObject(userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<User> getUserList() {
        return userList;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Frame().setVisible(true);
            }
        });
    }
}
