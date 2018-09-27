
import java.util.ArrayList;

public class Dictionary {
    private Trie trie;
    private ArrayList<Word> wordList;

    public Dictionary(){
        trie = new Trie();
        wordList = new ArrayList<>();
    }

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
