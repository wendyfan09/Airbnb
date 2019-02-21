import java.util.*;
import org.junit.Test;
import static org.junit.Assert.*;
public class AlienDictionary {
    public class Solution {
        public String alienOrder(String[] words) {
            // 1.Uses the order of word to determine which character comes first. And construct a direct graph(有向图)
            // 2.Base on the direct graph do topology sort.
            Map<Character, Set<Character>> graph = constructGraph(words);
            return topologicalSorting(graph);
        }


        private Map<Character, Set<Character>> constructGraph(String[] words) {
            Map<Character, Set<Character>> graph = new HashMap<>();

            // create nodes

            // Get all characters from words
            // For words = [abcd, abce, b]
            // get characters [a, b, c, d, e]
            for (int i = 0; i < words.length; i++) {
                for (int j = 0; j < words[i].length(); j++) {
                    char c = words[i].charAt(j);
                    if (!graph.containsKey(c)) {
                        graph.put(c, new HashSet<Character>());
                    }
                }
            }

            // create edges

            // Compare all pair of words[i] and words[i+1].
            // Get character order based on word order.
            // For example:
            //      words = [abcd, abce, b]
            // i=0:     abcd > abce   ===     d > e
            // i=1:     abcd > b      ===     a > b
            for (int i = 0; i <  words.length - 1; i++) {
                int index = 0;
                while (index < words[i].length() && index < words[i + 1].length()) {
                    if (words[i].charAt(index) != words[i + 1].charAt(index)) {
                        graph.get(words[i].charAt(index)).add(words[i + 1].charAt(index));
                        break;
                    }
                    index++;
                }
            }

            // After previous steps, we get graph = a > {b}, b > {}, c > {}, d > {e}, e > {}.
            // i.e. we only get two relationship, a>b and d>e.

            return graph;
        }

        // Initialize the "indegreee" map, which says in the graph we constrcut at "constructGraph", for each character, how many char is bigger than it.
        // For example, we have d>e and a>b in previous example, then this function will return {{e, 1}. {b, 1}, {a, 0}, {c, 0}, {e,0}}
        private Map<Character, Integer> getIndegree(Map<Character, Set<Character>> graph) {
            Map<Character, Integer> indegree = new HashMap<>();
            for (Character u : graph.keySet()) {
                indegree.put(u, 0);
            }

            for (Character u : graph.keySet()) {
                for (Character v : graph.get(u)) {
                    indegree.put(v, indegree.get(v) + 1);
                }
            }

            return indegree;
        }

        // Spend 5 minutes on here: https://www.coursera.org/lecture/algorithms-part2/topological-sort-RAMNS

        private String topologicalSorting(Map<Character, Set<Character>> graph) {
            Map<Character, Integer> indegree = getIndegree(graph);
            // as we should return the topo order with lexicographical order
            // we should use PriorityQueue instead of a FIFO Queue
            Queue<Character> queue = new PriorityQueue<>();

            // Just construct topology order. Chars with 0 indegree means it's not bigger than any other char, we can do BFS based on it.
            // There will be at least one char no bigger than any other chars. So start with it.
            // For example, graph a>b>c>d>e, put e in queue.
            for (Character u : indegree.keySet()) {
                if (indegree.get(u) == 0) {
                    queue.offer(u);
                }
            }

            StringBuilder sb = new StringBuilder();
            while (!queue.isEmpty()) {
                // Get "e" becase it's no bigger than any char.
                Character head = queue.poll();
                sb.append(head);
                // Because now we delete e from the graph, new graph is a>b>c>d. There is no long d>e, so d indegree -= 1.
                // Now d indegree = 0, put it in queue to continue BFS, find the next smallest char.
                for (Character neighbor : graph.get(head)) {
                    indegree.put(neighbor, indegree.get(neighbor) - 1);
                    if (indegree.get(neighbor) == 0) {
                        queue.offer(neighbor);
                    }
                }
            }

            // This is handling condition that the graph has loop like a>b>c>a, then there will be no order.
            if (sb.length() != indegree.size()) {
                return "";
            }
            return sb.toString();
        }
    }

    public static class UnitTest{
        @Test
        public void test1(){
            Solution sol = new AlienDictionary().new Solution();
            String[] words ={
                    "wrt",
                    "wrf",
                    "er",
                    "ett",
                    "rftt"};
            String result = sol.alienOrder(words);
            assertEquals("wertf", result);
        }
    }
}