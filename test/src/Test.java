import java.util.ArrayList;
import java.util.List;

public class Test {


    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");


        for (int i = list.size() - 1; i >= 0; i--) {

            // c
            // b
            // a
            System.out.println(list.get(i));
            if (list.get(i).equals("a")) {
                list.remove("a");
            }
        }

        // [b, c]
        System.out.println(list);

    }

}
