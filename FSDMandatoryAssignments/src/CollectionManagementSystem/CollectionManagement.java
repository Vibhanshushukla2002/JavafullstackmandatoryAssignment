package CollectionManagementSystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CollectionManagement {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Objects
        ListManager list = new ListManager();
        SetManager set = new SetManager();
        MapManager map = new MapManager();

        int choice = 0;

        do {

            System.out.println("\n===== COLLECTION MANAGEMENT SYSTEM =====");
            System.out.println("1. List Operations");
            System.out.println("2. Set Operations");
            System.out.println("3. Map Operations");
            System.out.println("4. Exit");
            System.out.print("Enter Choice: ");

            try {

                choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        list.listMenu(sc);
                        break;

                    case 2:
                        set.setMenu(sc);
                        break;

                    case 3:
                        map.mapMenu(sc);
                        break;

                    case 4:
                        System.out.println("Thank You!");
                        break;

                    default:
                        System.out.println("Invalid Choice!");
                }

            } catch (InputMismatchException e) {

                System.out.println("Please Enter Numbers Only.");
                sc.nextLine(); // Clear invalid input
            }

        } while (choice != 4);

        sc.close();
    }
}