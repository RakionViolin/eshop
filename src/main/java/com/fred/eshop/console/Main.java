package com.fred.eshop.console;

import java.util.Scanner;

import com.fred.eshop.model.SortByOrderID;
import com.fred.eshop.model.SortByPrice;

import java.util.Arrays;

public class Main {
    private static MarketSpace marketSpace = MarketSpace.getInstance();
    private static Admin admin = Admin.getInstance();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            mainMenu();

            int c = sc.nextInt();
            switch (c) {
                case 1:
                    marketSpace.buyComputer();
                    break;
                case 2:
                    System.out.println(marketSpace.getCart());
                    break;
                case 3:
                    marketSpace.sort(new SortByOrderID());
                    break;
                case 4:
                    marketSpace.sort(new SortByPrice());
                    break;
                case 5:
                    admin.admin();
                    break;
                case 6:
                    System.out.println(marketSpace.getOrders());
                    break;    
                case 7:
                    System.out.println("Goodbye");
                    System.exit(0);                                    
                default:
                    System.out.printf("\nInvalid choice %d. Please choose 1-5.\n", c);
            }
        }
    }

    public static void mainMenu() {
        String mainMenu[] = {
            "Hi, what would you like to do?", 
            "1: Buy a computer",
            "2: See my shopping cart",
            "3: Sort by order ID (Descending order)",
            "4: Sort by order price (Descending order)",
            "5: Manage computer components",  
            "6: See my orders",                      
            "7: Quit"
        };

        // for (String menu : mainMenu)
        //    System.out.println(menu);
        
        Arrays.stream(mainMenu).forEach(System.out::println);
    }  
}
