import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class DictionaryCommandLine {
    Scanner scanner;
    private DictionaryManagement dictionaryManagement;

    /**
     * initiate command line application with dictionary management
     */
    public DictionaryCommandLine() {

        scanner = new Scanner(System.in);
        this.dictionaryManagement = new DictionaryManagement();
    }

    /**
     * Show all word to cmd
     */
    public void showAllWords() {
        System.out.println("No" + "\t" + "| English" + "\t" + "|Vietnamese");
        for (Word temp : dictionaryManagement.getWordList()) {
            System.out.println((temp.getId()) + "\t" + temp.getWord_target() + "\t\t" + temp.getWord_explain());
        }
    }

    /**
     * Main menu for the 3 version
     * @param vers
     * @return
     */
    private int mainMenu(int vers) {
        cls();
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
                numberOfChoice = 8;
                System.out.println("1. Insert from file");
                System.out.println("2. Show all words");
                System.out.println("3. Look up word");
                System.out.println("4. Search word");
                System.out.println("5. Insert new word");
                System.out.println("6. Edit word");
                System.out.println("7. Remove word");
                System.out.println("8 .Export to file");
                break;
        }
        System.out.println("0. Quit");
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine();
        while (choice.isEmpty()||!(choice.charAt(0) >= '0' && choice.charAt(0) <= '0' + numberOfChoice)) {
            System.out.println("Please enter number a valid choice");
            System.out.print("Enter choice: ");
            choice = scanner.nextLine();
        }
        if ( Integer.parseInt("" + choice.charAt(0)) == 0)
            System.exit(0);
        return Integer.parseInt("" + choice.charAt(0));
    }

    /**
     * Second version
     * insert from file
     * dictionaryLookup
     */
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

    /**
     * first version : DictionaryBasic
     * show all word
     * insert from command line
     */
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

    /**
     * third version: dictionary searcher
     * all required features : edit, add , remove
     * search words with prefix
     */
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
                case 8:
                    dictionaryExportToFile();
                    break;
            }
            if (choice != 0)
                System.out.println("Press Enter to back to Main menu");
            scanner.nextLine();
        }
    }

    /**
     * Export dictionary to file
     */
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

    /**
     * clear screen (bad version, will implement)
     */
    public void cls(){
        try {

            if( System.getProperty( "os.name" ).startsWith( "Window" ) ) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }


        } catch (IOException e) {

            for(int i = 0; i < 50; i++) {
                System.out.println();
            }

        }
    }

    /**
     * Run application with version choice
     */
    public void run() {
        cls();
        int version = 0;
        System.out.print("Choose your version: ");
        version = scanner.nextInt();
        while (version < 1 || version > 3) {
            System.out.println("Please enter a valid version (1-3)");
            System.out.print("Choose your version: ");
            version = scanner.nextInt();
        }
        scanner.nextLine();
        switch (version) {
            case 1:
                dictionaryBasic();
                break;
            case 2:
                dictionaryAdvanced();
                break;
            case 3:
                dictionarySearcher();
                break;
        }
    }

}

