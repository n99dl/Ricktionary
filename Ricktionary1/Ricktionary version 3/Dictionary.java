import java.util.ArrayList;

public class Dictionary {
    private Trie trie;
    int size = 0;

    public Dictionary() {
        trie = new Trie();
    }

    public void insertWord(String newWord, String newWordExplanation) {
        Word word = new Word(newWord, newWordExplanation, ++size);
        trie.insertToTree(word);
    }

    public boolean removeWord(String removedWord) {
        Word word = lookupWord(removedWord);
        if (word != null) {
            trie.removeFromTree(removedWord);
            size--;
            return true;
        }
        return false;
    }

    public ArrayList<Word> getWordList() {
        ArrayList<Word> wordArrayList = trie.getAllWords();
        int c = 0;
        for (Word word : wordArrayList){
            word.setId(++c);
        }
        return wordArrayList;
    }

    public Word lookupWord(String word) {

        ArrayList<Word> arrayListWord = trie.searchInTree(word, 1);
        if (arrayListWord.isEmpty()) {
            System.out.println("No word found !");
            return null;
        }
        return arrayListWord.get(0);
    }

    public ArrayList<Word> searchWord(String word) {
        ArrayList<Word> arrayListWord = trie.searchInTree(word, 50);
        if (arrayListWord.isEmpty()) {
            System.out.println("No word found !");
            return null;
        }
        return arrayListWord;
    }
}

