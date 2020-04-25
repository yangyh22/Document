public class Test {


    public static void main(String[] args) {
        final  String s = new String();


    }

    public int hashCode1() {
        char value[] = {'a','b'};
        int h = 0;
        if (h == 0 && value.length > 0) {
            char val[] = value;

            for (int i = 0; i < value.length; i++) {
                h = 31 * h + val[i];
            }
        }
        return h;
    }



}
