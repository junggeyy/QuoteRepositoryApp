import java.time.LocalDate;
import java.util.Scanner;
public class Main {
    private static boolean isLoggedIn = false;
    public static String currUserName = "";
    private static boolean isAdmin = false;

    public static void createAcc(Scanner scanner) {
        System.out.println("------Create Account-------");
        System.out.println("----------------------------");
        System.out.println("Enter your email:");
        String email = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();
        User user = new User(email, password);
        System.out.println("Enter your name:");
        String name = scanner.nextLine();
        user.createAccount(name);
    }

    public static void loginAcc(Scanner scanner) {
        System.out.println("Enter your email:");
        String email = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();
        User user = new User(email, password);
        isLoggedIn = user.login();
        currUserName = user.getUserName();
        if (isLoggedIn) {
            System.out.println("Logged in successfully!");
            if (email.equals("admin@gmail.com") && password.equals("admin123")) {
                isAdmin = true;
                System.out.println("Logged in as Admin!");
            }
        } else {
            System.out.println("Invalid email or password");
        }
    }

    public static void main(String[] args) {
        Quote q = new Quote();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (isAdmin) {
                System.out.println("1. List all the quotes.");
                System.out.println("2. List all the authors.");
                System.out.println("3. List all the users");
                System.out.println("4. Quit");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        q.listAllQuotes(scanner, true, "admin");
                        break;
                    case 2:
                        q.listAllAuthors();
                        break;
                    case 3:
                        q.listAllUsers();
                        break;
                    case 4:
                        System.exit(1);
                        break;
                }
            } else {

                System.out.println("1. List all the quotes.");
                System.out.println("2. List all the authors.");
                System.out.println("3. Add a quote and an author");
                System.out.println("4. Search for an author");
                System.out.println("5. Get a random quote");
                System.out.println("6. Create an account");
                System.out.println("7. Log in to your account");
                System.out.println("8. Quit");
                System.out.println("-------------------------------------");
                System.out.println("Enter your choice");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        q.listAllQuotes(scanner, isLoggedIn, currUserName);
                        break;
                    case 2:
                        q.listAllAuthors();
                        break;
                    case 3:
                        q.addQuoteNAuthor(scanner, isLoggedIn, currUserName);
                        break;
                    case 4:
                        q.searchAuthor(scanner);
                        break;
                    case 5:
                        q.getRandomQuote(scanner, isLoggedIn, currUserName);
                        break;
                    case 6:
                        createAcc(scanner);
                        break;
                    case 7:
                        loginAcc(scanner);
                        break;
                    case 8:
                        System.exit(1);
                    default:
                        break;
                }
            }
        }
    }
}
