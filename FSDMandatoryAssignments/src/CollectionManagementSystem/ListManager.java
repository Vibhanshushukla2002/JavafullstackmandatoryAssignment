package CollectionManagementSystem;

import java.util.*;

public class ListManager {

    ArrayList<String> list = new ArrayList<>();

    public void addElement(Scanner sc) {

        try {

            System.out.print("Enter Element: ");
            String element = sc.next();

            list.add(element);

            System.out.println("Element Added Successfully.");

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());

        }
    }

    public void removeElement(Scanner sc) {

        try {

            System.out.print("Enter Element: ");
            String element = sc.next();

            if (list.remove(element))
                System.out.println("Element Removed Successfully.");
            else
                throw new Exception("Element Not Found.");

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());

        }
    }

    public void displayElements() {

        try {

            if (list.isEmpty())
                throw new Exception("List is Empty.");

            System.out.println(list);

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());

        }
    }
    
    public void listMenu(Scanner sc) {

        int choice;

        do {

            System.out.println("\n----- LIST MENU -----");
            System.out.println("1. Add");
            System.out.println("2. Remove");
            System.out.println("3. Display");
            System.out.println("4. Back");
            System.out.print("Enter Choice : ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    addElement(sc);
                    break;

                case 2:
                    removeElement(sc);
                    break;

                case 3:
                    displayElements();
                    break;

                case 4:
                    System.out.println("Returning...");
                    break;

                default:
                    System.out.println("Invalid Choice.");
            }

        } while (choice != 4);
    }
}