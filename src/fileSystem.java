import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
public class fileSystem{
    class Dir{
        HashMap<String, Dir> dirs = new HashMap<>();
        HashMap<String, String> files = new HashMap<>();
    }
    Dir root;
    public fileSystem(){
        root = new Dir();
    }
    public class Solution{
        public List<String> ls(String path){
            Dir t = root;
            List<String> files = new ArrayList<>();
            if(!path.equals("/")){
                String[] d = path.split("/");
                for(int i = 1; i < d.length - 1; i++){
                    t = t.dirs.get(d[i]);
                }
                if(t.dirs.containsKey(d[d.length - 1])){
                    files.add(t.files.get(d[d.length - 1]));
                }else{
                    t = t.dirs.get(d[d.length - 1]);
                }
            }
            files.addAll(new ArrayList<>(t.dirs.keySet()));
            files.addAll(new ArrayList<>(t.files.keySet()));
            Collections.sort(files);
            return files;
        }
        public void mkdir(String path){
            Dir t = root;
            String[] d = path.split("/");
            for(int i = 1; i < d.length; i++){
                if(!t.dirs.containsKey(d[i])) t.dirs.put(d[i], new Dir());
                t = t.dirs.get(d[i]);
            }
        }
        public void addContentToPath(String path, String content){
            Dir t = root;
            String[] d = path.split("/");
            for(int i = 1; i < d.length - 1; i++){
                t = t.dirs.get(d[i]);
            }
            t.files.put(d[d.length - 1], t.files.getOrDefault(d[d.length - 1], "") + content);
        }
        public String readContentFromPath(String path){
            Dir t = root;
            String[] d = path.split("/");
            for(int i = 1; i < d.length - 1; i++){
                t = t.dirs.get(d[i]);
            }
            return t.files.get(d[d.length - 1]);
        }
    }
    public static class UnitTest{
        @Test
        public void test(){
            Solution sol = new fileSystem().new Solution();
            List<String> result = sol.ls("/");
            assertEquals(0, result.size());
            sol.mkdir("/a/b/c");
            sol.addContentToPath("/a/b/c/d", "hello");
            String content = sol.readContentFromPath("/a/b/c/d");
            assertEquals("hello", content);
            result = sol.ls("/a/b/c/d");
            assertEquals("d", result.get(0));
            assertEquals("hello", result.get(1));
            result = sol.ls("/");
            for(String s: result){
                System.out.println(s);
            }
        }
    }
}