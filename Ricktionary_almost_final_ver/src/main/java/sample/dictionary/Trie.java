package sample.dictionary;

import java.util.ArrayList;


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

        public int getLCS(String a, String b){
            int n = a.length();
            int m = b.length();
            int[][] memo = new int[n + 5][m + 5];
            for(int i = n - 1; i >= 0; i--){
                for(int j = m - 1; j >= 0; j--){
                    if(a.charAt(i) == b.charAt(j)){
                        memo[i][j] = Math.max(memo[i][j], memo[i + 1][j + 1] + 1);
                    }
                    memo[i][j] = Math.max(memo[i][j], memo[i + 1][j]);
                    memo[i][j] = Math.max(memo[i][j], memo[i][j + 1]);
                }
            }
            return memo[0][0];
        }

        public ArrayList<Word> longestCommonSub(Node prefixNode, String word, int maxNext){
            getWord(prefixNode);
            int[] valueLCS = new int[results.size()];
            int maxValue = 0;
            int i = 0;
            for(Word thisWord : results){
                valueLCS[i] = getLCS(word, thisWord.getWord_target().getValue().toLowerCase());
                maxValue = Math.max(maxValue, valueLCS[i]);
                // System.out.println(word + " " + thisWord.getWord_target().getValue().toLowerCase() + valueLCS[i]);
                i++;
            }
            ArrayList<Word> trimmedRes = new ArrayList<>();
            for(i = 0; i < results.size(); i++){
                // System.out.println(valueLCS[i] + " " + maxValue);
                if(valueLCS[i] == maxValue){
                    trimmedRes.add(results.get(i));
                    maxNext--;
                    if(maxNext == 0){
                        break;
                    }
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
//        System.out.println(word);
        ArrayList<Word> results;
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
        FindMore next = new FindMore();
        if(canFindWord == false){
            results = next.longestCommonSub(tree, word, 5);
//            System.out.println(results.size());
            Word empty = new Word("-1", "-1", -1);
            results.add(0, empty);
        } else{
            results = next.commonPrefix(tree, maxNext);
        }
        return results;
    }
}