package CollectionManagementSystem;

import java.util.*;

public class MapManager {

    HashMap<Integer, String> map = new HashMap<>();

    public void addElement(Scanner sc) {

        try {

            System.out.print("Enter ID : ");
            int id = sc.nextInt();

            if (map.containsKey(id))
                throw new Exception("Duplicate Key.");

            System.out.print("Enter Name : ");
            String name = sc.next();

            map.put(id, name);

            System.out.println("Record Added Successfully.");

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());

        }
    }

    public void removeElement(Scanner sc) {

        try {

            System.out.print("Enter ID : ");
            int id = sc.nextInt();

            if (map.remove(id) == null)
                throw new Exception("ID Not Found.");

            System.out.println("Record Removed.");

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());

        }
    }

    public void displayElements() {

        try {

            if (map.isEmpty())
                throw new Exception("Map is Empty.");

            for (Map.Entry<Integer, String> entry : map.entrySet()) {

                System.out.println(entry.getKey() + " -> " + entry.getValue());

            }

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());

        }
    }
    public void mapMenu(Scanner sc) {

        int choice;

        do {

            System.out.println("\n----- MAP MENU -----");
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