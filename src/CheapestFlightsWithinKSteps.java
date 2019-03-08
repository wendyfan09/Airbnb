import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;
public class CheapestFlightsWithinKSteps{
    public class Solution{
        public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K){
            Map<Integer, Map<Integer, Integer>> prices = new HashMap<>();
            for(int[] f: flights){
                if(!prices.containsKey(f[0])) prices.put(f[0], new HashMap<>());
                prices.get(f[0]).put(f[1], f[2]);
            }
            Queue<int[]> pq = new PriorityQueue<>((a,b) -> (Integer.compare(a[0], b[0])));
            pq.add(new int[]{0, src, K+1});
            while(!pq.isEmpty()){
                int[] top = pq.remove();
                int price = top[0];
                int city = top[1];
                int stop = top[2];
                if(city == dst ) return price;
                if(stop > 0){
                    Map<Integer, Integer> adj = prices.getOrDefault(city, new HashMap<>());
                    for(int a: adj.keySet()){
                        pq.add(new int[]{price + adj.get(a), a, stop - 1});
                    }
                }
            }
            return -1;
        }
    }
    public static class UnitTest{
        @Test
        public void test(){
            Solution sol = new CheapestFlightsWithinKSteps().new Solution();
            int[][] flights = {
                    {0, 1, 100},
                    {1, 2, 100},
                    {0, 2, 500}
            };
            int result = sol.findCheapestPrice(3, flights, 0, 2, 1);
            assertEquals(200, result);

            int[][] newflights = {
                    {0, 1, 100},
                    {1, 2, 700},
                    {0, 2, 500}
            };
            int result2 = sol.findCheapestPrice(3, newflights, 0, 2, 1);
            assertEquals(500, result2);
        }
    }
}