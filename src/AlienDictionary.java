import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
public class AlienDictionary{
    public class Solution{
        private final int N = 26;
        public String alienOrder(String[] words){
            boolean[][] edges = new boolean[N][N];
            int[] visited = new int[N];
            buildGraph(words, edges, visited);

            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < N; i++){
                if(visited[i] == 0){
                    if(!dfs(visited, edges,sb,i)) return "";
                }
            }
            return sb.reverse().toString();
        }
        private boolean dfs(int[] visited, boolean[][] edges, StringBuilder sb, int i){
            visited[i] = 1;
            for(int j = 0; j < N; j++){
                if(edges[i][j]){
                    if(visited[j] == 1) return false;
                    if(visited[j] == 0){
                        if(!dfs(visited, edges, sb, j)) return false;
                    }
                }
            }
            visited[i] = 2;
            sb.append((char) (i + 'a'));
            return true;
        }
        private void buildGraph(String[] words, boolean[][] edges, int[] visited){
            Arrays.fill(visited, -1);
            for(int i = 0; i < words.length; i++){
                for(char c : words[i].toCharArray()) visited[c - 'a'] = 0;
                if(i > 0){
                    String w1 = words[i - 1];
                    String w2 = words[i];
                    int len = Math.min(w1.length(), w2.length());
                    for(int j = 0; j < len; j++){
                        char c1 = w1.charAt(j);
                        char c2 = w2.charAt(j);
                        if(c1 != c2){
                            edges[c1 - 'a'][c2 - 'a'] = true;
                            break;
                        }
                    }
                }
            }
        }
    }
    public static class UnitTest{
        @Test
        public void test(){
            Solution sol = new AlienDictionary().new Solution();
            String[] test = new String[]{"wrt", "wrf", "er", "ett", "rftt"};
            String result = sol.alienOrder(test);
            assertEquals("wertf", result);
        }
    }
}