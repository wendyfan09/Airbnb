import org.junit.Test;
import sun.text.normalizer.Trie;

import static org.junit.Assert.*;
import java.util.*;
public class PalindromePairs {
    //HashMap Solution
    public class Solution{
        public boolean isPalindrome(String s){
            int i = 0, j = s.length() - 1;
            while(i <= j){
                if(s.charAt(i++) != s.charAt(j--)) return false;
            }
            return true;
        }

        public List<List<Integer>> palindromePairs(String[] words){
            List<List<Integer>> result = new ArrayList<>();
            if(words == null || words.length <= 1) return result;
            HashMap<String, Integer> map = new HashMap<>();
            for(int i = 0; i < words.length; i++) map.put(words[i], i);

            for(int i = 0 ; i < words.length; i++){
                int left = 0, right = 0;
                while(left <= right){
                    String s = words[i].substring(left, right);
                    Integer j = map.get(new StringBuilder(s).reverse().toString());
                    if(j != null && i != j && isPalindrome(words[i].substring(left == 0 ? right:0, left == 0 ? words[i].length(): left))){
                        result.add(Arrays.asList(left == 0 ? new Integer[]{i, j} : new Integer[]{j, i}));
                    }
                    if(right < words[i].length()){
                        right++;
                    }else{
                        left++;
                    }
                }
            }
            return result;
        }
    }

    public class Solution2{
        class TrieNode {
            TrieNode[] next = new TrieNode[26];
            int index = -1;
            List<Integer> list = new ArrayList<>();
        }
        TrieNode root;

        public List<List<Integer>> palindromePairs(String[] words){
            List<List<Integer>> result = new ArrayList<>();
            TrieNode root = new TrieNode();
            if(words == null || words.length <= 1) return result;
            for(int i = 0; i < words.length; i++){
                addWords(root, words[i], i);
            }
            for(int i = 0; i < words.length; i++){
                search(words, i, root, result);
            }
            return result;
        }

        private boolean isPalindrom(String s, int i, int j){
            while(i < j){
                if(s.charAt(i++) != s.charAt(j--)) return false;
            }
            return true;
        }

        private void addWords(TrieNode root, String word, int index){
            for(int i = word.length() - 1; i >= 0; i--){
                if(root.next[word.charAt(i) - 'a'] == null){
                    root.next[word.charAt(i) - 'a'] = new TrieNode();
                }
                if(isPalindrom(word, 0, i)){
                    root.list.add(index);
                }
                root = root.next[word.charAt(i) - 'a'];
            }
            root.list.add(index);
            root.index = index;
        }

        private void search(String[]words, int i, TrieNode root, List<List<Integer>> list){
            for(int j = 0 ; j < words[i].length(); j++){
                if(root.index >= 0 && root.index != i && isPalindrom(words[i], j, words[j].length() - 1)){
                    list.add(Arrays.asList(i, root.index));
                }

                root = root.next[words[i].charAt(j) - 'a'];
                if(root == null) return;
            }

            for(int j : root.list){
                if(i == j) continue;
                list.add(Arrays.asList(i,j));
            }
        }

    }

    public static class UnitTest{
        @Test
        public void test1(){
            Solution2 sol1 = new PalindromePairs().new Solution2();
            String[] test = new String[]{"bat", "tab", "cat"};
            List<List<Integer>> result = sol1.palindromePairs(test);
            assertEquals(2, result.size());

            test = new String[]{"abcd", "dcba", "lls", "s", "sssll"};
            result = sol1.palindromePairs(test);
            assertEquals(4, result.size());

            test = new String[]{"a", ""};
            result = sol1.palindromePairs(test);
            assertEquals(2, result.size());
        }
    }
}

