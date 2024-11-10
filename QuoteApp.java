import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Quote {
    private String author;
    private String text;
    private String editor;
    private LocalDate addDate;
    private int quoteLen;

    public static int generateRandomNumber(int quoteLen) {
        long currentTimeMillis = System.currentTimeMillis();
        int randomNumber = (int) (currentTimeMillis % quoteLen);
        return randomNumber;
    }

    public Quote() {

    }

    public Quote(String author, String text, String editor, LocalDate addDate) {
        this.author = author;
        this.text = text;
        this.editor = editor;
        this.addDate = addDate;
    }

    public void create() {
        try {
            String quoteRecord = author + ";" + text + ";" + addDate + ";" + editor + "\n";
            Files.write(Paths.get("csv/quotes.csv"), quoteRecord.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        System.out.println("Author: " + author);
        System.out.println("Text: " + text);
        System.out.println("Add Date: " + addDate);
        System.out.println("Editor: " + editor);
    }

    public void editQuote(Scanner scanner, boolean isLoggedIn, String currUserName, int currentQuoteIndex) {
        if (!isLoggedIn) {
            System.out.println("Login First!");
            return;
        }
        int count = 0;
        int quoteNo = currentQuoteIndex;
        if (currentQuoteIndex == 0) {
            System.out.println("Enter the quote number");
            quoteNo = scanner.nextInt();
            scanner.nextLine(); // To consume the newline character
        }
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader("csv/data.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                count++;
                if (count == quoteNo) {
                    if ((isLoggedIn && currUserName.equals(values[2])) || currUserName.equals("admin")) {
                        // This is a valid user or admin
                        System.out.println("Enter the new text for the quote:");
                        String newText = scanner.nextLine();
                        sb.append(values[0]).append(';').append(newText).append(';').append(values[2]).append('\n');
                    } else {

                        System.out.println("You do not have permission to edit this quote.");
                        sb.append(line).append('\n');
                    }
                } else {
                    sb.append(line).append('\n');
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (count < quoteNo) {
            System.out.println("Quote Not Available");
            return;
        }

        try {
            Files.write(Paths.get("csv/data.csv"), sb.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteQuote(Scanner scanner, boolean isLoggedIn, String currUserName, int currentQuoteIndex) {
        if (!isLoggedIn) {
            System.out.println("Login First!");
            return;
        }
        int count = 0;
        int quoteNo = currentQuoteIndex;
        if (currentQuoteIndex == 0) {
            System.out.println("Enter the quote number");
            quoteNo = scanner.nextInt();
            scanner.nextLine(); // To consume the newline character
        }
        StringBuilder sb = new StringBuilder();
        boolean quoteDeleted = false;

        try (BufferedReader br = new BufferedReader(new FileReader("csv/data.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");

                if (count == quoteNo) {
                    if ((isLoggedIn && currUserName.equals(values[2])) || currUserName.equals("admin")) {
                        // This is a valid user or admin
                        System.out.println("Quote deleted.");
                        quoteDeleted = true;
                        System.out.println(
                                "isLoggedIn" + isLoggedIn + " currentUser " + currUserName + "values[2]" + values[2]);
                    } else {
                        System.out.println("You do not have permission to delete this quote.");
                        System.out.println(
                                "isLoggedIn" + isLoggedIn + " currentUser " + currUserName + "values[2]" + values[2]);
                        sb.append(line).append('\n');
                    }
                } else {
                    sb.append(line).append('\n');
                }
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!quoteDeleted && count < quoteNo) {
            System.out.println("Quote Not Available");
            return;
        }

        try {
            Files.write(Paths.get("csv/data.csv"), sb.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listAllQuotes(Scanner scanner, boolean isLoggedIn, String currUserName) {
        try (BufferedReader br = new BufferedReader(new FileReader("csv/data.csv"))) {
            String line;
            int count = 0;
            int pages = 0;
            int currPageNo = 1;
            int itemsPerPage = 5;
            System.out.println("--------------------------------");
            br.mark(1 << 24);
            while ((line = br.readLine()) != null) {
                pages++;
            }
            quoteLen = pages;
            br.reset();
            pages = (int) Math.ceil((double) quoteLen / itemsPerPage);
            System.out.println(currPageNo + " of " + pages + " pages ");
            while ((line = br.readLine()) != null) {
                if ((((currPageNo - 1) * 5) == count) && count != 0)
                    System.out.println(currPageNo + " of " + pages + " pages ");
                String[] values = line.split(";");
                System.out.println(count + 1 + ". \"" + values[1] + "\"");
                System.out.println("- " + values[0]);
                System.out.println("Added by " + values[2]);
                count++;
                if (currPageNo == (int) (count / 5)) {
                    currPageNo++;
                    System.out.println("M. Show More");
                    System.out.println("E. Edit Quote");
                    System.out.println("D. Delete Quote");
                    System.out.println("A. Add a quote");
                    System.out.println("B. Back to previous menu");
                    String input = scanner.next();
                    char character = input.charAt(0);
                    switch (character) {
                        case 'M':
                            continue;
                        case 'E':
                            // function edit Quote
                            editQuote(scanner, isLoggedIn, currUserName, 0);
                            break;
                        case 'D':
                            // function delete quote
                            deleteQuote(scanner, isLoggedIn, currUserName, 0);
                            break;
                        case 'A':
                            // function add a quote
                            addQuoteNAuthor(scanner, isLoggedIn, currUserName);
                            break;
                        case 'B':
                            return;
                    }
                    if (character == 'E' || character == 'D' || character == 'A') {
                        return;
                    }
                }
            }
            System.out.println("--------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listAllAuthors() {
        try (BufferedReader br = new BufferedReader(new FileReader("csv/data.csv"))) {
            String line;
            int count = 1;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                System.out.println(count + ". " + values[0]);
                count++;
            }
            System.out.println("--------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listAllUsers() {
        try (BufferedReader br = new BufferedReader(new FileReader("csv/data.csv"))) {
            String line;
            int count = 1;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                System.out.println(count + ". " + values[2]);
                count++;
            }
            System.out.println("--------------------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addQuoteNAuthor(Scanner scanner, boolean isLoggedIn, String AddedBy) {
        if (isLoggedIn) {
            // allowed to add quote
            System.out.println("Enter the author of the quote:");
            String author = scanner.nextLine();
            System.out.println("Enter the text of the quote:");
            String text = scanner.nextLine();
            try {
                String quoteRecord = author + ";" + text + ";" + AddedBy + "\n";
                Files.write(Paths.get("csv/data.csv"), quoteRecord.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Login First!");
        }
    }

    public void addAuthor(Scanner scanner) {

    }

    public void searchAuthor(Scanner scanner) {
        String csvFile = "csv/data.csv"; // Replace with the path to your CSV file
        String line;
        System.out.println("Enter the author name to search..");
        String nameToSearch = scanner.nextLine();// Replace with the name you want to search for

        // Define the regex pattern to search for names
        String regex = "^.*" + Pattern.quote(nameToSearch) + ".*$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            boolean foundSomeOne = false;
            while ((line = br.readLine()) != null) {
                // Apply the regex pattern to each line in the CSV file
                String[] values = line.split(";");
                Matcher matcher = pattern.matcher(values[0]);
                if (matcher.find()) {
                    System.out.println("Related Author: " + values[0]);
                    foundSomeOne = true;
                }
            }
            if (foundSomeOne == false) {
                System.out.println("No Related Author Found!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getRandomQuote(Scanner scanner, boolean isLoggedIn, String currUserName) {
        int currentQuoteIndex = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("csv/data.csv"))) {
            String line;
            System.out.println("--------------------------------");
            System.out.println("Random Quote: ");
            int quoteLen = 0;
            br.mark(1 << 24); // Mark the beginning of the file with a large read-ahead limit
            while ((line = br.readLine()) != null) {
                quoteLen++;
            }
            br.reset(); // Reset the reader to the beginning of the file

            int randomNum = generateRandomNumber(quoteLen);
            while ((line = br.readLine()) != null) {
                if (currentQuoteIndex == randomNum) {
                    String[] values = line.split(";");
                    System.out.println("1. \"" + values[1] + "\"");
                    System.out.println("- " + values[0]);
                    System.out.println("Added by " + values[2]);
                    break;
                }
                currentQuoteIndex++;
            }
            System.out.println("--------------------------------");

            // Menu options
            while (true) {
                System.out.println("M. Show another Random Quote");
                System.out.println("E. Edit Quote");
                System.out.println("D. Delete Quote");
                System.out.println("A. Add a quote");
                System.out.println("B. Back to previous menu");
                String input = scanner.next();
                char character = input.charAt(0);
                switch (character) {
                    case 'M':
                        getRandomQuote(scanner, isLoggedIn, currUserName);
                        return;
                    case 'E':
                        // function edit Quote
                        editQuote(scanner, isLoggedIn, currUserName, currentQuoteIndex);
                        break;
                    case 'D':
                        // function delete quote
                        deleteQuote(scanner, isLoggedIn, currUserName, currentQuoteIndex);
                        break;
                    case 'A':
                        // function add a quote
                        addQuoteNAuthor(scanner, isLoggedIn, currUserName);
                        break;
                    case 'B':
                        return;
                }
                if (character == 'E' || character == 'D' || character == 'A') {
                    return;
                }
                currentQuoteIndex = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
