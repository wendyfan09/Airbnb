import org.junit.Test;

public class WaterLand {
    public class Solution{
        public void pourWater(int[] heights, int location, int water){
            //use same length array to remember water location;
            int[] waters = new int[heights.length];
            int pourLocation;

            while(water > 0){
                int left = location - 1;
                while(left >= 0){
                    //check whether pour water on current location will make water+hight higher than right neighbor
                    if(heights[left] + waters[left] > heights[left + 1] + waters[left + 1]) break;
                    left--;
                }
                //compare left + 1 position with location
                if(heights[left + 1] + waters[left + 1] < heights[location] + waters[location]){
                    pourLocation = left + 1;
                    waters[pourLocation]++;
                    water--;
                    continue;
                }

                //left side is not available, let's check right side; similar logic to left side;
                int right = location + 1;
                while(right < heights.length){
                    if(heights[right] + waters[right] > heights[right -1] + waters[right - 1]) break;
                    right++;
                }
                if(heights[right - 1] + waters[right - 1] < heights[location] + waters[location]){
                    pourLocation = right - 1;
                    waters[pourLocation]++;
                    water--;
                    continue;
                }

                //if left and right side are all full, then we can directly pour the water at location
                pourLocation = location;
                waters[pourLocation]++;
                water--;
            }
            print(heights, waters);
        }

        //print result;
        private void print(int[] heights, int[] waters){
            int n = heights.length;

            int maxHeight = 0;
            for(int i = 0; i < n; i++){
                maxHeight = Math.max(maxHeight, heights[i] + waters[i]);
            }

            for(int height = maxHeight; height >= 0; height--){
                for(int i = 0; i < n; i++){
                    if(height <= heights[i]){
                        System.out.print("+");
                    }else if(height > heights[i] && height <= heights[i] + waters[i]){
                        System.out.print("W");
                    }else{
                        System.out.print(" ");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public static class UnitTest{
        @Test
        public void test(){
            Solution sol = new WaterLand().new Solution();
            int[] waterLand = new int[]{5,4,2,1,2,3,2,1,0,1,2,4};
            sol.pourWater(waterLand, 5, 1);
            sol.pourWater(waterLand, 5, 5);
            sol.pourWater(waterLand, 5, 100);

            waterLand = new int[]{2,1,1,2,1,2,2};
            sol.pourWater(waterLand, 3,4);
        }
    }
}
