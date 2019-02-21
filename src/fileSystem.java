import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
public class fileSystem {

    public class Solution {
        Map<String, Integer> pathMap;
        Map<String, Runnable> callbackMap;

        public Solution(){
            this.pathMap = new HashMap<>();
            this.callbackMap = new HashMap<>();
            pathMap.put("", 0);
        }

        public boolean create(String path, int value){
            //check whether path exsited already
            if(pathMap.containsKey(path)) return false;

            //check parent path exsited already
            int lastSlashIndex = path.lastIndexOf("/");
            if(!pathMap.containsKey(path.substring(0, lastSlashIndex))) return false;

            //create new path
            pathMap.put(path, value);
            return true;
        }

        public boolean set(String path, int value){
            if(!pathMap.containsKey(path)) return false;
            pathMap.put(path, value);

            //Trigger Callback
            String curPath = path;
            while(curPath.length() > 0){
                if(callbackMap.containsKey(curPath)){
                    callbackMap.get(curPath).run();
                }
                int lastSlashIndex = curPath.lastIndexOf("/");
                curPath = curPath.substring(0, lastSlashIndex);
            }

            return true;
        }

        public Integer get(String path){
            return pathMap.get(path);
        }

        public boolean watch(String path, Runnable callback){
            if(!pathMap.containsKey(path)) return false;

            callbackMap.put(path, callback);
            return true;
        }

    }

    public static class UnitTest {
        @Test
        public void test(){
            Solution sol = new fileSystem().new Solution();
            assertTrue(sol.create("/a",1));
            assertEquals(1, (int)sol.get("/a"));
            assertTrue(sol.create("/a/b",2));
            assertEquals(2, (int)sol.get("/a/b"));
            assertTrue(sol.set("/a/b",3));
            assertEquals(3, (int)sol.get("/a/b"));
            assertFalse(sol.create("/c/d",4));
            assertFalse(sol.set("/c/d",4));
        }
    }
}
