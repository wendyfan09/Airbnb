import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;

import java.util.Arrays;

public class DisplayPage {
    public class Solution {
        public List<String> displayPages(List<String> input, int pageSize){
            List<String> res = new ArrayList<>();
            Iterator<String> iter = input.iterator();
            HashSet<String> set = new HashSet<>();
            boolean reachEnd = false;
            int count = 0;

            while(iter.hasNext()){
                String cur = iter.next();
                String host = cur.split(",")[0];
                if(! set.contains(host) || reachEnd){
                    res.add(cur);
                    set.add(host);
                    count++;
                    iter.remove();
                }

                if(count == pageSize){
                    if(!input.isEmpty()) {
                        res.add(" ");
                    }
                    set.clear();
                    iter = input.iterator();
                    reachEnd = false;
                    count = 0;
                }

                if(!iter.hasNext()){
                    reachEnd = true;
                    iter = input.iterator();
                }
            }
            return res;
        }
    }

    @Test
    public void test(){
        Solution sol = new DisplayPage().new Solution();
        String[] str = new String[]{
                "1,28,300.1,SanFrancisco",
                "4,5,209.1,SanFrancisco",
                "20,7,208.1,SanFrancisco",
                "23,8,207.1,SanFrancisco",
                "16,10,206.1,Oakland",
                "1,16,205.1,SanFrancisco",
                "6,29,204.1,SanFrancisco",
                "7,20,203.1,SanFrancisco",
                "8,21,202.1,SanFrancisco",
                "2,18,201.1,SanFrancisco",
                "2,30,200.1,SanFrancisco",
                "15,27,109.1,Oakland",
                "10,13,108.1,Oakland",
                "11,26,107.1,Oakland",
                "12,9,106.1,Oakland",
                "13,1,105.1,Oakland",
                "22,17,104.1,Oakland",
                "1,2,103.1,Oakland",
                "28,24,102.1,Oakland",
                "18,14,11.1,SanJose",
                "6,25,10.1,Oakland",
                "19,15,9.1,SanJose",
                "3,19,8.1,SanJose",
                "3,11,7.1,Oakland",
                "27,12,6.1,Oakland",
                "1,3,5.1,Oakland",
                "25,4,4.1,SanJose",
                "5,6,3.1,SanJose",
                "29,22,2.1,SanJose",
                "30,23,1.1,SanJose"
        };
        List<String> input = new ArrayList<String>(Arrays.asList(str));
        List<String> result = sol.displayPages(input, 12);
        assertEquals(32, result.size());
        assertEquals("1,28,300.1,SanFrancisco", result.get(0));
        assertEquals("11,26,107.1,Oakland", result.get(11));
        assertEquals(" ", result.get(12));
        assertEquals("1,16,205.1,SanFrancisco", result.get(13));
        assertEquals("2,30,200.1,SanFrancisco", result.get(14));
        assertEquals("25,4,4.1,SanJose", result.get(24));
        assertEquals(" ", result.get(25));
        assertEquals("1,2,103.1,Oakland", result.get(26));
        assertEquals("3,11,7.1,Oakland", result.get(27));
        assertEquals("30,23,1.1,SanJose", result.get(30));
        assertEquals("1,3,5.1,Oakland", result.get(31));
    }
}