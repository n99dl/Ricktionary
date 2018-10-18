import java.util.ArrayList;

public class Dictionary {
    /**
     * Dictionary with trie implement
     */
    private Trie trie;
    int size = 0;

    /**
     * Constructor
     */
    public Dictionary() {
        trie = new Trie();
    }

    /**
     * Insert word
     * @param newWord
     * @param newWordExplanation
     */
    public void insertWord(String newWord, String newWordExplanation) {
        Word word = new Word(newWord, newWordExplanation, ++size);
        trie.insertToTree(word);
    }

    /**
     * Remove word
     * @param removedWord
     * @return
     */
    public boolean removeWord(String removedWord) {
        Word word = lookupWord(removedWord);
        if (word != null) {
            trie.removeFromTree(removedWord);
            size--;
            return true;
        }
        return false;
    }

    /**
     * get the list of words in dictionary
     * @return
     */
    public ArrayList<Word> getWordList() {
        ArrayList<Word> wordArrayList = trie.getAllWords();
        int c = 0;
        for (Word word : wordArrayList){
            word.setId(++c);
        }
        return wordArrayList;
    }

    /**
     * look up word
     * @param word
     * @return
     */
    public Word lookupWord(String word) {

        ArrayList<Word> arrayListWord = trie.searchInTree(word, 1);
        if (arrayListWord.isEmpty()) {
            System.out.println("No word found !");
            return null;
        }
        return arrayListWord.get(0);
    }

    /**
     * search same prefix words
     * @param word
     * @return
     */
    public ArrayList<Word> searchWord(String word) {
        ArrayList<Word> arrayListWord = trie.searchInTree(word, 50);
        if (arrayListWord.isEmpty()) {
            System.out.println("No word found !");
            return null;
        }
        return arrayListWord;
    }
}

