import org.junit.Test;
import sun.text.normalizer.Trie;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.*;
public class PalindromePairs {
    public class Solution{
        class TrieNode{
            TrieNode[] next;
            int index;
            List<Integer> list;

            TrieNode(){
                next = new TrieNode[26];
                index = -1;
                list = new ArrayList<>();
            }
        }
        public List<List<Integer>> PalindromePairs(String[] words){
            TrieNode root = new TrieNode();
            List<List<Integer>> result = new ArrayList<>();
            for(int i = 0; i < words.length; i++){
                addWord(root, words[i], i);
            }
            for(int i = 0; i < words.length; i++){
                searchWord(words, i, root, result);
            }
            return result;
        }
        private void addWord(TrieNode root, String word, int index){
            for(int i = word.length() - 1; i >= 0; i--){
                int j = word.charAt(i) - 'a';
                if(root.next[j] == null) root.next[j] = new TrieNode();
                if(isPalindrome(word, 0, i)){
                    root.list.add(index);
                }
                root = root.next[j];
            }
            root.index = index;
            root.list.add(index);
        }
        private void searchWord(String[] words, int i, TrieNode root, List<List<Integer>> result){
            for(int j = 0; j < words[i].length(); j++){
                if(root.index >= 0 && root.index != i && isPalindrome(words[i], j, words[i].length() - 1)){
                    result.add(Arrays.asList(i, root.index));
                }
                root = root.next[words[i].charAt(j) - 'a'];
                if(root == null) return;
            }
            for(int index: root.list){
                if(index == i) continue;
                result.add(Arrays.asList(i, index));
            }
        }
        private boolean isPalindrome(String word, int i, int j){
            while(i < j){
                if(word.charAt(i++) != word.charAt(j--)) return false;
            }
            return true;
        }
    }

    public static class UnitTest{
        @Test
        public void test1(){
            Solution sol1 = new PalindromePairs().new Solution();
            String[] test = new String[]{"bat", "tab", "cat"};
            List<List<Integer>> result = sol1.PalindromePairs(test);
            assertEquals(2, result.size());

            test = new String[]{"abcd", "dcba", "lls", "s", "sssll"};
            result = sol1.PalindromePairs(test);
            assertEquals(4, result.size());

            test = new String[]{"a", ""};
            result = sol1.PalindromePairs(test);
            assertEquals(2, result.size());
        }
    }
}

