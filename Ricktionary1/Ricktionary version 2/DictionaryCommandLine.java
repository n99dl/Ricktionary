class DictionaryCommandline {

    public static void showAllWords(DictionaryManagement dictionaryManagement) {
        System.out.println("No" + "\t" + "| English" + "\t" + "|Vietnamese");
        for (Word temp : dictionaryManagement.getWordList()) {
            System.out.println((temp.getId() + 1) + "\t" + temp.getWord_target() + "\t" + temp.getWord_explaint());
        }
    }

}

public class DictionaryCommandLine {
    public static void dictionaryAdvanced(DictionaryManagement dictionaryManagemen) {
        dictionaryManagemen.insertFromFile();
        DictionaryCommandline.showAllWords(dictionaryManagemen);
        dictionaryManagemen.dictionaryLookup();
    }

    public void dictionaryBasic(DictionaryManagement dictionaryManagement) {
        dictionaryManagement.insertFromCommandLine();
        DictionaryCommandline.showAllWords(dictionaryManagement);
    }
}
