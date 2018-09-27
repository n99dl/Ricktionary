import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

public class Trie {
    private static final int ALPHABET_SIZE = 26;

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

    public void insertToTree(Word thisWord) {
        String word = thisWord.getWord_target().toLowerCase();
        Node tree = treeRoot;
        for (int i = 0; i < word.length(); i++) {
            int charToInt = word.charAt(i) - 'a';
            if (tree.children[charToInt] == null) {
                tree.children[charToInt] = new Node();
            }
            tree = tree.children[charToInt];
        }
        tree.containID = thisWord;
    }

    public class FindMore {

        public ArrayList<Word> commonPrefix(Node curNode, int maxNext) {
            ArrayList<Word> results = new ArrayList<>();
            Queue<Node> q = new LinkedList<>();
            q.add(curNode);
            while (q.size() > 0) {
                Node topQueue = q.remove();
                if (topQueue.containID != null) {
                    results.add(topQueue.containID);
                    maxNext = maxNext - 1;
                    if (maxNext == 0) {
                        break;
                    }
                }
                for (int i = 0; i < Trie.ALPHABET_SIZE; i++) {
                    if (topQueue.children[i] != null) {
                        q.add(topQueue.children[i]);
                    }
                }
            }
            return results;
        }

    }

    public ArrayList<Word> searchInTree(String word, int maxNext) {
        ArrayList<Word> results = new ArrayList<>();
        Node tree = treeRoot;
        boolean canFindWord = true;
        for (int i = 0; i < word.length(); i++) {
            int charToInt = word.charAt(i) - 'a';
            if (tree.children[charToInt] == null) {
                canFindWord = false;
                break;
            }
            tree = tree.children[charToInt];
        }
        if (tree.containID == null) {
            canFindWord = false;
        }
        if (canFindWord) {
            FindMore next = new FindMore();
            results = next.commonPrefix(tree, maxNext);
        } else {
            // Develop later
        }
        return results;
    }
}