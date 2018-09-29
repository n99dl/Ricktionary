package sample;

import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

public class Trie {
    private static final int ALPHABET_SIZE = 127;
    private int vocabNum = 0;

    static class Node {
        public Node[] children;
        public Word containID;
        public int count;

        public Node() {
            this.children = new Node[Trie.ALPHABET_SIZE];
            this.containID = null;
        }
    }

    Node treeRoot;

    public Trie() {
        treeRoot = new Node();
    }

    public int getVocabNum(){
        return this.vocabNum;
    }

    public void insertToTree(Word thisWord) {
        String word = thisWord.getWord_target().getValue().toLowerCase();
        Node tree = treeRoot;
        for (int i = 0; i < word.length(); i++) {
            int charToInt = word.charAt(i);
            if (tree.children[charToInt] == null) {
                tree.children[charToInt] = new Node();
            }
            tree = tree.children[charToInt];
        }
        if(tree.containID == null){
            this.vocabNum += 1;
        }
        tree.containID = thisWord;
    }

    /**
     * Remove From Tree
     */
    private boolean isLeafNode(Node node) {
        return (node.containID != null);
    }

    private boolean isFreeNode(Node node) {
        for (int i = 0; i < this.ALPHABET_SIZE; i++) {
            if (node.children[i] != null) {
                return false;
            }
        }
        return true;
    }

    private boolean removeHelper(Node node, String word, int level, int len) {
        if (node != null) {
            if (level == len) { // Base case
                if (node.containID != null) {
                    node.containID = null;
                    if (isFreeNode(node)) {
                        return true;
                    }
                    return false;
                }
            } else {
                int charToInt = word.charAt(level);
                if (removeHelper(node.children[charToInt], word, level + 1, len)) {
                    node.children[charToInt] = null;
                    return (!isLeafNode(node) && isFreeNode(node));
                }
            }
        }
        return false;
    }

    public void removeFromTree(String word) {
        word = word.toLowerCase();
        if(this.searchInTree(word, 1) != null){
            this.vocabNum -= 1;
        }
        int len = word.length();
        if (len > 0) {
            removeHelper(treeRoot, word, 0, len);
        }
    }

    private class FindMore {
        ArrayList<Word> results;

        public FindMore(){
            results = new ArrayList<>();
        }

        private void getWord(Node node){
            if(node.containID != null){
                results.add(node.containID);
            }
            for (int i = 0; i < Trie.ALPHABET_SIZE; i++) {
                if (node.children[i] != null) {
                    getWord(node.children[i]);
                }
            }
        }

        public ArrayList<Word> commonPrefix(Node curNode, int maxNext) {
            getWord(curNode);
            ArrayList<Word> trimmedRes = new ArrayList<>();
            for(Word word : results){
                trimmedRes.add(word);
                maxNext -= 1;
                if(maxNext == 0){
                    break;
                }
            }
            return trimmedRes;
        }

    }

    /**
     * Get All the Words From Tree
     */
    public ArrayList<Word> getAllWords() {
        FindMore getAll = new FindMore();
        Node tree = treeRoot;
        return getAll.commonPrefix(tree, this.vocabNum);
    }

    public ArrayList<Word> searchInTree(String word, int maxNext) {
        word = word.toLowerCase();
        ArrayList<Word> results = new ArrayList<>();
        Node tree = treeRoot;
        boolean canFindWord = true;
        for (int i = 0; i < word.length(); i++) {
            int charToInt = word.charAt(i);
            if (tree.children[charToInt] == null) {
                canFindWord = false;
                break;
            }
            tree = tree.children[charToInt];
        }
        if (tree.containID == null) {
            canFindWord = false;
        }
        if(canFindWord == false){
            return results;
        }
        FindMore next = new FindMore();
        results = next.commonPrefix(tree, maxNext);
        return results;
    }
}