import java.util.HashMap;
import java.util.Map;

public class Test {


    public static void main(String[] args) {

        Map<String,String> map = new HashMap<>();

        for (String s : map.keySet()) {
            System.out.println(s.hashCode());
        }


    }




}
