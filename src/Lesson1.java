import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class Lesson1 {
    public static void random_access_file_example() {
        try {

            RandomAccessFile rf = new RandomAccessFile("binary_file", "rw");
            rf.writeShort(5);
            rf.writeInt(5);
            rf.writeByte(1);
            rf.writeInt(265);
            System.out.println(rf.getFilePointer());
            rf.seek(5);
            //System.out.println(rf.getFilePointer());
            System.out.println(rf.readShort()); // 51 -> 00000101 0000 0001 -> 1 + 1024 + 256
            System.out.println("dosya boyutu: " + rf.length());
            System.out.println("pointer: " + rf.getFilePointer());
            rf.setLength(0);
            rf.writeShort(5); // 05
            rf.seek(0);
            rf.writeByte(1); // 15
            rf.seek(0);
            System.out.println(rf.readShort()); // 261
            //System.out.println(rf.readByte()); // 1
            //System.out.println(rf.readByte()); // 5


            // dosya boyut artırma
            rf.setLength(100);
            rf.seek(50);
            System.out.println(rf.readInt());

            //rf.seek(150); // boyut 100 gidemeyiz






            rf.close();
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static void print(ArrayList<?> x) {
        for (int i = 0; i < x.size(); i++) {
            System.out.println(x.get(i));
        }
    }
    public static void sum(ArrayList<? extends Integer> x) {
        Integer toplam = 0;
        for (int i = 0; i < x.size(); i++) {
           toplam += x.get(i);

        }
        System.out.println(toplam);
    }
    public static void method(ArrayList<? super B> x) {

    }

    public static void main(String[] args) {
        //random_access_file_example();
        /*ArrayList<Integer> arrayList = new ArrayList<>(2);
        for (int i = 0; i < 10; i++) {
            arrayList.add(i);
        }

        arrayList.trimToSize();
        arrayList.ensureCapacity(10000);

        Object [] array = arrayList.toArray();
        Integer [] integer_array = new Integer[arrayList.size() - 5];
        integer_array = arrayList.toArray(integer_array);



        long begin = System.currentTimeMillis();
        for(int i = 0; i < integer_array.length; i++) {
            System.out.println(integer_array[i]);
        }
        long end = System.currentTimeMillis();
        System.out.println("time: " + (end-begin));

        ArrayList<Integer> copy_arraylist = arrayList;
        */
        ArrayList<X> list = new ArrayList<X>();

        list.add(new X(1,2));
        /*list.add(new X(1,3));
        list.add(new X(2,1));
        list.add(new X(2,3));*/

        X x = new X(1,5);
        //System.out.println(list.contains(x));

        /*ArrayList<String> a = new ArrayList<>();
        ArrayList<Integer> b = new ArrayList<>();
        a.add("a");
        a.add("b");
        a.add("c");

        b.add(1);
        b.add(2);
        b.add(3);

        //print(a);
        //print(b);

        sum(b);*/



        ArrayList<A> a_list = new ArrayList<>();
        ArrayList<B> b_list = new ArrayList<>();
        ArrayList<C> c_list = new ArrayList<>();

        a_list.add(new A());
        b_list.add(new B());
        c_list.add(new C());

        /*method(a_list);
        method(b_list);
        method(c_list);*/
    }
    static class X {
        int a;
        int b;

        public X(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public boolean equals(X x) {
            if(this.b == x.b)
                return true;
            return false;
        }
        public boolean equals(Object x) {
            X temp = (X)x;
            if(this.a == temp.a)
                return true;
            return false;
        }
    }
    static class A {

    }
    static class B {

    }
    static class C extends B {

    }
}
