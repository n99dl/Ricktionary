import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class DictionaryCommandLine {
    Scanner scanner;
    private DictionaryManagement dictionaryManagement;

    public DictionaryCommandLine() {

        scanner = new Scanner(System.in);
        this.dictionaryManagement = new DictionaryManagement();
    }

    public void showAllWords() {
        System.out.println("No" + "\t" + "| English" + "\t" + "|Vietnamese");
        for (Word temp : dictionaryManagement.getWordList()) {
            System.out.println((temp.getId()) + "\t" + temp.getWord_target() + "\t\t" + temp.getWord_explain());
        }
    }

    private int mainMenu(int vers) {
        int numberOfChoice = 0;
        System.out.println("    Ricktionary " + vers + ".0\n");
        switch (vers) {
            case 1:
                numberOfChoice = 2;
                System.out.println("1. Insert from command line");
                System.out.println("2. Show all words");
                break;
            case 2:
                numberOfChoice = 3;
                System.out.println("1. Insert from file");
                System.out.println("2. Show all words");
                System.out.println("3. Look up word");
                break;
            case 3:
                numberOfChoice = 7;
                System.out.println("1. Insert from file");
                System.out.println("2. Show all words");
                System.out.println("3. Look up word");
                System.out.println("4. Search word");
                System.out.println("5. Insert new word");
                System.out.println("6. Edit word");
                System.out.println("7. Remove word");
        }
        System.out.println("0. Quit");
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine();
        while (!(choice.charAt(0) >= '1' && choice.charAt(0) <= '0'+ numberOfChoice)) {
            System.out.println("Please enter number a valid choice");
            System.out.print("Enter choice: ");
            choice = scanner.nextLine();
        }
        return Integer.parseInt("" + choice.charAt(0));
    }

    public void dictionaryAdvanced() {
        dictionaryManagement.loadScanner(scanner);
        int choice = -1;
        while (choice != 0) {
            choice = mainMenu(2);
            switch (choice) {
                case 1:
                    dictionaryManagement.insertFromFile();
                    break;
                case 2:
                    showAllWords();
                    break;
                case 3:
                    dictionaryManagement.dictionaryLookup();
                    break;
            }
            if (choice != 0)
                System.out.println("Press Enter to back to Main menu");
            scanner.nextLine();
        }
    }

    public void dictionaryBasic() {
        dictionaryManagement.loadScanner(scanner);
        int choice = -1;
        while (choice != 0) {
            choice = mainMenu(1);
            switch (choice) {
                case 1:
                    dictionaryManagement.insertFromCommandLine();
                    break;
                case 2:
                    showAllWords();
                    break;
            }
            if (choice != 0)
                System.out.println("Press Enter to back to Main menu");
            scanner.nextLine();
        }
    }

    public void dictionarySearcher() {
        dictionaryManagement.loadScanner(scanner);
        int choice = -1;
        while (choice != 0) {
            choice = mainMenu(3);
            switch (choice) {
                case 1:
                    dictionaryManagement.insertFromFile();
                    break;
                case 2:
                    showAllWords();
                    break;
                case 3:
                    dictionaryManagement.dictionaryLookup();
                    break;
                case 4:
                    dictionaryManagement.dictionarySearch();
                    break;
                case 5:
                    dictionaryManagement.insertWordFromCommandLine();
                    break;
                case 6:
                    dictionaryManagement.editWordFromCommandLine();
                    break;
                case 7:
                    dictionaryManagement.removeWordFromCommandLine();
                    break;
            }
            if (choice != 0)
                System.out.println("Press Enter to back to Main menu");
            scanner.nextLine();
        }
    }

    public void dictionaryExportToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("dictionaries_out.txt"));) {
            pw.write("No" + "\t" + "| English" + "\t" + "|Vietnamese" + "\n");
            for (Word word : dictionaryManagement.getWordList()) {
                pw.write((word.getId()) + "\t" + word.getWord_target().toString() + "\t\t" + word.getWord_explain().toString() + "\n");
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

