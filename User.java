import java.io.*;
import java.nio.file.*;

public class User {
    private String email;
    private String password;
    private boolean logged;
    public String userName = "";

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.logged = false;
    }

    public void createAccount(String name) {
        try {
            String userRecord = email + ";" + password + ";" + name + "\n";
            Files.write(Paths.get("csv/users.csv"), userRecord.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean login() {
        try (BufferedReader br = new BufferedReader(new FileReader("csv/users.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                if (values[0].equals(email) && values[1].equals(password)) {
                    logged = true;
                    userName = values[2];
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isLogged() {
        return logged;
    }

    public void logout() {
        logged = false;
    }

    public String getUserName() {
        return userName;
    }
}
