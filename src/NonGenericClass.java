public class NonGenericClass // <V>
{

    //static V v; // compile error

    public static void m() {
        // V v = null;
    }

    public void nongeneric_method(int x) {
        //T tmp = null;

    }

    public <T> void generic_method(T x) {

    }

    public <T> T generic_method_2(T x) {
        return null;
    }

    public static  <T> void generic_static_method(T x) {
        System.out.println(x);
    }


    public static void main(String[] args) {
        NonGenericClass.generic_static_method("a");
        NonGenericClass.generic_static_method(3);
    }
}
