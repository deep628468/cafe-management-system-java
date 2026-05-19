import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// ---------------- ITEM CLASS ----------------
class Item {
    String name;
    int price;
    int quantity;
    String imagePath;

    Item(String name, int price, String imagePath) {
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
        this.quantity = 0;
    }
}

// ---------------- MAIN CLASS ----------------
public class CafeManagementSystem extends JFrame {

    CardLayout cardLayout;
    JPanel mainPanel;
    ArrayList<Item> cart = new ArrayList<>();
    JTextArea billArea;
    JLabel receiptArea;
    HashMap<String, Item> menuItems = new LinkedHashMap<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CafeManagementSystem());
    }

    public CafeManagementSystem() {
        setTitle("Cafe Management System");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        initializeItems();

        mainPanel.add(createWelcomeScreen(), "welcome");
        mainPanel.add(createMenuScreen(), "menu");
        mainPanel.add(createBillingScreen(), "billing");
        mainPanel.add(createReceiptScreen(), "receipt");

        add(mainPanel);
        setVisible(true);
    }

    private void initializeItems() {
        addItem("Tea", 20, "images/tea.jpg");
        addItem("Coffee", 30, "images/coffee.jpg");
        addItem("Cold Coffee", 50, "images/coldcoffee.jpg");
        addItem("Cappuccino", 60, "images/cappuccino.jpg");
        addItem("Coke", 40, "images/coke.jpg");
        addItem("Hot Chocolate", 70, "images/hotChocolate.jpg");
        addItem("Green Tea", 25, "images/greentea.jpg");
        addItem("Pepsi", 40, "images/pepsi.jpg");
        addItem("Sprite", 40, "images/sprite.jpg");
        addItem("Fanta", 40, "images/fanta.jpg");
        addItem("Limca", 40, "images/limca.jpg");
        addItem("Orange Juice", 60, "images/orangejuice.jpg");
        addItem("Mango Juice", 75, "images/mangojuice.jpg");
        addItem("Apple Juice", 65, "images/applejuice.jpg");
        addItem("Mixed Fruit Juice", 80, "images/mixedfruitjuice.jpg");
        addItem("Burger", 60, "images/burger.jpg");
        addItem("French Fries", 80, "images/frenchfries.jpg");
        addItem("Pizza", 180, "images/pizza.jpg");
        addItem("Pasta", 180, "images/pasta.jpg");
        addItem("Samosa", 20, "images/samosa.jpg");
        addItem("Mineral Water", 20, "images/mineralwater.jpg");
        addItem("Ice Cream", 60, "images/icecream.jpg");
        addItem("Choclate Milk", 40, "images/choclatemilk.jpg");
    }

    private void addItem(String name, int price, String img) {
        menuItems.put(name, new Item(name, price, img));
    }

    private JPanel createWelcomeScreen() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 228, 181));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Welcome TO Cafe ☕", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 100));
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(title, gbc);

        JLabel info = new JLabel("<html><center>📍 Ludhiana<br>Mon - Sunday: 8:00 AM - 9:00 PM</center></html>", SwingConstants.CENTER);
        info.setFont(new Font("Arial", Font.PLAIN, 35));
        gbc.weighty = 0.1;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(info, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton startBtn = new JButton("Start Order");
        JButton exitBtn = new JButton("Exit");
        startBtn.addActionListener(e -> cardLayout.show(mainPanel, "menu"));
        exitBtn.addActionListener(e -> System.exit(0));
        buttonPanel.add(startBtn);
        buttonPanel.add(exitBtn);

        gbc.weighty = 0;
        gbc.insets = new Insets(0, 0, 50, 0);
        panel.add(buttonPanel, gbc);
        return panel;
    }

    private JPanel createMenuScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Cafe Menu", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        panel.add(title, BorderLayout.NORTH);

        JPanel itemsPanel = new JPanel(new GridLayout(0, 3, 25, 25));
        for (Item item : menuItems.values()) {
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

            // Image Placeholder/Loader
           ImageIcon icon = new ImageIcon(item.imagePath);
           Image img = icon.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH);
           JLabel imgLabel = new JLabel(new ImageIcon(img));
           imgLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JPanel details = new JPanel(new GridLayout(3, 1));
            JLabel name = new JLabel(item.name, SwingConstants.CENTER);
            name.setFont(new Font("Arial", Font.BOLD, 18));
            JLabel price = new JLabel("₹" + item.price, SwingConstants.CENTER);
            
            JSpinner quantity = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
            
            JButton addBtn = new JButton("Add to Cart");
            addBtn.addActionListener(e -> {
                item.quantity += (Integer) quantity.getValue();
                if (!cart.contains(item)) cart.add(item);
                JOptionPane.showMessageDialog(this, item.name + " Added!");
            });

            details.add(name);
            details.add(price);
            details.add(quantity);

            itemPanel.add(imgLabel, BorderLayout.NORTH);
            itemPanel.add(details, BorderLayout.CENTER);
            itemPanel.add(addBtn, BorderLayout.SOUTH);
            itemsPanel.add(itemPanel);
        }

        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        JButton billBtn = new JButton("Generate Bill");
        billBtn.setPreferredSize(new Dimension(0, 60));
        billBtn.setFont(new Font("Arial", Font.BOLD, 20));
        billBtn.addActionListener(e -> generateBill());

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(billBtn, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createBillingScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Your Bill", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 40));

        billArea = new JTextArea();
        billArea.setFont(new Font("Monospaced", Font.BOLD, 22));
        billArea.setEditable(false);
        billArea.setMargin(new Insets(50, 50, 50, 50));

        JButton receiptBtn = new JButton("Finalize Order");
        receiptBtn.setFont(new Font("Arial", Font.BOLD, 20));
        receiptBtn.addActionListener(e -> cardLayout.show(mainPanel, "receipt"));

        panel.add(title, BorderLayout.NORTH);
        panel.add(new JScrollPane(billArea), BorderLayout.CENTER);
        panel.add(receiptBtn, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createReceiptScreen() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 228, 181));
        
        receiptArea = new JLabel("", SwingConstants.CENTER);
        receiptArea.setFont(new Font("Arial", Font.BOLD, 45));
        receiptArea.setText("<html><center>Thank You For Visiting our Cafe <br><br>Have a Nice Day✨</center></html>");

        panel.add(receiptArea);
        return panel;
    }

    private void generateBill() {
        StringBuilder bill = new StringBuilder();
        double subtotal = 0;
        
        // Alignment formatting for columns
        bill.append(String.format("%-20s %-10s %-10s\n", "ITEM", "QTY", "PRICE"));
        bill.append("------------------------------------------\n");

        for (Item item : cart) {
            double total = item.price * item.quantity;
            subtotal += total;
            bill.append(String.format("%-20s %-10d ₹%-10.2f\n", item.name, item.quantity, total));
        }

        double cgst = subtotal * 0.025;
        double sgst = subtotal * 0.025;
        double grandTotal = subtotal + cgst + sgst;

        bill.append("------------------------------------------\n");
        bill.append(String.format("%-31s ₹%-10.2f\n", "Subtotal:", subtotal));
        bill.append(String.format("%-31s ₹%-10.2f\n", "CGST (2.5%):", cgst));
        bill.append(String.format("%-31s ₹%-10.2f\n", "SGST (2.5%):", sgst));
        bill.append("==========================================\n");
        bill.append(String.format("%-31s ₹%-10.2f\n", "GRAND TOTAL:", grandTotal));

        billArea.setText(bill.toString());
        cardLayout.show(mainPanel, "billing");
    }
}