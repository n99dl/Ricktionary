public class Main {

    public static void main(String[] args) {
        DictionaryManagement dictionaryManagement = new DictionaryManagement();
        dictionaryManagement.insertFromFile();
        DictionaryCommandline.showAllWords(dictionaryManagement);
        // Word temp = new Word("Hello","Xin Chao", 1);
        // temp.print();
    }
}
