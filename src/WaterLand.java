import org.junit.Test;

public class WaterLand{
    public static class Solution{
        public static void pourWater(int[] heights, int location, int water){
            int[] waters = new int[heights.length];
            if(heights == null || heights.length == 0 || water == 0){
                print(heights, waters);
            }
            int index;
            while(water > 0){
                index = location;
                for(int i = location - 1; i >= 0; i--){
                    if(heights[i] + waters[i] > heights[index] + waters[index]){
                        break;
                    }else if(heights[i] + waters[i] < heights[index] + waters[index]){
                        index = i;
                    }
                }
                if(index != location){
                    waters[index]++;
                    water--;
                    continue;
                }
                for(int i = location + 1; i < heights.length; i++){
                    if(heights[i] + waters[i] > heights[index] + waters[index]){
                        break;
                    }else if(heights[i] + waters[i] < heights[index] + waters[index]){
                        index = i;
                    }
                }
                waters[index]++;
                water--;
            }
            print(heights, waters);
        }

        private static void print(int[] heights, int[] waters){
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

        public static void main(String[] str){
            int[] waterLand = new int[]{5,4,2,1,2,3,2,1,0,1,2,4};
            pourWater(waterLand, 5, 1);
            pourWater(waterLand, 5, 5);
            pourWater(waterLand, 5, 100);

            waterLand = new int[]{2,1,1,2,1,2,2};
            pourWater(waterLand, 3,4);
        }
    }

}