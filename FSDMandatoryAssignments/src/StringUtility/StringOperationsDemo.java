package StringUtility;


import java.util.Scanner;

public class StringOperationsDemo {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        StringUtility obj = new StringUtility();

        int choice;

        do {

            System.out.println("\n===== STRING OPERATIONS =====");
            System.out.println("1. Extract Substring");
            System.out.println("2. Split Sentence");
            System.out.println("3. Exit");
            System.out.print("Enter Choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Enter String: ");
                    String str = sc.nextLine();

                    int start, end;

                    while (true) {

                        System.out.print("Enter Start Index: ");
                        start = sc.nextInt();

                        System.out.print("Enter End Index: ");
                        end = sc.nextInt();
                        sc.nextLine();

                        if (start >= 0 && end <= str.length() && start < end) {
                            break;
                        }

                        System.out.println("Invalid Index! Try Again.");
                    }

                    obj.extractSubstring(str, start, end);
                    break;

                case 2:

                    System.out.print("Enter Sentence: ");
                    String sentence = sc.nextLine();

                    obj.splitSentence(sentence);
                    break;

                case 3:

                    System.out.println("Thank You!");
                    break;

                default:

                    System.out.println("Invalid Choice!");

            }

        } while (choice != 3);

        sc.close();
    }
}