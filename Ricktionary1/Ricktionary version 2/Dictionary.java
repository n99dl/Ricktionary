import java.util.ArrayList;

public class Dictionary {
    private Trie trie;
    private ArrayList<Word> wordList;

    public Dictionary() {
        trie = new Trie();
        wordList = new ArrayList<>();
    }

    public void insertWord(String newWord, String newWordExplanation) {
        Word word = new Word(newWord, newWordExplanation, wordList.size() + 1);
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
        ArrayList<Word> arrayListWord = trie.searchInTree(word, 1);
        if (arrayListWord.isEmpty()) {
            System.out.println("No word found !");
            return null;
        }
        return arrayListWord.get(0);
    }
}
