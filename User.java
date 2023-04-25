import java.util.Scanner;

public class User {
    private String email;

    private String password;

    private String logged;

    public User(){
        this.email = null;
        this.password = null;
        this.logged = "no";
    }

    public String getEmail(){
        return this.email;
    }
    public String getPassword(){
        return this.password;
    }

    public static void createAccount(){
        Scanner input = new Scanner(System.in);
        System.out.println("------Create Account-------");
        System.out.println("----------------------------");
        System.out.print("Enter your name: ");
        String name = input.nextLine();
        System.out.print("Enter your email: ");
        String email = input.nextLine();
        do {
            System.out.print("Create your password: ");
            String password = input.nextLine();
            System.out.print("Enter your password again to confirm: ");
            String passwordCheck = input.nextLine();
            if (password.equals(passwordCheck)) {
                System.out.println("Account created successfully!");
                //save in file email;password;name
                break;
            } else {
                System.out.println("Passwords do not match! Try again!!");
            }
        }while(true);
        System.out.println("----------------------------");
    }
    public static void login(){
        Scanner input = new Scanner(System.in);
        System.out.println("-------Log in--------");
        System.out.print("Enter your email: ");
        String email = input.nextLine();
        System.out.print("Enter your password: ");
        String password = input.nextLine();
        //check details from file
        //if details found: logged = true?

    }
    public void isLogged(){
        this.logged="yes";
    }

    public void logout(){
        //log user out?
        this.logged="no";
    }

}
