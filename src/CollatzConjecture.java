import org.junit.Test;

import java.util.HashMap;
import static org.junit.Assert.*;

public class CollatzConjecture {

    public class Solution {
        HashMap<Integer, Integer> map = new HashMap<>();

        public int findLongestStep(int num){
            if(num <= 0) return 0;
            int step = 0;
            for(int i = 1; i <= num; i++){
                int t = findStep(i);
                map.put(i, t);
                step = Math.max(step, t);
            }
            return step;
        }

        private int findStep(int num){
            if(num <= 1) return 1;
            if(map.containsKey(num)) return map.get(num);
            if(num % 2 == 0) return 1 + findStep(num/2);
            return 1 + findStep(num * 3 + 1);
        }
    }

    public static class UnitTest{
        @Test
        public void test(){
            Solution sol = new CollatzConjecture().new Solution();
            assertEquals(1, sol.findLongestStep(1));
            assertEquals(20, sol.findLongestStep(10));
            assertEquals(112, sol.findLongestStep(37));
            assertEquals(119, sol.findLongestStep(101));
        }

    }
}
