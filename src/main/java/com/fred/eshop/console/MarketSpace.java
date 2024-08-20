package com.fred.eshop.console;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import com.fred.eshop.dao.ProductDAO;
import com.fred.eshop.dao.ProductDAOMySql;
import com.fred.eshop.model.Component;
import com.fred.eshop.model.Computer;
import com.fred.eshop.model.ComputerBase;
import com.fred.eshop.model.Order;
import com.fred.eshop.model.Product;
import com.fred.eshop.model.SortStrategy;
import com.fred.eshop.service.OrderService;
import com.fred.eshop.service.ProductService;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class MarketSpace {
    private static MarketSpace instance = new MarketSpace();
    private Map<Integer, Product> products = new HashMap<Integer, Product>();
    private List<Computer> cart = new ArrayList<Computer>();

    private MarketSpace() {
        // loadProducts("demo/products.csv");
        try {
            loadProductsDB();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static MarketSpace getInstance() {
        return MarketSpace.instance;
    }

    public void loadProducts(String fname) {
        try {
            File file = new File(fname);
            Scanner sc = new Scanner(file);
            String line, tokens[];
            Product product;
            int i = 1;
            while (sc.hasNextLine()) {
                line = sc.nextLine().strip();
                tokens = line.split(",");
                product = new Product(tokens[0], (float)Double.parseDouble(tokens[1]), Integer.parseInt(tokens[2]), tokens[3]);
                products.put(i++, product);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found.");
        }

        System.out.println(products);
    }

    public void loadProductsDB() throws SQLException {
        ProductDAO dao = new ProductDAOMySql();
        List<Product> pList = dao.readAll();

        int i = 1;    
        for (Product product : pList) 
            if (product.getQuantity() > 0)
                products.put(i++, product);
    }    
 
    public void buyComputer() {
        Computer computer = new ComputerBase();
        Map<Integer, Product> components = new HashMap<>();

        int size = products.size() + 1;
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.printf("Current Build: %s, and total price is %.1f\n", computer.getDescription(), computer.getPrice());
            System.out.println("What component would you like to add? ");
            for (Map.Entry<Integer, Product> pair : products.entrySet()) {
                Product product = pair.getValue();
                System.out.printf("%d: %s $%.2f\n", pair.getKey(), product.getDescription(), product.getPrice());
            }
            System.out.println(size +": Done");

            // Read in component choice and add to the base computer
            if (sc.hasNextInt()) {
                int c = sc.nextInt();
                if (c == size)
                    break;
                if (c>size || c<0) {
                    System.out.println("Invalid input");
                    continue;
                }

                Product product = products.get(c);
                int stock = product.getQuantity();
                if (stock > 0) {
                    Product component = new Product(product.getDescription(), product.getPrice(), 1, product.getImage());
                    component.setID(product.getID());

                    if (components.containsKey(component.getID()))
                        component.setQuantity(components.get(component.getID()).getQuantity()+1);
                    
                    components.put(component.getID(), component);
                    
                    stock--;
                    product.setQuantity(stock);
                } else {
                    System.out.println("No more " + product.getDescription() + " is available. Please choose another component.");
                }
                
                computer = new Component(computer, product.getDescription(), product.getPrice());
            } else 
                System.out.println("\nInvalid input: " + sc.next() + "\n");
        }

        OrderService orderService = new OrderService();
        Date now = Calendar.getInstance().getTime();
        Order order = new Order(computer.getOrderID(), computer.getDescription(), (float) computer.getPrice(), now, new ArrayList<>(components.values()));
        orderService.create(order);

        ProductService productService = new ProductService();
        productService.updateAll(new ArrayList<>(products.values()));

        cart.add(computer);
    }

    public String getCart() {
        String cartStr = "[", fmt;
        for (Computer computer : cart) {
            // fmt = (cart.indexOf(computer) == cart.size()-1) ? "OrderID@%s: %s $%.2f" : "OrderID@%s: %s $%.2f, ";
            fmt = "OrderID@%s: %s $%.2f" + ((cart.indexOf(computer) == cart.size()-1) ? "" : ", ");
            cartStr += String.format(fmt, computer.getOrderID(), computer.getDescription(), computer.getPrice());
        }

        cartStr += "]";
        return cartStr;
    }

    public String getOrders() {
        List<Order> orders = new OrderService().readAll();

        String orderStr = "[", fmt;
        for (Order order : orders) {
            fmt = "OrderID@%s: %s $%.2f" + ((orders.indexOf(order) == orders.size()-1) ? "" : ", ");
            orderStr += String.format(fmt, order.getOrderID(), order.getDescription(), order.getPrice(), order.getDate());
        }

        orderStr += "]";
        return orderStr;
    }

    public void sort(SortStrategy strategy) {
        strategy.sort(cart);
    }
}
