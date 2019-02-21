import org.junit.Test;
import static org.junit.Assert.*;
public class FindMedianInLargeIntegerFileOfIntegers {
    public class Solution{
        public double findMedian(int[] nums){
            int len = 0;
            for(int num : nums){
                len++;
            }
            if(len % 2 == 1){
                //directly return middle
                return (double) search(nums, len/2 + 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
            }else{
                //return the average between len/2 and len/2 + 1;
                return (double) (search(nums, len/2, Integer.MIN_VALUE, Integer.MAX_VALUE) +
                        search(nums, len/2 + 1, Integer.MIN_VALUE, Integer.MAX_VALUE))/2;
            }
        }
        //binary search O(longn)
        private long search(int[] nums, int k, long left, long right){
            if(left >= right){
                return left;
            }

            long res = left;
            long guess = left + (right - left)/2;
            int count = 0;
            //travel through input and check whether we have half numbers smaller then guess number
            for(int num : nums){
                if(num <= guess){
                    count++;
                    //if count == k, we should return largest elemetn in the file that is smaller than the guess
                    res = Math.max(res, num);
                }
            }
            if(count == k){
                return res;
            }else if(count < k){
                //if count is smaller than half, then use the bigger one from res + 1 and guess to be a new left boundary.
                return search(nums, k, Math.max(res + 1, guess), right);
            }else{
                //counter is larger then k, then use binary search make right boundary is res(guess - 1)
                return search(nums, k, left, res);
            }
        }

    }

    public static class UnitTest{
        @Test
        public void test1(){
            Solution sol = new FindMedianInLargeIntegerFileOfIntegers().new Solution();
            assertEquals(3.0, sol.findMedian(new int[]{3, -2, 7}), 1E-03);
            assertEquals(5.0, sol.findMedian(new int[]{-100, 99, 3, 0, 5, 7, 11, 66, -33}), 1E-03);
            assertEquals(4.5, sol.findMedian(new int[]{4, -100, 99, 3, 0, 5, 7, 11, 66, -33}), 1E-03);
        }
    }
}
