import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class CheapestFlightsWithinKSteps {
    public static class Pair<K, V>{
        private final K element0;
        private final V element1;

        public static <K,V> Pair<K,V> createPair(K element0, V element1){
            return new Pair<K, V>(element0, element1);
        }
        public Pair(K element0, V element1){
            this.element0 = element0;
            this.element1 = element1;
        }

        public K getKey(){
            return element0;
        }
        public V getValue(){
            return element1;
        }
    }
    public class Solution{
        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K){
            Map<Integer, List<Integer>> outdegrees = new HashMap<>();
            for(int i = 0; i < flights.length; i++){
                if(outdegrees.containsKey(flights[i][0])){
                    outdegrees.get(flights[i][0]).add(i);
                }else{
                    List<Integer> list = new ArrayList<>();
                    list.add(i);
                    outdegrees.put(flights[i][0], list);
                }
            }

            Queue<Pair<Integer, Integer>> que = new LinkedList<>();

            int k = 0, ans = Integer.MAX_VALUE;

            for (int i = 0; i < outdegrees.get(src).size(); ++i) {
                que.add(new Pair <Integer, Integer>(outdegrees.get(src).get(i), 0));
            }

            NavigableMap<Integer,Integer> memo = new TreeMap<Integer, Integer>();

            while (!que.isEmpty() && k <= K) {
                int size = que.size();
                for (int i = 0; i < size; ++i) {
                    int curr_f = que.peek().getKey();
                    int start = flights[curr_f][0];
                    int end = flights[curr_f][1];
                    int curr_mon = que.peek().getValue() + flights[curr_f][2];
                    que.poll();
                    if (memo.containsKey(end) && memo.lastEntry().getKey() == end) {
                        memo.put(end, curr_mon);
                    } else {
                        if (memo.containsKey(end) && memo.get(end) <= curr_mon) {
                            continue;
                        }
                        memo.put(end, curr_mon);
                    }
                    if (end == dst) {
                        ans = Math.min(ans, curr_mon);
                    }
                    if (outdegrees.containsKey(end)) {
                        for (int j = 0; j < outdegrees.get(end).size(); ++j) {
                            que.add(new Pair <Integer, Integer>(outdegrees.get(end).get(j), curr_mon));
                        }
                    }
                }
                k++;
            }

            return ans == Integer.MAX_VALUE ? -1 : ans;
        }
    }
    public static class UnitTest{
        @Test
        public void test1(){
            Solution sol = new CheapestFlightsWithinKSteps().new Solution();
            int[][] edges = {
                    {0, 1, 100},
                    {1, 2, 100},
                    {0, 2, 500}
            };
            int result = sol.findCheapestPrice(3, edges, 0, 2, 1);
            assertEquals(200, result);
        }
    }
}
