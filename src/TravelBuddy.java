import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
public class TravelBuddy {
    //need a new class to help us
    class Buddy implements Comparable<Buddy> {
        String name;
        int similarity; //similarity point betwwen me and this buddy
        Set<String> wishList;

        Buddy(String name, int similarity, Set<String> wishList){
            this.name = name;
            this.similarity = similarity;
            this.wishList = wishList;
        }

        @Override
        public int compareTo(Buddy that){
            return that.similarity - this.similarity;
        }

    }
    public class Solution {
        private List<Buddy> buddies;
        private Set<String> myWishList;

        public Solution(Set<String> myWishList, Map<String, Set<String>> friendsWishlist){
            this.buddies = new ArrayList<>();
            this.myWishList =  myWishList;

            for(String name: friendsWishlist.keySet()){
                //get everyone's list
                Set<String> wishList = friendsWishlist.get(name);
                Set<String> intersection = new HashSet<>(wishList);
                //get intersection list
                intersection.retainAll(myWishList);
                int similarity = intersection.size();
                //if similarity >= 50%, then we need to consider this buddy
                if(similarity >= wishList.size()/2)
                    buddies.add(new Buddy(name, similarity, wishList));
            }
        }

        public List<String> recommendCities(int k ){
            List<String> res = new ArrayList<>();
            //get sorted buddies
            List<Buddy> buddies = getSortedBuddies();

            int i = 0;
            while(k > 0 && i < buddies.size()){
                Set<String> diff = new HashSet<>(buddies.get(i).wishList);
                //filter out cities which is already in my wish list;
                diff.removeAll(myWishList);
                //if size smaller then k, then we can add all and go to find next buddies' recommendation
                if(diff.size() <= k){
                    res.addAll(diff);
                    k -= diff.size();
                    i++;
                }else{
                    //else we just need get k cities from current buddy;
                    Iterator<String> it = diff.iterator();
                    while(k > 0){
                        res.add(it.next());
                        k--;
                    }
                }
            }
            return res;
        }


        //sorted buddy by similiaty points;
        public List<Buddy> getSortedBuddies(){
            Collections.sort(buddies);
            List<Buddy> res = new ArrayList<>(buddies);
            return res;
        }

    }


    public static class UnitTest{
        @Test
        public void test() {
            Set<String> myWishList = new HashSet<>(Arrays.asList(new String[]{"a", "b", "c", "d"}));
            Set<String> wishList1 = new HashSet<>(Arrays.asList(new String[]{"a", "b", "e", "f"}));
            Set<String> wishList2 = new HashSet<>(Arrays.asList(new String[]{"a", "c", "d", "g"}));
            Set<String> wishList3 = new HashSet<>(Arrays.asList(new String[]{"c", "f", "e", "g"}));
            Map<String, Set<String>> friendWishLists = new HashMap<>();
            friendWishLists.put("Buddy1", wishList1);
            friendWishLists.put("Buddy2", wishList2);
            friendWishLists.put("Buddy3", wishList3);
            Solution sol = new TravelBuddy().new Solution(myWishList, friendWishLists);
            List<String> res = sol.recommendCities(10);
            assertEquals(3, res.size());
            assertEquals("g", res.get(0));
            assertEquals("e", res.get(1));
            assertEquals("f", res.get(2));

        }
    }
}
