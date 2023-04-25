import java.util.Scanner;

public class QuoteApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Boolean again = true;
        System.out.println("--------------------------------------");
        System.out.println("Welcome User, what do you want to do?");
        do {
            System.out.println("1. List all the quotes.");
            System.out.println("2. List all the authors.");
            System.out.println("3. Add a quote");
            System.out.println("4. Add an author");
            System.out.println("5. Search for an author");
            System.out.println("6. Get a random quote");
            System.out.println("7. Create an account");
            System.out.println("8. Log in to your account");
            System.out.println("9. Quit");
            System.out.println("-------------------------------------");
            int choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Yet to do!");
                    break;
                case 2:
                    System.out.println("Yet to do!");
                    break;
                case 3:
                    System.out.println("Yet to do!");
                    break;
                case 4:
                    System.out.println("Yet to do!");
                    break;
                case 5:
                    System.out.println("Yet to do!");
                    break;
                case 6:
                    System.out.println("Yet to do!");
                    break;
                case 7:
                    User.createAccount();
                    break;
                case 8:
                    User.login();
                    break;
                case 9:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice!");
                    System.out.println("Enter a correct choice:");
                    again = false;
            }

        }while(!again);

    }
}
