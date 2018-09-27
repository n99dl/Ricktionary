import java.util.Comparator;

public class SortWords implements Comparator<Word>{
    public int compare(Word a, Word b){
        String wordA = a.getWord_target();
        String wordB = b.getWord_target();
        return wordA.compareTo(wordB);
    }   
}