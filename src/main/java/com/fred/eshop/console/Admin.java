package com.fred.eshop.console;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.fred.eshop.dao.ProductDAO;
import com.fred.eshop.dao.ProductDAOMySql;
import com.fred.eshop.model.Product;

import java.sql.SQLException;

public class Admin {
    private static Admin instance = new Admin();
    Scanner sc;
    private ProductDAO dao;

    private Admin() {
        this.dao = new ProductDAOMySql();
        sc = new Scanner(System.in);
    }

    public static Admin getInstance() {
        return Admin.instance;
    }

    public void admin() {
        while (true) {    
            adminMenu();
            try {
                int c = sc.nextInt();
                switch (c) {
                    case 1:
                        create();
                        break;
                    case 2:
                        read();
                        break;
                    case 3:
                        readAll();
                        break;
                    case 4:
                        update();
                        break;
                    case 5:
                        delete();
                        break;
                    case 6:
                        return;
                    default:
                        System.out.printf("\nInvalid choice %d. Please choose 1-6.\n", c);
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }            

    public void adminMenu() {
        String adminMenu[] = {
            "Backend Administration for Computer Components", 
            "1: Create a new component",
            "2: Read a component",
            "3: Read all components",
            "4: Update a component",
            "5: Delete a component",            
            "6: Return to Main Menu"
        };

        for (String menu : adminMenu)
            System.out.println(menu);
    }    

    private void create() throws SQLException {
        System.out.println("Please input compoent info:");
        System.out.print("Component description: ");
        String desc = sc.next();
        System.out.print("Component price: ");
        float price = sc.nextFloat();
        System.out.print("Component quantity: ");
        int quantity = sc.nextInt();        
        System.out.print("Component image: ");
        String image = sc.next();
        Product product = new Product(desc, price, quantity, image);
        dao.create(product);
        MarketSpace.getInstance().loadProductsDB();
    }

    private void read() throws SQLException {
        System.out.print("Please input compoent ID: ");
        int id = sc.nextInt();
        Product product = dao.read(id);
        System.out.println(product);
    }

    private void readAll() throws SQLException {
        System.out.println("Component list: ");
        List<Product> products = dao.readAll();
        for (Product product : products) 
            System.out.println(product);
    }

    private void delete() throws SQLException {
        System.out.print("Please input compoent ID: ");
        int id = sc.nextInt();
        int n = dao.delete(id);
        if (n>0)
            System.out.println(n + " row(s) deleted");
        else   
            System.out.println(id + " not deleted");
    }

    public void update() throws SQLException{
        ProductDAO dao = new ProductDAOMySql();
        Scanner sc = new Scanner(System.in);
        System.out.print("Product to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        Product product = dao.read(id);
        System.out.println(product);
        System.out.println("Enter without input for no change");
        
        System.out.printf("Product new name: ");
        String str = sc.nextLine();
        if (!str.isEmpty())
            product.setDescription(str);

        System.out.printf("Product new price: ");
        String priceStr = sc.nextLine();
        if (!priceStr.isEmpty()) {
            float price = Float.parseFloat(priceStr);
            if (price > 0)
            product.setPrice(price);
        }
 
        System.out.printf("Product new quantity: ");
        String quantityStr = sc.nextLine();
        if (!quantityStr.isEmpty()) {
            int quantity = Integer.parseInt(quantityStr);
            if (quantity >= 0)
            product.setQuantity(quantity);
        }

        int row = dao.update(product);
        System.out.println(row + "");
    }    
}
