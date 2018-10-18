public class Word {
    private String word_target, word_explain;
    private int id;

    /**
     * Constructor
     * @param word_target
     * @param word_explain
     * @param id
     */
    public Word(String word_target, String word_explain, int id) {
        this.word_target = word_target;
        this.word_explain = word_explain;
        this.id = id;
    }

    /**
     * get word name
     * @return
     */
    public String getWord_target() {
        return word_target;
    }

    /**
     * set word name
     * @param word_target
     */
    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    /**
     * get word explanation
     * @return
     */
    public String getWord_explain() {
        return word_explain;
    }

    /**
     * set word explanation
     * @param word_explain
     */
    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }

    /***
     * Id of the words
     * @return
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Print the word to command line (for command line version and debug)
     */
    public void print() {
        System.out.println(this.id + "   " + this.word_target + "  " + this.word_explain);
    }
}
