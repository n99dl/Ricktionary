
import java.util.ArrayList;

public class Dictionary {
    private Trie trie = new Trie();
    private ArrayList<Word> wordList = new ArrayList<Word>();

    public void insertWord(Word word) {
        trie.insertToTree(word);
        wordList.add(word);
    }

    public ArrayList<Word> getWordList() {
        return wordList;
    }

    public Word getWord(int index) {
        return wordList.get(index);
    }

    public Word lookupWord(String word) {
        ArrayList<Integer> arrayListWord = trie.searchInTree(word, 1);
        if (arrayListWord.isEmpty()) {
            return null;
        }
        return wordList.get(arrayListWord.get(0));
    }
}
