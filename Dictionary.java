import java.util.ArrayList;
import java.util.Collections;

public class Dictionary {
    private Trie trie;
    private ArrayList<Word> wordList;

    public Dictionary(){
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

    public void sort(){
        Collections.sort(wordList, new SortWords());
        int count = 0; // 0-indexed
        for(Word word : wordList){
            word.setId(++count); // Set new ID
        }
    }

    public Word lookupWord(String word) {
        ArrayList<Integer> arrayListWord = trie.searchInTree(word, 1);
        if (arrayListWord.isEmpty()) {
            return null;
        }
        return wordList.get(arrayListWord.get(0));
    }
}