import javafx.scene.control.DatePicker;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuCombination {
    public class Solution{
        public List<List<Double>> getCombos(double[] prices, double target){
            List<List<Double>> res = new ArrayList<>();

            //boundary cases
            if(prices == null || prices.length == 0 || target <= 0) return res;

            // use int for target and price to keep accuracy
            int centsTarget = (int) Math.round(target * 100);
            //sort the price array
            Arrays.sort(prices);
            int[] centPrices = new int[prices.length];
            for(int i = 0; i < prices.length; i++){
                centPrices[i] = (int) Math.round(prices[i] * 100);
            }

            search(res, centPrices, 0, centsTarget, new ArrayList<>(), prices);
            return res;
        }

        //dfs
        private void search(List<List<Double>> res, int[] centsPrices, int start, int centsTarget,
                            List<Double> curCombo, double[] prices){
            //find one match combination
            if(centsTarget == 0){
                res.add(new ArrayList<>(curCombo));
                return;
            }

            for(int i = start; i < centsPrices.length; i++){
                //we don't check start incease start == 0, if start i > start, then we can compare whether we have duplicated price
                if(i > start && centsPrices[i] == centsPrices[i - 1]) continue;
                //current price has been already over target budget
                if(centsPrices[i] > centsTarget) break;
                //dfs
                curCombo.add(prices[i]);
                search(res, centsPrices, i + 1, centsTarget - centsPrices[i], curCombo, prices);
                curCombo.remove(curCombo.size() - 1);
            }
        }
    }
    public static class UnitTest{
        @Test
        public void test1(){
            Solution sol = new MenuCombination().new Solution();
            double[] prices = {10.02, 1.11, 2.22, 3.01, 4.02, 2.00, 5.03};
            List<List<Double>> combos = sol.getCombos(prices, 7.03);
            System.out.println(combos);
            assertEquals(2, combos.size());
        }
    }
}


