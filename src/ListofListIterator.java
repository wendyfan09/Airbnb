import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Iterator;
import java.util.*;

public class ListofListIterator {
    public class Solution implements Iterator<Integer> {
        private Iterator<List<Integer>> rowIter;
        private Iterator<Integer> colIter;

        public Solution(List<List<Integer>> vec2d){
            rowIter = vec2d.iterator();
            colIter = Collections.emptyIterator();
        }

        @Override
        public Integer next(){
            return colIter.next();
        }

        @Override
        public boolean hasNext() {
            while((colIter == null || !colIter.hasNext()) && rowIter.hasNext())
                colIter = rowIter.next().iterator();
                return colIter != null && colIter.hasNext();
        }

        @Override
        public void remove(){
            while(colIter == null && rowIter.hasNext())
                colIter = rowIter.next().iterator();
            if(colIter != null){
                colIter.remove();
            }
        }
    }

    @Test
    public void test(){
        List<List<Integer>> test = new ArrayList<>();
        test.add(new ArrayList<Integer>(){{
            add(1);
            add(2);
        }});
        test.add(new ArrayList<Integer>(){{
            add(3);
        }});
        test.add(new ArrayList<Integer>(){{
            add(4);
            add(5);
            add(6);
        }});

        Solution sol = new ListofListIterator().new Solution(test);
        assertTrue(sol.hasNext());
        assertEquals(1, (int) sol.next());
        sol.remove();
        List<Integer> res = new ArrayList<>();
        while(sol.hasNext()){
            res.add(sol.next());
        }

        assertEquals(5, res.size());
        assertEquals(2, (int) res.get(0));
        assertEquals(4, (int) res.get(2));

        test = new ArrayList<>();
        test.add(new ArrayList<Integer>(){{
            add(1);
            add(2);
        }});
        test.add(new ArrayList<Integer>(){{
            add(3);
        }});
        sol = new ListofListIterator().new Solution(test);
        assertTrue(sol.hasNext());
        assertEquals(1, (int) sol.next());
        assertTrue(sol.hasNext());
        assertEquals(2, (int) sol.next());
        sol.remove();
        assertTrue(sol.hasNext());
        assertEquals(3, (int) sol.next());
    }
}
