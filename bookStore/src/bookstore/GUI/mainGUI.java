package bookstore.GUI;

import bookstore.Inventory;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The main GUI. Renders a window that allows a person to add add and sell
 * books.
 *
 * @author Vassily
 */
public class mainGUI extends JFrame {

    Inventory books;
    bookListPanel scroller;
    addWindow adder;
    sellWindow seller;
    bookStatusPanel book;

    public mainGUI(Inventory books) {
        
        

        ImageIcon[] icons = new imageIconLoader().getImages();

        setIconImage(icons[5].getImage());

        setLocationRelativeTo(null);
        this.books = books;
        book = new bookStatusPanel();
        scroller = new bookListPanel(books, book);

        // Window closer
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        this.setMinimumSize(new Dimension(575,400));
        setTitle("Webbazon Bookstore");

        JPanel horizontal = new JPanel();
        horizontal.setLayout(new BoxLayout(horizontal, BoxLayout.Y_AXIS));
        add(horizontal);

        JLabel name = new JLabel("", SwingConstants.CENTER);
        name.setIcon(icons[3]);

        name.setFont(new Font("Helvetica", 1, 30));
        name.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        horizontal.add(name);

        JPanel buttonRow = new JPanel();
        horizontal.add(buttonRow);
        buttonRow.setLayout(new BoxLayout(buttonRow, BoxLayout.X_AXIS));

        buttonRow.add(Box.createRigidArea(new Dimension(30, 0)));

        JButton inventory = new JButton("Add inventory");
        inventory.setIcon(icons[0]);
        inventory.setMargin(new Insets(5, 5, 5, 5));
        buttonRow.add(inventory);

        // adds inventory sub-menu. Only 1 is ever created.
        inventory.addActionListener(inventoryWindow.make(this));

        buttonRow.add(Box.createHorizontalGlue());

        JButton add = new JButton("Add items");
        add.setIcon(icons[1]);
        add.setMargin(new Insets(5, 5, 5, 5));
        buttonRow.add(add);

        adder = addWindow.make(this);
        add.addActionListener(adder);

        buttonRow.add(Box.createRigidArea(new Dimension(30, 0)));

        JButton sell = new JButton("Sell items");
        sell.setIcon(icons[2]);
        sell.setMargin(new Insets(5, 5, 5, 5));
        buttonRow.add(sell);

        seller = new sellWindow(this);
        sell.addActionListener(seller);

        buttonRow.add(Box.createRigidArea(new Dimension(20, 0)));

        /*
         JButton open = new JButton("Open Save Directory");
         FileChooser chooser = new FileChooser();
         open.setMargin(new Insets(5, 5, 5, 5));
         open.addActionListener(chooser);
         buttonRow.add(open);
        
         buttonRow.add(Box.createRigidArea(new Dimension(20, 0)));

         */
        
        JButton lock = new JButton("Lock");
        lock.setIcon(icons[6]);
        lock.setMargin(new Insets(5, 5, 5, 5));
        lock.addActionListener(new passwordWindow(this));
        buttonRow.add(lock);

        buttonRow.add(Box.createRigidArea(new Dimension(20, 0)));

        horizontal.add(scroller.getScroller());
        horizontal.add(book);

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Obtains the inventory used by this GUI
     *
     * @return
     */
    public Inventory getInventory() {
        return books;
    }

    /**
     * Adds a number of books and an amount of money to the total books sold.
     *
     * @param copies number of books
     * @param money amount of money
     */
    public void addToTotal(int copies, double money) {
        book.addToTotal(copies, money);
    }

    /**
     * Updates the list of books to reflect the state of the Inventory.
     */
    public void updateList() {
        scroller.update();
        adder.update();
        seller.update();
    }

    /**
     * Creates an instance of the GUI.
     *
     * @param inventory The inventory that will be displayed.
     */
    public static void make(final Inventory inventory) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame f = new mainGUI(inventory);
                f.setVisible(true);

            }
        });
    }
}
