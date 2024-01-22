import javax.swing.*;
import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager{
    public static Scanner input = new Scanner(System.in);
    public static ArrayList<Product> productArrayList = new ArrayList<>();
    public static String[] clothingSizes = {"XS", "S", "M", "L", "XL", "XXL"};

    //check if the same product is available while reading from the file
    public static boolean sameProductAvailable = false;
    public static boolean loggedIn = false;

    public static void main(String[] args) {
        ShoppingCart shoppingCart = new ShoppingCart();
        int option = 10;

        do {
            try {
                // Displaying the main menu
                System.out.println("""
                        --------------------------------------------
                        Welcome to FALIH'S ONLINE SHOPPING
                        CHOOSE AN OPTION FROM MENU
                        --------------------------------------------
                        1 ➡ Add Product
                        2 ➡ Delete Product
                        3 ➡ Print List of Products
                        4 ➡ Save in File
                        5 ➡ Read and Rearrange File
                        6 ➡ Clear List
                        7 ➡ Open GUI(Showcase Interface)
                        0 ➡ Exit the System
                        --------------------------------------------
                        ENTER OPTION HERE:""");

                option = Integer.parseInt(input.next());

                // Handling user input based on the selected option
                switch (option) {
                    case 1:
                        addNewProduct(shoppingCart);
                        // Set customerMadePurchase to true when a purchase is made
                        shoppingCart.setFirstPurchase(true);
                        break;
                    case 2:
                        promptingDeleteProductID();
                        break;
                    case 3:
                        printList();
                        break;
                    case 4:
                        createFile();
                        break;
                    case 5:
                        readFile();
                        break;
                    case 6:
                        clearList();
                        break;
                    case 7:
                        openGUI();
                        break;
                    case 0:
                        System.out.println("'Thank You For Coming :)'- FALIH'S ONLINE SHOPPING ");
                        break;
                    default:
                        System.out.println("❌Incorrect Number Option Entered, Please Try With The CORRECT Option!\n");
                }
            } catch (NumberFormatException error) {
                System.out.println("❌Invalid input entered!!! Please enter a NUMBER.\n");
            } catch (InputMismatchException error) {
                System.out.println("❌Invalid input entered!!!!\n");
            }
        } while (option != 0);
    }

    // Function to add a new product to the productArrayList
    public static void addNewProduct(ShoppingCart shoppingCart) {
        String productType;

        if (productArrayList.size() < 50) {
            while (true) {
                try {
                    // Choosing product type
                    System.out.print("\nEnter number to Choose Product Type \n----------------------------------\n\t1 ➡ Clothing \n\t2 ➡ Electronics \n\t0 ➡ Go Back\n\nEnter Number Here: ");
                    String productTypeInput = input.next();

                    if (productTypeInput.equals("0")) {
                        break;
                    }

                    int productTypeNo = Integer.parseInt(productTypeInput);

                    if (productTypeNo == 1 || productTypeNo == 2) {
                        String productName = validateStringInput("Name:", "Tshirt");

                        if (!productName.equals("0")) {
                            double productPrice = validatePrice();

                            if (!(productPrice == 0)) {
                                int itemCount = validateItemCount();

                                if (itemCount != 0) {
                                    switch (productTypeNo) {
                                        case 1:
                                            productType = "Clothing";
                                            String productSize = validateStringInput("Size", "XXS");

                                            if (!productSize.equals("0")) {
                                                String productColor = validateStringInput("Color", "Red");

                                                if (!productColor.equals("0")) {
                                                    Clothing cloth = new Clothing(productName, productPrice, productType, productColor, productSize, itemCount);
                                                    productArrayList.add(cloth);
                                                    System.out.println("\n✅Product Added Successfully!!!\n");
                                                    return;
                                                }
                                            }
                                            break;

                                        case 2:
                                            productType = "Electronics";
                                            String productBrand = validateStringInput("Brand", "Lenova");

                                            if (!productBrand.equals("0")) {
                                                String productWarranty = validateWarranty();

                                                if (!productWarranty.equals("0")) {
                                                    Electronics electronic = new Electronics(productName, productPrice, productType, productBrand, productWarranty, itemCount);
                                                    productArrayList.add(electronic);
                                                    System.out.println("\n✅Product Added Successfully\n");
                                                    return;
                                                }
                                            }
                                            break;
                                    }
                                }
                            }
                        }
                    } else {
                        System.out.println("❌Incorrect Number entered!\n");
                    }
                } catch (NumberFormatException error) {
                    System.out.println("❌Invalid input entered!!! Please enter a NUMBER.\n");
                } catch (InputMismatchException error) {
                    System.out.println("❌Invalid input entered!\n");
                }
            }
        } else {
            System.out.println("☹ Product List reached the limit (50 Product already added), Please REMOVE and ADD another!\n");
        }
    }

    // Function to validate string input
    public static String validateStringInput(String wording, String example) {
        while (true) {
            try {
                if (wording.equals("Size")) {
                    System.out.println("Sizes are ( 'XS', 'S', 'M', 'L', 'XL', 'XXL')");
                }

                System.out.print("Enter Product " + wording + " Example - " + example + " (Enter 0 to go back): ");
                String userInput = input.next();

                if (userInput.equals("0")) {
                    return userInput;
                }

                if (!containsString(userInput)) {
                    System.out.println("Product " + wording + " cannot contain special characters or numbers!");
                } else {
                    if (wording.equals("Size")) {
                        if (!validateSize(userInput)) {
                            System.out.println("❌Wrong size input!\n");
                            continue;
                        }
                    }
                    return userInput;
                }
            } catch (InputMismatchException error) {
                System.out.println("❌Invalid input!\n");
            }
        }
    }

    // Function to check if a string contains only letters
    public static boolean containsString(String userInput) {
        for (int i = 0; i < userInput.length(); i++) {
            char letter = userInput.charAt(i);
            if (!Character.isLetter(letter)) {
                return false;
            }
        }
        return true;
    }

    // Function to validate the price
    public static double validatePrice() {
        while (true) {
            try {
                System.out.print("Enter your product price (Enter 0 to go back): ");
                String productPriceString = input.next();

                double productPrice = Double.parseDouble(productPriceString);

                if (productPrice == 0) {
                    return productPrice;
                } else {
                    return Math.round(productPrice * 100.0) / 100.0;
                }

            } catch (NumberFormatException error) {
                System.out.println("❌Invalid input entered!!! Please enter a NUMBER.\n");
            } catch (InputMismatchException error) {
                System.out.println("❌Invalid input!\n");
            }
        }
    }

    // Function to validate the item count
    public static int validateItemCount() {
        while (true) {
            try {
                System.out.print("Enter your product count (Enter 0 to go back): ");
                String productItemString = input.next();

                return Integer.parseInt(productItemString);

            } catch (NumberFormatException error) {
                System.out.println("❌Invalid input entered!!! Please enter a NUMBER.\n");
            } catch (InputMismatchException error) {
                System.out.println("❌Invalid input!\n");
            }
        }
    }

    // Function to validate the clothing size
    public static boolean validateSize(String size) {
        size = size.toUpperCase();
        for (String element : clothingSizes) {
            if (size.equals(element)) {
                return true;
            }
        }
        return false;
    }

    // Function to validate the warranty
    public static String validateWarranty() {
        while (true) {
            try {
                int productWarrantyYears = validateWarrantyYearAndMonth("years", "25");

                if (productWarrantyYears != -1) {
                    int productWarrantyMonths = validateWarrantyYearAndMonth("months", "12");

                    if (productWarrantyMonths == -1) {
                        return "0";
                    } else {
                        return (productWarrantyYears + " years & " + productWarrantyMonths + " months");
                    }
                } else {
                    return "0";
                }
            } catch (NumberFormatException error) {
                System.out.println("❌Invalid input entered!!! Please enter a NUMBER.\n");
            } catch (InputMismatchException error) {
                System.out.println("❌Invalid input!\n");
            }
        }
    }

    // Function to validate the warranty year and month
    public static int validateWarrantyYearAndMonth(String wording, String max) {
        while (true) {
            try {
                System.out.print("Enter your product warranty period in " + wording + " within 0 - " + max + " (Enter -1 to go back): ");
                int productWarranty = Integer.parseInt(input.next());

                if (wording.equals("months") && (productWarranty >= 0 && productWarranty <= 12)) {
                    return productWarranty;
                } else if (wording.equals("years") && (productWarranty >= 0 && productWarranty <= 25)) {
                    return productWarranty;
                } else if (productWarranty == -1) {
                    return -1;
                } else {
                    System.out.println("invalid " + wording + "!");
                }

            } catch (NumberFormatException error) {
                System.out.println("❌Invalid input entered!!! Please enter a NUMBER.\n");
            } catch (InputMismatchException error) {
                System.out.println("❌Invalid input!\n");
            }
        }
    }

    // Function to prompt for deleting a product by its ID
    public static void promptingDeleteProductID() {
        while (true) {
            try {
                if (productArrayList.isEmpty()) {
                    System.out.println("\nEmpty Products");
                    break;
                } else {
                    System.out.print("Enter the product ID you want to delete (Enter 0 to go back): ");
                    String productID = input.next();

                    if (productID.equals("0")) {
                        break;
                    } else {
                        boolean productFound = false;
                        Iterator<Product> iterator = productArrayList.iterator();

                        while (iterator.hasNext()) {
                            Product product = iterator.next();
                            if (productID.equals(product.getProductID())) {
                                iterator.remove();
                                productFound = true;
                                System.out.println("✅Product removed successfully..\nRemaining products are:");
                                printList();
                                break;
                            }
                        }

                        if (!productFound) {
                            System.out.println("❌Product ID not found!\n");
                        }
                    }
                }
            } catch (NumberFormatException error) {
                System.out.println("❌Invalid input entered!!! Please enter a NUMBER.\n");
            } catch (InputMismatchException error) {
                System.out.println("❌Invalid input!\n");
            }
        }
    }

    // Function to print the list of products
    public static void printList() {
        if (!productArrayList.isEmpty()) {
            Collections.sort(productArrayList);

            for (Product product : productArrayList) {
                System.out.println(product);
            }
            System.out.println();
        } else {
            System.out.println("❌No products added!");
        }
    }

    // Function to create a file and save product details
    public static void createFile() {
        try {
            File file = new File("product-data.txt");

            if (file.createNewFile()) {
                System.out.println("✅File created successfully");
            } else {
                System.out.println("❌File already exists");
            }

            try (FileWriter fileWriter = new FileWriter("product-data.txt");
                 BufferedWriter writer = new BufferedWriter(fileWriter)) {

                // Writing product details to the file
                for (Product product : productArrayList) {
                    if (product instanceof Clothing cloth) {
                        writer.write(product.getProductType() + ",");
                        writer.write(product.getProductID() + ",");
                        writer.write(product.getProductName() + ",");
                        writer.write(product.getProductPrice() + ",");
                        writer.write(cloth.getproductSize() + ",");
                        writer.write(cloth.getproductColor() + ",");
                        writer.write(cloth.getAvailableProductsCount() + ",");
                    } else {
                        Electronics electronics = (Electronics) product;
                        writer.write(product.getProductType() + ",");
                        writer.write(product.getProductID() + ",");
                        writer.write(product.getProductName() + ",");
                        writer.write(product.getProductPrice() + ",");
                        writer.write(electronics.getproductBrand() + ",");
                        writer.write(electronics.getproductWarranty() + ",");
                        writer.write(electronics.getAvailableProductsCount() + ".");
                    }
                    writer.newLine();
                }
                System.out.println("\n✅Saved successfully\n");

            } catch (IOException error) {
                error.printStackTrace();
            }

        } catch (IOException outerError) {
            outerError.printStackTrace();
        }
    }

    // Function to read and rearrange product details from a file
    public static void readFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("product-data.txt"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] productDetailsArray = line.split(",");

                for (Product product : productArrayList) {
                    if (product.getProductID().equals(productDetailsArray[1])) {
                        sameProductAvailable = true;
                        break;
                    }
                }
                if (!sameProductAvailable) {
                    Product newProduct = createProduct(productDetailsArray);
                    productArrayList.add(newProduct);
                }
            }

        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    // Function to create a product based on the given details
    private static Product createProduct(String[] productDetailsArray) {
        if (productDetailsArray[0].equals("Clothing")) {
            return new Clothing(productDetailsArray[0], productDetailsArray[1], productDetailsArray[2],
                    Double.parseDouble(productDetailsArray[3]), productDetailsArray[4], productDetailsArray[5],
                    Integer.parseInt(productDetailsArray[6]));
        } else {
            return new Electronics(productDetailsArray[0], productDetailsArray[1], productDetailsArray[2],
                    Double.parseDouble(productDetailsArray[3]), productDetailsArray[4], productDetailsArray[5],
                    Integer.parseInt(productDetailsArray[6]));
        }
    }

    // Function to clear the product list
    public static void clearList() {
        System.out.println("Are you sure you want to clear the list (Y/N): ");
        String answer = input.next().trim().toUpperCase();

        if (answer.equals("Y")) {
            productArrayList.clear();
            System.out.println("✅\nProduct list cleared successfully.\n");
        } else if (answer.equals("N")) {
            System.out.println("\nClear list operation canceled.\n");
        } else {
            System.out.println("\n❌Invalid input. Please enter Y or N.\n");
        }
    }

    // Function to open the graphical user interface
    public static void openGUI() {
        Frame frame = new Frame();
        frame.setTitle("Westminster Shopping Centre");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 700);
        frame.setVisible(true);


    }
}



